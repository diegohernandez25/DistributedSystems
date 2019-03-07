package Locations;

import Objects.CarPart;

import java.util.ArrayList;

public class SupplierSite<R> {

    public SupplierSite(){}

    /**
     * @param name - Name of the part of the car.
     * @param number - Total number of parts.
     * @return all requested parts
     * */
    public ArrayList<CarPart> getParts(String name, int number)
    {
        ArrayList<CarPart> tmp = new ArrayList<>();
        for( int i = 0; i< number; i++)
        {
            tmp.add(new CarPart(name));
        }
        return tmp;
    }
}
