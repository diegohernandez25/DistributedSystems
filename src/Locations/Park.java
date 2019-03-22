package Locations;

import java.util.HashMap;

public class Park {

    private static boolean[] cars;
    /**
     * Instantiation of the Park.
     * */
    public Park(int numSlots)
    {
        cars = new boolean[numSlots]; // numSlots = numCustomerCars + numReplaceCars
    }

    /**
     *      Park Car.
     *
     *      @return Operation status.
     * */
    public synchronized boolean parkCar(Integer carId)
    {
        assert(carId < cars.length);
        if(cars[carId]) return false;
        cars[carId] = true;
        return true;
    }
    /**
     *      Gets Car.
     *
     *      @return the car.
     * */
    public synchronized Integer getCar(Integer carId)
    {
        assert(carId < cars.length);
        if(!cars[carId]) return -1;
        cars[carId] = false;
        return carId;
    }


}
