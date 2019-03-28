package Locations;

import Loggers.Logger;
import Resources.MemException;
import Resources.MemFIFO;
import sun.rmi.runtime.Log;

import java.util.Random;

public class RepairArea
{

    /**
     *      Constants
     * */
    private static String   MANAGER     = "Manager",
                            MECHANIC    = "Mechanic",
                            REPAIR_AREA = "Repair Area";

    /**
     *      States of the cars.
     *
     * */
    private static int  NOT_REGISTERED  = 0,
                        CHECKED         = 1,
                        WAITING_PARTS   = 2,
                        ON_REPAIR       = 3,
                        REPAIRED        = 4;

    /**
     *  Initialize GeneralRepInformation
     *
     *  @serialField gri
     * */
    private GeneralRepInformation gri;

    /**
     *      Cars waiting for parts
     *
     *      @serialField carsWaitingForParts
     * */
    private volatile MemFIFO<Integer> carsWaitingForParts;

    /**
     *      Range of the IDs of car types
     *
     *      @serialField rangeCarPartTypes
     * */
    private int rangeCarPartTypes;

    /**
     *      Array of car's status.
     *      Index represents the id of the car and the value the state of the car.
     *
     *      @serialField statusOfCars
     * */
    private volatile int[] statusOfCars;

    /**
     *      Array of number of carParts to be used.
     *      Index represents the id of the car. Value represents the number of parts available.
     *
     *      @serialField carParts
     * */

    private volatile int[] carParts;

    /**
     * Max stock possible for each car part
     * Index represents the id of the part
     * @serialField maxCarParts;
     * */
    private volatile int[] maxCarPartsNumber;

    /**
     *      Array with the id part type.
     *      Index represents the id of the car.
     *      Value the needed part for the repair.
     * */
    private volatile int[] carNeededPart;

    /**
     *  Flag announcing work left to do
     *  @serialField workToDo
     * */
    private volatile boolean workToDo = false;

    /**
     *  Array with a flag announcing if a car needs to be checked for missing part. The index of the array is the ID of the car
     *  @serialField carsNeedsCheck
     * */
    private volatile boolean[] carsNeedsCheck;

    /**
     *  Array that is -1 if a car doesn't have a car part associated with it, and the ID of the part, to reserve
     *  that part to a specific car (index is the ID of the car).
     *  @serialField reserveCarPart
     * */
    private volatile int[] reserveCarPart;

    /**
     *  Flag announcing if all the tasks were done
     *  @serialField allDone
     * */
    private boolean allDone;


    /**
     * Instantiation of the Repair Area.
     * @param totalNumCars number of the total number of cars
     * @param rangeCarPartTypes range of the IDs of car types
     * @param carParts array of number of carParts to be used. Index represents the ID of the car and value represents the number of parts available.
     * @param maxCarParts max stock possible for each car part. Index represents the ID of the part
     * @param gri General Repository Information to be used as logger
     * */
    public RepairArea( int totalNumCars, int rangeCarPartTypes, int[] carParts, int[] maxCarParts, GeneralRepInformation gri)
    {
        this.gri = gri;

        this.allDone = false;
        this.rangeCarPartTypes = rangeCarPartTypes;
        this.carParts = carParts;
        this.statusOfCars = new int[totalNumCars];
        this.carNeededPart = new int[totalNumCars];
        this.maxCarPartsNumber = maxCarParts;
        this.carsNeedsCheck = new boolean[totalNumCars];
        this.reserveCarPart = new int[totalNumCars];
        for(int i = 0; i<totalNumCars; i++){
            this.statusOfCars[i] = NOT_REGISTERED;
            this.carNeededPart[i] = -1;
            this.reserveCarPart[i] = -1;
            this.carsNeedsCheck[i] = false;
        }
        try {
            this.carsWaitingForParts = new MemFIFO<>(new Integer[totalNumCars]);
        } catch (MemException e) {
            Logger.logException(e);
        }
    }

    /**
     *      Checks the needed parts for the car to repair
     *
     *      @param idCar    - id of the car to check
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return the id of the part needed for repair
     * */
    public synchronized int checkCar(int idCar, int mechanicId)
    {   String FUNCTION = "checkCar";
        Random rand = new Random();
        int randomNum = rand.nextInt(rangeCarPartTypes);
        assert (randomNum <= rangeCarPartTypes && randomNum >= 0);
        statusOfCars[idCar] = CHECKED;
        carNeededPart[idCar] = randomNum; //FIXME: Needed??
        Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Car "+idCar+" needs partType "+randomNum,mechanicId,10);
        return randomNum;
    }

    /**
     *      Checks availability of car part
     *
     *      @param partId    - ID of the part.
     *
     *      @return true if there are parts available. False otherwise.
     * */
    public synchronized boolean checkPartAvailability(int partId)
    {   assert (partId <= rangeCarPartTypes);
        return carParts[partId] != 0;
    }

    /**
     *      Checks availability of car parts and if available repairs the car
     *
     *      @param carId    - The Id of car to repair.
     *      @param partId   - The id of the car part needed for the repair.
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return true ready for repair. False otherwise
     * */
    public synchronized boolean repairCar(int carId, int partId, int mechanicId)
    {   String FUNCTION = "repairCar";
        assert (partId <= rangeCarPartTypes);
        //if(carParts[carId]>0) FIXME
        if(carParts[partId]>0)
        {   Logger.log(MECHANIC,REPAIR_AREA,"Car is ready for repair. Parts available",mechanicId,Logger.SUCCESS);
            statusOfCars[carId] = ON_REPAIR;
            carNeededPart[carId] = -1;
            carParts[partId]--;
            return true;
        }
        Logger.log(MECHANIC,REPAIR_AREA,"HEEEEEREEEE Car part "+carId+" not available. Requesting part",
                mechanicId,Logger.WARNING);
        statusOfCars[carId] = WAITING_PARTS;
        try {

            carsWaitingForParts.write(carId);

            if(carsWaitingForParts.isEmpty())
            {
                Logger.log(MECHANIC,REPAIR_AREA,"Should not happen. A car just got inside the storage",mechanicId,Logger.ERROR);
                System.exit(1);
            }
        } catch (MemException e) {
            Logger.logException(e);
            System.exit(1);
        }
        return false;
    }

    /**
     *      Checks if car there are cars waiting for parts
     *
     *      @return true - no cars waiting for parts
     *
     * */
    public synchronized boolean CarsWaitingForPartsIsEmpty()
    {
        return carsWaitingForParts.isEmpty();
    }

    /**
     *      Checks which of the waiting cars are ready for repair
     *
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return id of the car ready for repair.
     * */
    public synchronized int repairWaitingCarWithPartsAvailable(int mechanicId)
    {   String FUNCTION = "repairWaitingCarWithPartsAvailable";
        int length = carsWaitingForParts.numElements();
        int tmp = -1;
        int res = -1;
        boolean flag = false;
        while (length > 0)
        {   length--;
            try
            {   tmp = carsWaitingForParts.read();//gets

                if(reserveCarPart[tmp] != -1 && !flag)
                {   res = tmp;
                    statusOfCars[tmp] = ON_REPAIR;
                    Logger.log(REPAIR_AREA,MECHANIC,FUNCTION,"Car "+tmp+" ready for repair. Car Part "+reserveCarPart[tmp],mechanicId,Logger.SUCCESS);

                    gri.setNumCarWaitingPart(reserveCarPart[tmp], -1);  // Log minus one car needs the part

                    reserveCarPart[tmp] = -1; //part car taken
                    flag=true;
                    continue; //does not put back
                }
                carsWaitingForParts.write(tmp);//put back

            } catch (MemException e) { Logger.logException(e); }
        }
        if(length == 0 && !flag)
        {   Logger.log(REPAIR_AREA,MECHANIC,FUNCTION,"There was no parts reserved",0,Logger.ERROR);
            System.exit(1);
        }
        return res;
    }

    /**
     *      Conclude repair of the car.
     *
     *      @param idCar    - Id of the car.
     *      @param mechanicId - id of the mechanic doing the task
     * */
    public synchronized void concludeCarRepair(int idCar, int mechanicId) {
        String FUNCTION = "ConcludeCarRepair";
        if (statusOfCars[idCar] == REPAIRED)
        {
            Logger.log(REPAIR_AREA,MECHANIC,FUNCTION,"Error: Car was registered as repaired before, this should not happen",mechanicId,Logger.ERROR);
            System.exit(1);
        }
        //Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Car fixed. Register done on Repair Area",mechanicId,Logger.SUCCESS);
        statusOfCars[idCar] = REPAIRED;
        gri.setNumCarsRepaired();                                               // Log additional car repaired
    }

    /**
     *      Refill car Part stock
     *      @param idPart   - ID of car part.
     *      @param quantity - number of car parts to refill
     * */
    public synchronized  void  refillCarPartStock(int idPart, int quantity)
    {   String FUNCTION = "refillCarPartStock";
        assert idPart <= rangeCarPartTypes;

        carParts[idPart] = quantity;
        workToDo = true;
        gri.addNumPartAvailable(idPart, quantity);          // Log number of parts now available in stock
       // Logger.log(MANAGER,REPAIR_AREA,FUNCTION,"Stock Refilled.Notifying Mechanics",0,Logger.SUCCESS);
        notifyAll();        //notify sleeping mechanics
    }

    /**
     * Get maximum number of a specific part that can be stored on the repairArea
     * @param partId    - Id of the car part.
     * @return the maximum number of storage for the part
     * */
    public synchronized int getMaxPartStock(int partId)
    {   assert (partId <= rangeCarPartTypes);
        return maxCarPartsNumber[partId];
    }

    /**
     *  Mechanic checks what has to do next
     *  @param mechanicId - id of the mechanic doing the task
     *  @return the task that has to be done
     * */
    public synchronized int findNextTask(int mechanicId)
    {   String FUNCTION = "findNextTask";
        int  CONTINUE_REPAIR_CAR = 1, REPAIR_NEW_CAR = 2, WAKEN =3, GO_HOME =4;
        //Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"finding next task",mechanicId,10);
        if(workToDo || allDone)
        {   //Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"There may be work to do",mechanicId,10);
            int size = carsWaitingForParts.numElements();
            boolean flag = false;
            while(size-- >0)
            {
                try {
                    int tmpCar = carsWaitingForParts.read();//gets
                    int tmpPart;
                    if((tmpPart= carNeededPart[tmpCar]) != -1)
                    {
                        if(carParts[tmpPart] != 0) {
                            carNeededPart[tmpCar] = -1;
                            Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Reserving part "+tmpPart,mechanicId,10);
                            reserveCarPart[tmpCar] = tmpPart; //Reserve part for the car;
                            carParts[tmpPart]-=1;
                            flag = true;
                            //continue;
                            // return CONTINUE_REPAIR_CAR;
                        }
                    }
                    carsWaitingForParts.write(tmpCar);
                    if(carsWaitingForParts.numElements() == 0)
                    {
                        Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Car Waiting For Parts is empty. Should not happen "+tmpPart,mechanicId,Logger.ERROR);
                        System.exit(1);
                    }
                } catch (MemException e) {
                    //Logger.logException(e);
                    System.exit(1);
                }
            }
            if(flag)
            {   //Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"TODO: Continue repair car",mechanicId,Logger.SUCCESS);
                return CONTINUE_REPAIR_CAR;
            }
            for(int i = 0; i< carsNeedsCheck.length; i++)
            {   if(carsNeedsCheck[i])
                {   carsNeedsCheck[i] =false;
                    //Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"TODO: Check a car and repair it",mechanicId,Logger.SUCCESS);
                    return REPAIR_NEW_CAR;
                }
            }
            if(allDone)
            {
                System.out.println("RETURNING TO HOME");
                return GO_HOME;
            }

            workToDo = false;
        }
        while (!workToDo)
        {   try {
                //Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Mechanic is sleeping",mechanicId,Logger.WARNING);
                wait();
            } catch (InterruptedException e) {
                Logger.logException(e);
            }
        }
        //Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Mechanic has woken",mechanicId,Logger.SUCCESS);
        return WAKEN;
    }

    /**
     * Alerts that a new car needs to be checked.
     * @param carID the ID of the car that needs to be repaired
     * */
    public synchronized void postJob(int carID)
    {   String FUNCTION = "postJob";
        gri.setNumPostJobs();           // Log additional job posted
        //Logger.log(MANAGER,REPAIR_AREA,FUNCTION,"New car needs to be checked. Notifying managers",0,Logger.WARNING);
        carsNeedsCheck[carID] = true;
        workToDo = true;                        //notify sleeping mechanics
        notifyAll();
    }

    /**
     *  Sends the Mechanics home
     * */
    public synchronized void sendHome()
    {
        allDone = true;
        workToDo = true;
        notifyAll();
    }


}
