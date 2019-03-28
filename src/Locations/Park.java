package Locations;

import Loggers.Logger;

import java.util.HashMap;

public class Park {
    /**
     * Constants
     * */
    public static String    LOCAL       = "Park";

    /**
     * Array of cars with the purpose of knowing if car is parked or not. Index is the id of the car, value is a boolean
     * true - car is parked; false - otherwise.
     * @serialField cars.
     * */
    private static boolean[] cars;

    /**
     * Instantiation of the Park.
     * @param numSlots - number of slots of the parking lot.
     * @param parkCars - parks that are already parked for default.
     * */
    public Park(int numSlots, int[] parkCars)
    {
        cars = new boolean[numSlots]; // numSlots = numCustomerCars + numReplaceCars
        for(int i = 0;i<numSlots; i++)
        {
            cars[i] = false;
        }
        for(int i = 0; i<parkCars.length; i++)
        {
            cars[parkCars[i]] = true;
        }
        printParkingLot();
    }

    /**
     *  Park Car.
     *  @param carId - ID of the car.
     *  @param id - ID of customer or mechanic. This is just for the Logger
     *  @return Operation status.
     * */
    public synchronized boolean parkCar(Integer carId, int id)
    {   String FUNCTION = "parkCar";
        assert(carId < cars.length);
        //Logger.log(LOCAL,LOCAL,FUNCTION,"Parking car "+carId,id,10);
        printParkingLot();
        if(cars[carId])
        {
            Logger.log(LOCAL,LOCAL,FUNCTION,"Car is already parked. This should not happend",id,Logger.ERROR);
            System.exit(1);
            return false;
        }
        //Logger.log(LOCAL,LOCAL,FUNCTION,"Car parked.",id,//Logger.SUCCESS);
        cars[carId] = true;
        printParkingLot();
        return true;
    }

    /**
     *  Gets Car.
     *  @param carId - id of the car.
     *  @param id - id of the car
     *  @return the car.
     * */
    public synchronized Integer getCar(Integer carId, int id)
    {   String FUNCTION = "getCar";
        printParkingLot();
        assert(carId < cars.length);
        //Logger.log(LOCAL,LOCAL,FUNCTION,"Getting Car"+carId,id,10);
        if(!cars[carId])
        {   Logger.log(LOCAL,LOCAL,FUNCTION,"Car is not parked. This should not happend",id,Logger.ERROR);
            System.exit(1);
            return -1;
        }
        cars[carId] = false;
        printParkingLot();
        return carId;
    }

    /**
     *  Print Parking Lot
     * */
    public synchronized void printParkingLot()
    {
        String tmp = "[";
        for(int i = 0; i<cars.length;i++)
        {
            tmp+=cars[i]+", ";
        }
    }


}
