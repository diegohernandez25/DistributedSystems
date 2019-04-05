package Locations;

import Loggers.Logger;
import Interfaces.*;

public class Park implements CustomerPark, MechanicPark {
    /**
     * Constants
     * */
    private static String    LOCAL       = "Park";

    /**
     *  Initialize GeneralRepInformation
     * */
    private GriPark gri;

    /**
     *  Number of Replacement Cars
     *  @serialField numReplacement
     * */
    private int numReplacement;

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
     * @param gri - used General Repository Information
     * */
    public Park(int numSlots, int[] parkCars, GriPark gri)
    {
        this.gri = gri;

        this.numReplacement = parkCars.length;

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
     *  @param carId ID of the car.
     *  @param id ID of customer or mechanic. This is just for the Logger
     *  @param customerPark (true/false) if it was the Customer parking the car. GeneralRep only
     *  @return Operation status.
     * */
    public synchronized boolean parkCar(Integer carId, int id, boolean customerPark)
    {   String FUNCTION = "parkCar";
        assert(carId < cars.length);
        //Logger.log(LOCAL,LOCAL,FUNCTION,"Parking car "+carId,id,10);
        printParkingLot();
        if(cars[carId])
        {
            Logger.log(LOCAL,LOCAL,FUNCTION,"Car is already parked. This should not happened",id,Logger.ERROR);
            System.exit(1);
            return false;
        }

        //Logger.log(LOCAL,LOCAL,FUNCTION,"Car parked.",id,//Logger.SUCCESS);

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

        printParkingLot();
        return true;
    }

    /**
     *  Gets Car.
     *  @param carId id of the car.
     *  @param id id of the Mechanic or Customer
     *  @param customerGet if it was the Customer getting the Car
     *  @return id of the car
     * */
    public synchronized Integer getCar(Integer carId, int id, boolean customerGet)
    {
        String FUNCTION = "getCar";
        printParkingLot();
        assert(carId < cars.length);
        //Logger.log(LOCAL,LOCAL,FUNCTION,"Getting Car"+carId,id,10);
        if(!cars[carId])
        {   Logger.log(LOCAL,LOCAL,FUNCTION,"Car is not parked. This should not happened",id,Logger.ERROR);
            System.exit(1);
            return -1;
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
