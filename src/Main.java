import Locations.*;
import Loggers.Logger;
import Objects.Key;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        OutsideWorld outsideWorld;
        Lounge lounge ;
        Park park;
        RepairArea repairArea;
        SupplierSite supplierSite;

        Scanner sc = new Scanner(System.in);

        System.out.print("Number of clients: ");
        int numClients = sc.nextInt();
        assert numClients > 0;

        System.out.print("Number of mechanics: ");
        int numMechanics = sc.nextInt();
        assert numMechanics > 0;

        System.out.print("Number of replacement cars: ");
        int numReplacementCars = sc.nextInt();
        assert numReplacementCars > 0;

        System.out.print("Stock length: ");
        int stockLength = sc.nextInt();
        assert stockLength > 0;
        Key[] replacementKeys = new Key[stockLength];
        for(int i = 0; i < stockLength; i++)
        {
            replacementKeys[i] = new Key();
        }

        try {
            outsideWorld = new OutsideWorld(numClients);
        } catch (Exception e) {
            Logger.logException(e);
        }

        lounge = new Lounge()

    }
}
