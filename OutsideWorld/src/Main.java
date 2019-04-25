import Communication.ServerCom;
import SharedRegions.OutsideWorld;
import SharedRegions.OutsideWorldProxy;
import SharedRegions.ServiceProvider;
import Main.Parameters;

import java.net.SocketTimeoutException;

public class Main {
    private static final int PORT_NUM = Parameters.owPort;

    /**
     * Number of customers
     * */
    public static final int numCustomers = Parameters.numCustomers;

    /**
     * Main
     * @param args  arguments.
     * */
    public static void main(String[] args)
    {
        System.out.println("Outside World starting...");
        ServerCom sc, sci;
        ServiceProvider sp;
        OutsideWorld outsideWorld = new OutsideWorld(numCustomers);
        OutsideWorldProxy outsideWorldProxy = new OutsideWorldProxy(outsideWorld);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!outsideWorld.finish)
        {   try {
                sci = sc.accept();
                sp = new ServiceProvider(sci,outsideWorldProxy);
                sp.start();
            } catch (SocketTimeoutException e) {}
        }
        System.out.println("Outside World finished!");
    }
}
