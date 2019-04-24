import Communication.ServerCom;
import Locals.OutsideWorld;
import Locals.Park;
import Locals.RepairArea;
import Locals.SupplierSite;
import SharedRegions.Lounge;
import SharedRegions.LoungeProxy;
import SharedRegions.ServiceProvider;
import Interfaces.*;
import Main.Parameters;
import GeneralRep.GeneralRepInformation;

import java.net.SocketTimeoutException;

public class Main {
    /**
     * Port number.
     * */
    private static final int PORT_NUM = Parameters.loungePort;

    /**
     * Number of customers
     * */
    public static final int numCustomers = Parameters.numCustomers;

    /**
     * Number of mechanics
     * */
    public static final int numMechanics = Parameters.numMechanics;

    /**
     * Number of replacement cars
     * */
    public static final int numReplacementCars = Parameters.numReplacementCars;

    /**
     * Number of car part types.
     * */
    public static final int numCarTypes = Parameters.numCarTypes;

    /**
     * Main function.
     * @param args arguments
     * */
    public static void main(String[] args)
    {
        System.out.println("Starting...");

        ServerCom sc, sci;
        ServiceProvider sp;

        GeneralRepInformation gri = new GeneralRepInformation(Parameters.griHost, Parameters.griPort);
        OutsideWorld outsideWorld = new OutsideWorld(Parameters.owHost, Parameters.owPort);
        Park park = new Park(Parameters.parkHost, Parameters.parkPort);
        RepairArea repairArea = new RepairArea(Parameters.raHost, Parameters.raPort);
        SupplierSite supplierSite = new SupplierSite(Parameters.ssHost, Parameters.ssPort);


        int[] replacementCarKeys = new int[numReplacementCars];
        for(int i = numCustomers; i< numCustomers + numReplacementCars; i++)
        {   replacementCarKeys[i - numCustomers] = i;
        }

        Lounge lounge = new Lounge(numCustomers, numMechanics, replacementCarKeys, numCarTypes, (GriLounge) gri,
                outsideWorld, park, repairArea, supplierSite);
        LoungeProxy loungeProxy = new LoungeProxy(lounge);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!lounge.finish)
        {   try {
                sci = sc.accept();
                sp = new ServiceProvider(sci,loungeProxy);
                sp.start();
            } catch (SocketTimeoutException e) {
            }
        }
        System.out.println("Goodbye!");
    }
}
