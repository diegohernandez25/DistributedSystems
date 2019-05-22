package Main;

import Interfaces.*;

public class Park implements ParkInterface{

    /**
     * Initialize General Repository Information
     */
    //TODO: private volatile GriPark gri;

    /**
     *  Number of Replacement Cars
     * */
    private volatile int numReplacement;

    /**
     * Array of cars with the purpose of knowing if car is parked or not. Index is the id of the car, value is a boolean
     * true - car is parked; false - otherwise.
     * */
    private volatile static boolean[] cars;


    /**
     * Finish flag
     * */
    public volatile boolean finish;

    /**
     * Instantiation of the Park.
     * @param numSlots number of slots of the parking lot.
     * @param parkCars parks that are already parked for default.
     * */
    public Park(int numSlots, int[] parkCars)
    {

        this.numReplacement = parkCars.length;

        this.finish =false;

        cars = new boolean[numSlots]; // numSlots = numCustomerCars + numReplaceCars
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
     *  @param id customer id.
     *  @param customerPark flag if it was a customer who parked the car
     * */
    public synchronized void parkCar(Integer carId, int id, boolean customerPark)
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
     *  @param id customer id.
     *  @param customerGet flag if it was a customer who got the car
     *  @return id of the car
     * */
    public synchronized Integer getCar(Integer carId, int id, boolean customerGet)
    {   assert(carId < cars.length);
        if(!cars[carId])
        {   System.out.println("Error: car is not parked.");
            System.exit(1);
        }
        cars[carId] = false;
        return carId;
    }

    /**
     * Terminates Park Information Server
     * */
    public synchronized void finish()
    {   this.finish = true;
    }
}
