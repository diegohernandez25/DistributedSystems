import Communication.ServerCom;
import SharedRegions.Park;
import SharedRegions.ParkProxy;
import SharedRegions.ServiceProvider;

public class Main {
    private static final int PORT_NUM = 1;  //TODO: Replace by port number.

    /**
     * Service Termination flag;
     * */
    public static boolean serviceEnd = false;

    /**
     * Number of customers
     * */
    public static final int numCustomers = 30;

    /**
     * Number of replacement cars.
     * */
    public static final int numReplacementCars = 3;

    /**
     * Main
     * @param args  - arguments.
     * */
    public static void main(String[] args)
    {   ServerCom sc, sci;
        ServiceProvider sp;

        int[] replacementCarKeys = new int[numReplacementCars];
        for(int i = numCustomers; i< numCustomers + numReplacementCars; i++)
        {   replacementCarKeys[i - numCustomers] = i;
        }
        Park park = new Park((numCustomers + numReplacementCars),replacementCarKeys);
        ParkProxy parkProxy = new ParkProxy(park);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!serviceEnd)
        {   sci = sc.accept();
            sp = new ServiceProvider(sci,parkProxy);
            sp.start();
        }
    }
}
