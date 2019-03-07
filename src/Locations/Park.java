package Locations;

import Objects.Car;
import Objects.ReplacementCar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Park {

    private static HashMap<String, Car> Cars;
    //private static ArrayList<ReplacementCar> replacementCars; //FIXME Am I allowed to do this??

    public Park(ReplacementCar[] cars)
    {
        Cars = new HashMap<>();
        //replacementCars = new ArrayList<ReplacementCar>(Arrays.asList(cars));
    }

    public boolean parkCar(String id, Car car)
    {
        if(Cars.containsKey(id)){ return false; }
        Cars.put(id,car);
        return true;
    }

    public Car getsCar(String id)
    {
        if(!Cars.containsKey(id)){ return null; }
        return Cars.get(id);
    }

    public boolean isParkEmpty()
    {
        return Cars.isEmpty();
    }


}
