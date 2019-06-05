package Main;

import Interfaces.*;
import Resources.MemException;
import Resources.MemFIFO;

import java.rmi.RemoteException;
import java.util.Random;

/**
 * Repair Area Class. Used by Manager and Mechanics.
 * */
public class RepairArea implements RepairAreaInterface {

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
     * Initialize General Repository Information
     */
    private GeneralRepInterface gri;

    /**
     *      Cars waiting for parts
     *
     * */
    private volatile MemFIFO<Integer> carsWaitingForParts;

    /**
     *      Range of the IDs of car types
     *
     * */
    private int rangeCarPartTypes;

    /**
     *      Array of car's status.
     *      Index represents the id of the car and the value the state of the car.
     *
     * */
    private volatile int[] statusOfCars;

    /**
     *      Array of number of carParts to be used.
     *      Index represents the id of the car. Value represents the number of parts available.
     *
     * */

    private volatile int[] carParts;

    /**
     * Max stock possible for each car part
     * Index represents the id of the part
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
     * */
    private volatile boolean workToDo = false;

    /**
     *  Array with a flag announcing if a car needs to be checked for missing part. The index of the array is the ID of the car
     *
     * */
    private volatile boolean[] carsNeedsCheck;

    /**
     *  Array that is -1 if a car doesn't have a car part associated with it, and the ID of the part, to reserve
     *  that part to a specific car (index is the ID of the car).
     *
     * */
    private volatile int[] reserveCarPart;

    /**
     *  Flag announcing if all the tasks were done
     * */
    private volatile boolean allDone;

    /**
     * Finish flag
     * */
    public volatile boolean finish;

    /**
     * Instantiation of the Repair Area.
     * @param totalNumCars number of the total number of cars
     * @param rangeCarPartTypes range of the IDs of car types
     * @param carParts array of number of carParts to be used. Index represents the ID of the car and value represents the number of parts available.
     * @param maxCarParts max stock possible for each car part. Index represents the ID of the part
     * @param gri general repository information object.
     * */
    public RepairArea( int totalNumCars, int rangeCarPartTypes, int[] carParts, int[] maxCarParts, GeneralRepInterface gri)
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
        this.finish = false;

        for(int i = 0; i<totalNumCars; i++){
            this.statusOfCars[i] = NOT_REGISTERED;
            this.carNeededPart[i] = -1;
            this.reserveCarPart[i] = -1;
            this.carsNeedsCheck[i] = false;
        }
        try {
            this.carsWaitingForParts = new MemFIFO<>(new Integer[totalNumCars]);
        } catch (MemException e) {
            System.out.println(e);
        }
    }

    /**
     *      Checks the needed parts for the car to repair
     *
     *      @param idCar id of the car to check
     *      @param mechanicId id of the mechanic doing the task
     *
     *      @return the id of the part needed for repair
     * */
    public synchronized int checkCar(int idCar, int mechanicId)
    {   Random rand = new Random();
        int randomNum = rand.nextInt(rangeCarPartTypes);
        assert (randomNum <= rangeCarPartTypes && randomNum >= 0);
        statusOfCars[idCar] = CHECKED;
        carNeededPart[idCar] = randomNum; //FIXME: Needed??
        return randomNum;
    }


    /**
     *      Checks availability of car parts and if available repairs the car
     *
     *      @param carId The Id of car to repair.
     *      @param partId The Id of the car part needed for the repair.
     *      @param mechanicId Id of the mechanic doing the task
     *
     *      @return true ready for repair. False otherwise
     * */
    public synchronized boolean repairCar(int carId, int partId, int mechanicId) throws RemoteException {   assert (partId <= rangeCarPartTypes);
        int count = 0;
        for(int i = 0; i< carNeededPart.length; i++)
        {
            if(carNeededPart[i] == partId) { count++; }
        }

        if( carParts[partId]>=count)
        {
            statusOfCars[carId] = ON_REPAIR;
            carNeededPart[carId] = -1;
            carParts[partId]--;
            gri.removeNumPartAvailable(partId);
            return true;
        }
        statusOfCars[carId] = WAITING_PARTS;
        try {

            carsWaitingForParts.write(carId);

            if(carsWaitingForParts.isEmpty())
            {
                System.exit(1);
            }
        } catch (MemException e) {
            System.out.println(e);
            System.exit(1);
        }
        return false;
    }

    /**
     *      Checks which of the waiting cars are ready for repair
     *
     *      @param mechanicId id of the mechanic doing the task
     *
     *      @return id of the car ready for repair.
     * */
    public synchronized int repairWaitingCarWithPartsAvailable(int mechanicId) throws RemoteException {   int length = carsWaitingForParts.numElements();
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
                    gri.setNumCarWaitingPart(reserveCarPart[tmp], -1);
                    reserveCarPart[tmp] = -1; //part car taken
                    gri.removeNumPartAvailable(tmp);
                    flag=true;
                    continue; //does not put back
                }
                carsWaitingForParts.write(tmp);//put back

            } catch (MemException e) { System.out.println(e); }
        }
        if(length == 0 && !flag)
        {
            System.exit(1);
        }
        return res;
    }

    /**
     *      Conclude repair of the car.
     *
     *      @param idCar    Id of the car.
     *      @param mechanicId id of the mechanic doing the task
     * */
    public synchronized void concludeCarRepair(int idCar, int mechanicId) throws RemoteException {
        if (statusOfCars[idCar] == REPAIRED)
        {
            System.exit(1);
        }
        statusOfCars[idCar] = REPAIRED;
        gri.setCustomerCarRepaired(idCar);
        gri.setNumCarsRepaired();
    }

    /**
     *      Refill car Part stock
     *      @param idPart   ID of car part.
     *      @param quantity number of car parts to refill
     * */
    public synchronized  void  refillCarPartStock(int idPart, int quantity) throws RemoteException {   assert idPart <= rangeCarPartTypes;
        carParts[idPart] += quantity;
        workToDo = true;
        gri.addNumPartAvailable(idPart, quantity);
        notifyAll();        //notify sleeping mechanics
    }

    /**
     * Get maximum number of a specific part that can be stored on the repairArea
     * @param partId    Id of the car part.
     * @return the maximum number of storage for the part
     * */
    public synchronized int getMaxPartStock(int partId)
    {   assert (partId <= rangeCarPartTypes);
        return maxCarPartsNumber[partId];
    }

    /**
     *  Mechanic checks what has to do next
     *  @param mechanicId id of the mechanic doing the task
     *  @return the task that has to be done
     * */
    public synchronized int findNextTask(int mechanicId)
    {   int  CONTINUE_REPAIR_CAR = 1, REPAIR_NEW_CAR = 2, WAKEN =3, GO_HOME =4;
        if(workToDo || allDone)
        {   int size = carsWaitingForParts.numElements();
            boolean flag = false;

            while(size-- >0)
            {
                try {
                    int tmpCar = carsWaitingForParts.read();//gets
                    carsWaitingForParts.write(tmpCar);
                    int tmpPart;
                    if((tmpPart= carNeededPart[tmpCar]) != -1)
                    {   if(carParts[tmpPart] != 0) {
                        carNeededPart[tmpCar] = -1;
                        reserveCarPart[tmpCar] = tmpPart; //Reserve part for the car;
                        carParts[tmpPart]--;
                        flag = true;
                        break;
                    }
                    }
                    if(carsWaitingForParts.numElements() == 0)
                    {
                        System.exit(1);
                    }
                } catch (MemException e) {
                    System.out.println(e);
                    System.exit(1);
                }
            }
            if(flag)
            {
                return CONTINUE_REPAIR_CAR;
            }
            for(int i = 0; i< carsNeedsCheck.length; i++)
            {   if(carsNeedsCheck[i])
            {   carsNeedsCheck[i] =false;
                return REPAIR_NEW_CAR;
            }
            }
            if(allDone)
            {   return GO_HOME;
            }

            workToDo = false;
        }
        while (!workToDo)
        {   try {
            wait();
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        }
        return WAKEN;
    }

    /**
     * Alerts that a new car needs to be checked.
     * @param carID the ID of the car that needs to be repaired
     * */
    public synchronized void postJob(int carID) throws RemoteException {   gri.setNumPostJobs();
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

    /**
     * Terminates Repair Area Server
     * */
    public synchronized void finish()
    {   this.finish = true;
    }


}
