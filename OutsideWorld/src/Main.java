import Communication.ServerCom;
import SharedRegions.OutsideWorld;
import SharedRegions.OutsideWorldProxy;
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
     * Main
     * @param args  - arguments.
     * */
    public static void main(String[] args)
    {   ServerCom sc, sci;
        ServiceProvider sp;
        OutsideWorld outsideWorld = new OutsideWorld(numCustomers);
        OutsideWorldProxy outsideWorldProxy = new OutsideWorldProxy(outsideWorld);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!serviceEnd)
        {   sci = sc.accept();
            sp = new ServiceProvider(sci,outsideWorldProxy);
            sp.start();
        }
    }
}
