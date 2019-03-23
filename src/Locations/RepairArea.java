package Locations;

import Loggers.Logger;
import Resources.MemException;
import Resources.MemFIFO;
import java.util.Random;

public class RepairArea
{

    /**
     *
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


    /**
     *
     * Instantiation of the Lounge
     *      @param rangeCarPartTypes - Range of car types.
     *      @param totalNumCars - total number of cars
     * */
    public RepairArea(int rangeCarPartTypes, int totalNumCars, int[] carParts, int[] maxCarPartsNumber)
    {
        this.rangeCarPartTypes = rangeCarPartTypes;
        this.carParts = carParts;
        this.statusOfCars = new int[totalNumCars];
        this.carNeededPart = new int[totalNumCars];
        this.maxCarPartsNumber = maxCarPartsNumber;
        for(int i = 0; i<totalNumCars; i++){
            this.statusOfCars[i] = NOT_REGISTERED;
            this.carNeededPart[i] = -1;
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
    public int checkCar(int idCar)
    {   Random rand = null;
        int randomNum = rand.nextInt(rangeCarPartTypes)+1;
        assert (randomNum <= rangeCarPartTypes && randomNum >= 0);
        statusOfCars[idCar] = CHECKED;
        carNeededPart[idCar] = randomNum; //FIXME: Needed??
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
    public synchronized boolean repairCar(int carId, int partId)
    {
        assert (partId <= rangeCarPartTypes);
        if(carParts[carId]>0)
        {
            statusOfCars[carId] = ON_REPAIR;
            carNeededPart[carId] = -1;
            carParts[partId]--;
            return true;
        }
        statusOfCars[carId] = WAITING_PARTS;    //else
        try {
            carsWaitingForParts.write(carId);
        } catch (MemException e) {
            Logger.logException(e);
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
    public synchronized int repairWaitingCarWithPartsAvailable()
    {   String REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE = "repairWaitingCarWithPartsAvailable";
        int length = carsWaitingForParts.size();
        Logger.log(REPAIR_AREA,MECHANIC,REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE,
                "Queue: "+carsWaitingForParts.toString(),0,Logger.WARNING);
        int tmp = -1;
        while (length >= 0)
        {   length--;
            try
            {   tmp = carsWaitingForParts.read();
                if(checkPartAvailability(carNeededPart[tmp])) {
                    statusOfCars[tmp] = ON_REPAIR;
                    if(carParts[carNeededPart[tmp]] == 0)
                    {   Logger.log(REPAIR_AREA,MECHANIC,REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE,
                                "ERROR: There is no parts to fix the car. THis should not happen!",0,Logger.ERROR);
                    }
                    carNeededPart[tmp] = -1;
                    carParts[carNeededPart[tmp]]--;
                    break;
                }
                carsWaitingForParts.write(tmp);
            } catch (MemException e) { Logger.logException(e); }
        }
        while (length >= 0)                                                 //Re-establish arrival order.
        {   length--;
            try { carsWaitingForParts.write(carsWaitingForParts.read()); }
            catch (MemException e) { Logger.logException(e); }
        }
        Logger.log(REPAIR_AREA,MECHANIC,REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE,
                "Queue: "+carsWaitingForParts.toString(),0,Logger.WARNING);
        return tmp;                                                         //returns the id of the car which got repaired.
    }

    /**
     *      Conclude repair of the car.
     *
     *      @param idCar    - Id of the car.
     * */
    public void concludeCarRepair(int idCar) {
        String CONCLUDE_CAR_REPAIR = "Conclude Car Repair";
        if (statusOfCars[idCar] == REPAIRED)
        {
            Logger.log(REPAIR_AREA,MECHANIC,CONCLUDE_CAR_REPAIR,
                    "Error: Car was registeres as repaired before, this should not happen",0,Logger.ERROR);
            System.exit(1);
        }
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
    public boolean refillCarPartStock(int idPart, int quantity)
    {
        assert idPart <= rangeCarPartTypes;
        if(carParts[idPart] == 0) {
            carParts[idPart] = quantity;
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
    {
        assert (partId <= rangeCarPartTypes);
        return maxCarPartsNumber[partId];
    }
}
