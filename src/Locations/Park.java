package Locations;

import Objects.Car;
import java.util.HashMap;

public class Park {

    private static HashMap<String, Car> cars;
    /**
     * Instantiation of the Park.
     * */
    public Park()
    {
        cars = new HashMap<>();
    }

    /**
     *  Park Car.
     *
     *      @param id - ID of the car key.
     *      @param car - The car object.
     *
     *      @return Operation status.
     * */
    public synchronized boolean parkCar(String id, Car car)
    {

        if(cars.containsKey(id)){ return false; }
        cars.put(id,car);
        return true;
    }

    /**
     *  Gets Car.
     *
     *      @param id - ID of the car key.
     *
     *      @return the car.
     * */
    public synchronized Car getCar(String id)
    {
        if(!cars.containsKey(id)){ return null; }

        return cars.remove(id);
    }

}
