package Locations;

import Loggers.Logger;
import Resources.MemException;
import Resources.MemFIFO;
import java.util.Random;

public class RepairArea
{

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
    public RepairArea(int rangeCarPartTypes, int totalNumCars, int[] carParts)
    {
        this.rangeCarPartTypes = rangeCarPartTypes;
        this.carParts = carParts;
        this.statusOfCars = new int[totalNumCars];
        this.carNeededPart = new int[totalNumCars];
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
    public synchronized int getWaitingCarWithPartsAvailable()
    {
        int length = carsWaitingForParts.size();
        int tmp = -1;
        while (length >= 0)
        {   length--;
            try
            {   tmp = carsWaitingForParts.read();
                if(checkPartAvailability(carNeededPart[tmp])) {
                    statusOfCars[tmp] = ON_REPAIR;
                    carNeededPart[tmp] = -1;
                    carParts[carNeededPart[tmp]]--;
                    break;
                }
                carsWaitingForParts.write(tmp);
            } catch (MemException e) { Logger.logException(e); }
        }
        while (length >= 0)//Re-establish arrival order.
        {   length--;
            try { carsWaitingForParts.write(carsWaitingForParts.read()); }
            catch (MemException e) { Logger.logException(e); }
        }
        return tmp; //returns the id of the car which got repaired.
    }

    /**
     *      Conclude repair of the car.
     *
     *      @param idCar    - Id of the car.
     * */
    public void concludeCarRepair(int idCar)
    {
        statusOfCars[idCar] = REPAIRED;
    }














}
