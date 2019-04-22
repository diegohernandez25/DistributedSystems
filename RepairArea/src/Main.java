import Communication.ServerCom;
import SharedRegions.RepairArea;
import SharedRegions.RepairAreaProxy;
import SharedRegions.ServiceProvider;

public class Main {
    private static final int PORT_NUM = 22463;  //TODO: Replace by port number.

    /**
     * Service Termination flag;
     * */
    public static boolean serviceEnd = false;

    /**
     * Number of customers
     * */
    public static final int numCustomers = 30;

    /**
     * Number of car parts.
     * */
    public static final int numPartTypes = 3;

    /**
     * Car parts
     */
    public static final int[] carParts = {0, 0, 0};

    /**
     * Maximum number of storage for each car part
     */
    public static final int[] maxCarParts = {1, 1, 1};

    /**
     * Main
     * @param args  - arguments.
     * */
    public static void main(String[] args)
    {   System.out.println("Starting...");
        ServerCom sc, sci;
        ServiceProvider sp;

        RepairArea repairArea = new RepairArea(numCustomers, numPartTypes, carParts, maxCarParts);
        RepairAreaProxy repairAreaProxy = new RepairAreaProxy(repairArea);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!serviceEnd)
        {   sci = sc.accept();
            sp = new ServiceProvider(sci,repairAreaProxy);
            sp.start();
        }
    }
}
