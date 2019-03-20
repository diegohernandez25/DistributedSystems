import Entities.Customer;
import Entities.Manager;
import Entities.Mechanic;
import Locations.*;
import Loggers.Logger;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        OutsideWorld outsideWorld;
        Lounge lounge;
        Park park;
        RepairArea repairArea;
        SupplierSite supplierSite;

        Scanner sc = new Scanner(System.in);

        System.out.print("Number of clients: ");
        int numClients = sc.nextInt();
        assert numClients > 0;

        System.out.print("Number of interactions: ");
        int numIter = sc.nextInt();
        assert numIter > 0;

        System.out.print("Number of mechanics: ");
        int numMechanics = sc.nextInt();
        assert numMechanics > 0;

        System.out.print("Number of replacement cars: ");
        int numReplacementCars = sc.nextInt();
        assert numReplacementCars > 0;

        System.out.print("Stock length: ");
        int stockLength = sc.nextInt();
        assert stockLength > 0;

        //TODO initiate all types of keys
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
