package Main;

import Interfaces.*;

import java.rmi.RemoteException;

public class Park implements ParkInterface{
    /**
     * Initialize General Repository Information
     */
    private volatile GriPark gri;

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
     * @param gri general repository information object
     * */
    public Park(int numSlots, int[] parkCars, GriPark gri)
    {
        this.gri = gri;

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
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    public synchronized void parkCar(Integer carId, int id, boolean customerPark) throws RemoteException {
        assert(carId < cars.length);
        if(cars[carId])
        {   System.out.println("Error: Car is already parked.");
            System.exit(1);
        }
        cars[carId] = true;

        if(customerPark)                                    // if it was the Customer who parked
        {
            if (carId <= cars.length - numReplacement)      // if the parked car was the Customers
            {
                gri.setNumCarsParked(1);                    // Logs Customer parked his car
                gri.setCustomerVehicle(id, "-");    // Logs Customer doesn't have their car anymore
            }
            else                                            // if the parked car was a Replacement
            {
                gri.setNumReplacementParked(1);             // Logs replacement car parking in park
                gri.setCustomerVehicle(id, "-");    // Logs Customer doesn't have replacement car anymore
            }
        }
        else                                                // if it was the Mechanic who parked
        {
            gri.setNumCarsParked(1);                        // Log Customer car parked (the Mechanic only parks
            // Customer's Cars, not Replacements)
        }
    }

    /**
     *  Gets Car.
     *  @param carId id of the car.
     *  @param id customer id.
     *  @param customerGet flag if it was a customer who got the car
     *  @return id of the car
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    public synchronized Integer getCar(Integer carId, int id, boolean customerGet) throws RemoteException {   assert(carId < cars.length);
        if(!cars[carId])
        {   System.out.println("Error: car is not parked.");
            System.exit(1);
        }
        cars[carId] = false;

        if(customerGet)                                         // if it was the Customer who got the car
        {
            if (carId <= cars.length - numReplacement)          // if the car was the Customers
            {
                gri.setNumCarsParked(-1);                       // Logs Customer removed their car from park
                gri.setCustomerVehicle(id, String.valueOf(id)); // Logs Customer has their own car again
            }
            else                                                // if the car was a Replacement
            {
                gri.setNumReplacementParked(-1);                // Logs replacement car removed from park
                gri.setCustomerVehicle(id,                      // Logs Customer changing cars to a replacement car
                        "R"+(carId-(cars.length - numReplacement)));
            }
        }
        else                                                    // if it was the Mechanic who got the car
        {
            gri.setNumCarsParked(-1);                           // Log Customer car removed (the Mechanic only removes
            // Customer's Cars, not Replacements)
        }

        return carId;
    }

    /**
     * Terminates Park Information Server
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    public synchronized void finish() throws RemoteException
    {   this.finish = true;
        notifyAll();
    }
}
