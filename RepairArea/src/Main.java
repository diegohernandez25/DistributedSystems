import Communication.ServerCom;
import SharedRegions.RepairArea;
import SharedRegions.RepairAreaProxy;
import SharedRegions.ServiceProvider;
import Main.Parameters;
import Interfaces.*;
import GeneralRep.GeneralRepInformation;

import java.net.SocketTimeoutException;

public class Main {
    /**
     * Port number
     * */
    private static final int PORT_NUM = Parameters.raPort;

    /**
     * Number of customers
     * */
    public static final int numCustomers = Parameters.numCustomers;

    /**
     * Number of car parts.
     * */
    public static final int numPartTypes = Parameters.numCarTypes;

    /**
     * Car parts
     */
    public static final int[] carParts = Parameters.carParts;

    /**
     * Maximum number of storage for each car part
     */
    public static final int[] maxCarParts = Parameters.maxCarParts;

    /**
     * Main
     * @param args  - arguments.
     * */
    public static void main(String[] args)
    {
        System.out.println("Starting...");

        ServerCom sc, sci;
        ServiceProvider sp;

        GeneralRepInformation gri = new GeneralRepInformation(Parameters.griHost, Parameters.griPort);

        RepairArea repairArea = new RepairArea(numCustomers, numPartTypes, carParts, maxCarParts, (GriRA) gri);
        RepairAreaProxy repairAreaProxy = new RepairAreaProxy(repairArea);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!repairArea.finish)
        {
            try {
                sci = sc.accept();
                sp = new ServiceProvider(sci,repairAreaProxy);
                sp.start();
            } catch (SocketTimeoutException e) {}
        }
        System.out.println("Goodbye!");
    }
}
