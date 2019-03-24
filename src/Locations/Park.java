package Locations;

import Loggers.Logger;

import java.util.HashMap;

public class Park {
    public static String    LOCAL       = "Park";


    private static boolean[] cars;
    /**
     * Instantiation of the Park.
     * */
    public Park(int numSlots, int[] parkCars)
    {
        cars = new boolean[numSlots]; // numSlots = numCustomerCars + numReplaceCars
        for(int i = 0; i<parkCars.length; i++)
        {
            parkCar(i,-1);
        }
    }

    /**
     *Park Car.
     * @id of customer or mechanic. This is just for the logger
     *@return Operation status.
     * */
    public synchronized boolean parkCar(Integer carId, int id)
    {   String FUNCTION = "parkCar";
        assert(carId < cars.length);
        Logger.log(LOCAL,LOCAL,FUNCTION,"Parking car",id,10);
        if(cars[carId])
        {
            Logger.log(LOCAL,LOCAL,FUNCTION,"Car is already parked. This should not happend",id,Logger.ERROR);
            System.exit(1);
            return false;
        }
        Logger.log(LOCAL,LOCAL,FUNCTION,"Car parked.",id,Logger.SUCCESS);
        cars[carId] = true;
        return true;
    }
    /**
     *Gets Car.
     *@return the car.
     * */
    public synchronized Integer getCar(Integer carId, int id)
    {   String FUNCTION = "getCar";
        assert(carId < cars.length);
        Logger.log(LOCAL,LOCAL,FUNCTION,"Getting Car",id,10);
        if(!cars[carId])
        {   Logger.log(LOCAL,LOCAL,FUNCTION,"Car is not parked. This should not happend",id,Logger.ERROR);
            System.exit(1);
            return -1;
        }
        cars[carId] = false;
        return carId;
    }


}
