package Locations;

import Objects.Car;
import Objects.ReplacementCar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Park {

    /**
     * @brief Storage of all type of cars
     * @key String Key ID.
     * @value Car or Replacement Car.
     * */
    private static HashMap<String, Car> cars;

    public Park()
    {
        cars = new HashMap<>();
    }

    public boolean parkCar(String id, Car car)
    {
        //TODO: mutex
        if(cars.containsKey(id)){ return false; }
        cars.put(id,car);
        return true;
    }

    public Car getCar(String id)
    {
        //TODO: mutex
        if(!cars.containsKey(id)){ return null; }
        return cars.get(id);
    }

    public boolean isParkEmpty() //TODO Is it necessary?
    {
        return cars.isEmpty();
    }

}
