import Entities.*;
import Interfaces.*;
import Locations.*;
import Loggers.Logger;

import java.util.Scanner;

public class Main {

    public static final int NUM_CLIENTS = 32,
                            NUM_MECHANICS = 2,
                            NUM_REPLACEMENT_CARS = 3,
                            NUM_PART_TYPES = 3;

    public static void main(String[] args)
    {
        int[] carParts = {0,0,0};
        int[] maxCarParts = {1,1,1};

        GeneralRepInformation gri = new GeneralRepInformation(NUM_CLIENTS, NUM_MECHANICS, NUM_PART_TYPES, carParts, "log.txt");

        OutsideWorld outsideWorld = new OutsideWorld(NUM_CLIENTS);

        int[] replacementCarKeys = new int[NUM_REPLACEMENT_CARS];
        for(int i = NUM_CLIENTS; i< NUM_CLIENTS + NUM_REPLACEMENT_CARS; i++)
        {
            replacementCarKeys[i-NUM_CLIENTS] = i;
        }

        Lounge lounge = new Lounge(NUM_CLIENTS, NUM_MECHANICS,replacementCarKeys,NUM_PART_TYPES, (GriLounge) gri);

        Park park = new Park(NUM_REPLACEMENT_CARS+NUM_CLIENTS, replacementCarKeys, (GriPark) gri);
        RepairArea repairArea = new RepairArea(NUM_CLIENTS, NUM_PART_TYPES, carParts, maxCarParts, (GriRA) gri);

        SupplierSite supplierSite = new SupplierSite(NUM_PART_TYPES, (GriSS) gri);

        Customer[] customer = new Customer[NUM_CLIENTS];
        for(int i =0 ; i<NUM_CLIENTS; i++)
            customer[i] = new Customer(i,Math.random() < 0.5,i,(CustomerLounge) lounge,(CustomerPark) park,(CustomerOW) outsideWorld);

        Mechanic[] mechanic = new Mechanic[NUM_MECHANICS];
        for(int i = 0; i<NUM_MECHANICS;i++)
            mechanic[i] = new Mechanic(i,(MechanicLounge) lounge,(MechanicPark) park,(MechanicRA) repairArea);

        Manager manager = new Manager(0,(ManagerLounge) lounge, (ManagerSS) supplierSite, (ManagerOW) outsideWorld, (ManagerRA) repairArea);

        for(int i = 0;i<NUM_MECHANICS; i++)
            mechanic[i].start();

        for(int i = 0;i<NUM_CLIENTS; i++)
            customer[i].start();

        manager.start();


        for(int i = 0;i<NUM_CLIENTS; i++) {
            try {
                customer[i].join();
            } catch (InterruptedException e) {
                Logger.logException(e);
            }
        }
        for(int i = 0; i<NUM_MECHANICS; i++)
        {
            try {
                mechanic[i].join();
            } catch (InterruptedException e) {
                Logger.logException(e);
            }
        }
        try {
            manager.join();
        } catch (InterruptedException e) {
            Logger.logException(e);
        }
    }
}
