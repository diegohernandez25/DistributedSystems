package SharedRegions;

import Interfaces.*;

public class Park implements CustomerPark, MechanicPark {

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
    {   cars = new boolean[numSlots]; // numSlots = numCustomerCars + numReplaceCars
        for(int i = 0;i<numSlots; i++)
        {   cars[i] = false;
        }
        for(int i = 0; i<parkCars.length; i++)
        {   cars[parkCars[i]] = true;
        }
    }

    /**
     *  Park Car.
     *  @param carId ID of the car.
     * */
    public synchronized void parkCar(Integer carId)
    {   assert(carId < cars.length);
        if(cars[carId])
        {   System.out.println("Error: Car is already parked.");
            System.exit(1);
        }
        cars[carId] = true;
    }

    /**
     *  Gets Car.
     *  @param carId id of the car.
     *  @return id of the car
     * */
    public synchronized Integer getCar(Integer carId)
    {   assert(carId < cars.length);
        if(!cars[carId])
        {   System.out.println("Error: car is not parked.");
            System.exit(1);
        }
        cars[carId] = false;
        return carId;
    }
}
