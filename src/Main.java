import Entities.Customer;
import Entities.Manager;
import Entities.Mechanic;
import Locations.*;
import Loggers.Logger;

import java.util.Scanner;

public class Main {

    public static final int NUM_CLIENTS = 30,
                            NUM_MECHANICS = 2,
                            NUM_REPLACEMENT_CARS = 3,
                            NUM_PART_TYPES = 3;

    public static void main(String[] args)
    {
        OutsideWorld outsideWorld;
        Lounge lounge;
        Park park;
        RepairArea repairArea;
        SupplierSite supplierSite;

        Scanner sc = new Scanner(System.in);


        int numClients = NUM_CLIENTS;
        int numMechanics = NUM_MECHANICS;
        int numReplacementCars = NUM_REPLACEMENT_CARS;
        int stockLength = NUM_PART_TYPES;

        /**
         *      Cars initiation.
         * */
        int totalCars = numClients + numReplacementCars;
        int i = 0;
        Integer[] arrayCustomerCars = new Integer[numClients];
        Integer[] arrayReplacementCars = new Integer[numReplacementCars];
        Integer[] arrayAllCars = new Integer[totalCars];
        for(;i < numClients; i++) {
            arrayCustomerCars[i] = i;
            arrayAllCars[i] = i;
        }
        for(;i < totalCars; i++) {
            arrayReplacementCars[i - numClients] = i;
            arrayAllCars[i] = i;
        }



        //lounge = new Lounge(arrayReplacementCars,)

        Customer[] customer = new Customer[numClients];

        Mechanic[] mechanic = new Mechanic[numMechanics];

        Manager manager = new Manager(0,lounge, supplierSite);

        /**
         *      Create Customers
         * */
        for(; i<numClients; i++)
            customer[i] = new Customer(i,Math.random() < 0.5, numIter,i,lounge,park,outsideWorld);

        /**
         *      Create Mechanics
         * */
        i = 0;
        for(; i<numMechanics; i++)
            mechanic[i] = new Mechanic(i,lounge,park,repairArea);

        /**
         *      Deploy Customer Simulation
         * */
        i = 0;
        for(;i<numClients; i++)
            customer[i].start();

        /**
         *      Deploy Mechanic Simulation
         * */
        i = 0;
        for(;i<numMechanics; i++)
        {
            mechanic[i].start();
        }

        /**
         *      Waiting for the simulation to end
         * */
        i = 0;
        for(;i<numClients; i++) {
            try {
                customer[i].join();
                mechanic[i].join();
            } catch (InterruptedException e) {
                Logger.logException(e);
            }
        }

        try {
            outsideWorld = new OutsideWorld(numClients);
        } catch (Exception e) {
            Logger.logException(e);
        }



    }
}
