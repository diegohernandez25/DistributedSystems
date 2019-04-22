import Communication.ServerCom;
import SharedRegions.Lounge;
import SharedRegions.LoungeProxy;
import SharedRegions.ServiceProvider;

import java.net.SocketTimeoutException;

public class Main {
    private static final int PORT_NUM = 22460 ;

    /**
     * Service Termination flag;
     * */
    public static boolean serviceEnd = false;

    /**
     * Number of customers
     * */
    public static final int numCustomers = 30;

    /**
     * Number of mechanics
     * */
    public static final int numMechanics = 2;

    /**
     * Number of replacement cars
     * */
    public static final int numReplacementCars = 3;

    /**
     * Number of car part types.
     * */
    public static final int numCarTypes = 3;

    /**
     * Main function.
     * */
    public static void main(String[] args)
    {   ServerCom sc, sci;
        ServiceProvider sp;

        int[] replacementCarKeys = new int[numReplacementCars];
        for(int i = numCustomers; i< numCustomers + numReplacementCars; i++)
        {   replacementCarKeys[i - numCustomers] = i;
        }
        Lounge lounge = new Lounge(numCustomers, numMechanics, replacementCarKeys, numCarTypes);
        LoungeProxy loungeProxy = new LoungeProxy(lounge);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!serviceEnd)
        {   sci = sc.accept();
            sp = new ServiceProvider(sci,loungeProxy);
            sp.start();
        }
    }
}
