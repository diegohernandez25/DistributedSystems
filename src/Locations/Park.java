package Locations;

import java.util.HashMap;

public class Park {

    //private static HashMap<String, Car> cars;
    private static boolean[] cars;
    /**
     * Instantiation of the Park.
     * */
    //public Park()
    public Park(int numSlots)
    {
        //cars = new HashMap<>();
        cars = new boolean[numSlots]; // numSlots = numCustomerCars + numReplaceCars
    }

    /**
     *  Park Car.
     *      @return Operation status.
     * */
    /*public synchronized boolean parkCar(String id, Car car)
    {

        if(cars.containsKey(id)){ return false; }
        cars.put(id,car);
        return true;

    }*/
    public synchronized boolean parkCar(Integer carId)
    {
        assert(carId < cars.length);
        if(cars[carId]) return false;
        cars[carId] = true;
        return true;
    }
    /**
     *  Gets Car.
     *
     *
     *      @return the car.
     * */
    /*public synchronized Car getCar(String id)
    {
        if(!cars.containsKey(id)){ return null; }

        return cars.remove(id);
    }
    */
    public synchronized Integer getCar(Integer carId)
    {
        assert(carId < cars.length);
        if(!cars[carId]) return -1;
        cars[carId] = false;
        return carId;
    }


}
