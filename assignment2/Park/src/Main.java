import Communication.ServerCom;
import SharedRegions.Park;
import SharedRegions.ParkProxy;
import SharedRegions.MessageHandler;
import Main.Parameters;
import Interfaces.*;
import GeneralRep.GeneralRepInformation;

import java.net.SocketTimeoutException;

public class Main {
    private static final int PORT_NUM = Parameters.parkPort;

    /**
     * Number of customers
     * */
    public static final int numCustomers = Parameters.numCustomers;

    /**
     * Number of replacement cars.
     * */
    public static final int numReplacementCars = Parameters.numReplacementCars;

    /**
     * Main
     * @param args  arguments.
     * */
    public static void main(String[] args)
    {
        System.out.println("Park starting...");

        ServerCom sc, sci;
        MessageHandler mh;

        GeneralRepInformation gri = new GeneralRepInformation(Parameters.griHost, Parameters.griPort);

        int[] replacementCarKeys = new int[numReplacementCars];
        for(int i = numCustomers; i< numCustomers + numReplacementCars; i++)
        {   replacementCarKeys[i - numCustomers] = i;
        }
        Park park = new Park((numCustomers + numReplacementCars),replacementCarKeys, (GriPark) gri);
        ParkProxy parkProxy = new ParkProxy(park);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!park.finish)
        {
            try {
                sci = sc.accept();
                mh = new MessageHandler(sci,parkProxy);
                mh.start();
            } catch (SocketTimeoutException e) {}
        }
        System.out.println("Park finished!");
    }
}
