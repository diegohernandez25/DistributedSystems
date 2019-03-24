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
    private static int  NOT_REGISTERED = 0,
                        CHECKED = 1,
                        WAITING_PARTS = 2,
                        ON_REPAIR = 3,
                        REPAIRED = 4;
    /**
     *      Cars waiting for parts
     *
     *      @serialField carsWaitingForParts
     * */
    private MemFIFO<Integer> carsWaitingForParts;

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
    private int[] statusOfCars;

    /**
     *      Array of number of carParts to be used.
     *      Index represents the id of the car. Value represents the number of parts available.
     *
     *      @serialField carParts
     * */

    private int[] carParts;

    /**
     * Max stock possible for each car part
     * Index represents the id of the part
     * @serialField maxCarParts;
     * */
    private int[] maxCarPartsNumber;

    /**
     *      Array with the id part type.
     *      Index represents the id of the car.
     *      Value the needed part for the repair.
     * */
    private int[] carNeededPart;

    private boolean workToDo = false;

    private boolean[] carsNeedsCheck;

    private int[] reserveCarPart;


    /**
     *
     * */
    public RepairArea( int totalNumCars, int rangeCarPartTypes, int[] carParts, int[] maxCarParts)
    {
        this.rangeCarPartTypes = rangeCarPartTypes;
        this.carParts = carParts;
        this.statusOfCars = new int[totalNumCars];
        this.carNeededPart = new int[totalNumCars];
        this.maxCarPartsNumber = maxCarParts;
        this.carsNeedsCheck = new boolean[totalNumCars];
        for(int i = 0; i<totalNumCars; i++){
            this.statusOfCars[i] = NOT_REGISTERED;
            this.carNeededPart[i] = -1;
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
     *
     *      @return the id of the part needed for repair
     * */
    public synchronized int checkCar(int idCar, int mechanicId)
    {   String FUNCTION = "checkCar";
        Random rand = new Random();
        int randomNum = rand.nextInt(rangeCarPartTypes)+1;
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
     *
     *      @return true ready for repair. False otherwise
     * */
    public synchronized boolean repairCar(int carId, int partId, int mechanicId)
    {   String FUNCTION = "repairCar";
        assert (partId <= rangeCarPartTypes);
        if(carParts[carId]>0)
        {   Logger.log(MECHANIC,REPAIR_AREA,"Car is ready for repair. Parts available",mechanicId,Logger.SUCCESS);
            statusOfCars[carId] = ON_REPAIR;
            carNeededPart[carId] = -1;
            carParts[partId]--;
            return true;
        }
        Logger.log(MECHANIC,REPAIR_AREA,"Car part "+carId+" not available. Requesting part",
                mechanicId,Logger.WARNING);
        statusOfCars[carId] = WAITING_PARTS;
        try {
            carsWaitingForParts.write(carId);
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
     *      @return id of the car ready for repair.
     * */
    public synchronized int repairWaitingCarWithPartsAvailable(int mechanicId)
    {   String FUNCTION = "repairWaitingCarWithPartsAvailable";
        int length = carsWaitingForParts.numElements();
        Logger.log(REPAIR_AREA,MECHANIC,FUNCTION,
                "Queue: "+carsWaitingForParts.toString(),0,Logger.WARNING);
        int tmp = -1;
        while (length >= 0)
        {   length--;
            try
            {   tmp = carsWaitingForParts.read();
                if(reserveCarPart[tmp] != -1)
                {   statusOfCars[tmp] = ON_REPAIR;
                    Logger.log(REPAIR_AREA,MECHANIC,FUNCTION,
                            "Car "+tmp+" ready for repair. Car Part "+reserveCarPart[tmp],
                            mechanicId,Logger.SUCCESS);
                    reserveCarPart[tmp] = -1; //part car taken
                    continue;
                }
                carsWaitingForParts.write(tmp);
            } catch (MemException e) { Logger.logException(e); }
        }
        if(length == -1)
        {   Logger.log(REPAIR_AREA,MECHANIC,FUNCTION,
                    "There was no parts reserved",0,Logger.ERROR);
            System.exit(1);
        }
        Logger.log(REPAIR_AREA,MECHANIC,FUNCTION,
                "Queue: "+carsWaitingForParts.toString(),0,Logger.WARNING);
        return tmp;
    }

    /**
     *      Conclude repair of the car.
     *
     *      @param idCar    - Id of the car.
     * */
    public synchronized void concludeCarRepair(int idCar, int mechanicId) {
        String FUNCTION = "ConcludeCarRepair";
        if (statusOfCars[idCar] == REPAIRED)
        {
            Logger.log(REPAIR_AREA,MECHANIC,FUNCTION,
                    "Error: Car was registered as repaired before, this should not happen"
                    ,mechanicId,Logger.ERROR);
            System.exit(1);
        }
        Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Car fixed. Register done on Repair Area"
                ,mechanicId,Logger.SUCCESS);
        statusOfCars[idCar] = REPAIRED;
    }

    /**
     *      Refill car Part stock
     *
     *      @param idPart   - ID of car part.
     *      @param quantity - number of car parts to refill
     *
     *      @return true - refill done to accordance. false - There shouldn't be a refill.
     * */
    public synchronized  boolean  refillCarPartStock(int idPart, int quantity)
    {   String FUNCTION = "refillCarPartStock";
        assert idPart <= rangeCarPartTypes;
        Logger.log(MANAGER,REPAIR_AREA,FUNCTION,"Refilling part: "+idPart+". number: "+quantity,0,10);
        if(carParts[idPart] == 0) {
            carParts[idPart] = quantity;
            workToDo = true;
            Logger.log(MANAGER,REPAIR_AREA,FUNCTION,"Stock Refilled.Notifying Mechanics"+quantity,
                    0,Logger.SUCCESS);
            notifyAll();        //notify sleeping mechanics
            return true;
        }
        Logger.log(MANAGER,REPAIR_AREA,"Error: Car parts is not empty!",0,Logger.ERROR);
        System.exit(1);
        return false;
    }

    /**
     * Get maximum number of a specific part that can be stored on the repairArea
     * @param partId    - Id of the car part.
     * @return the maximum number of storage for the part
     * */
    public int getMaxPartStock(int partId)
    {   assert (partId <= rangeCarPartTypes);
        return maxCarPartsNumber[partId];
    }

    public synchronized int findNextTask(int mechanicId)
    {   String FUNCTION = "findNextTask";
        int  CONTINUE_REPAIR_CAR = 1, REPAIR_NEW_CAR = 2, WAKEN =3;
        Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"finding next task",mechanicId,10);
        if(workToDo)
        {   Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"There may be work to do",mechanicId,10);
            Integer[] tmpWaitPartCarts = carsWaitingForParts.getStorage();
            for(int i: tmpWaitPartCarts)
            {
                int tmpPart;
                if((tmpPart = carNeededPart[i]) != -1)
                {   if(carParts[tmpPart] != 0) {
                        carNeededPart[i] = -1;
                        reserveCarPart[i] = tmpPart; //Reserve part for the car;
                        carParts[tmpPart]-=1;
                        Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"TODO: Continue repair car",mechanicId,Logger.SUCCESS);
                        return CONTINUE_REPAIR_CAR;
                    }
                }
            }
            for(int i = 0; i< carsNeedsCheck.length; i++)
            {   if(carsNeedsCheck[i])
                {   carsNeedsCheck[i] =false;
                    Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"TODO: Check a car and repair it",
                            mechanicId,Logger.SUCCESS);
                    return REPAIR_NEW_CAR;
                }
            }
            workToDo = false;
        }
        while (!workToDo)
        {   try {
                Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Mechanic is sleeping",mechanicId,Logger.WARNING);
                wait();
            } catch (InterruptedException e) {
                Logger.logException(e);
            }
        }
        Logger.log(MECHANIC,REPAIR_AREA,FUNCTION,"Mechanic has woken",mechanicId,Logger.SUCCESS);
        return WAKEN;
    }

    /**
     * Alerts that a new car needs to be checked.
     * */
    public synchronized void postJob(int carID)
    {   String FUNCTION = "postJob";
        Logger.log(MANAGER,REPAIR_AREA,FUNCTION,"New car needs to be checked. Notifying managers",
                0,Logger.WARNING);
        carsNeedsCheck[carID] = true;
        workToDo = true;                        //notify sleeping mechanics
        notifyAll();
    }

}
