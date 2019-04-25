import Communication.ServerCom;
import SharedRegions.GeneralRepInformation;
import SharedRegions.GeneralRepInformationProxy;
import SharedRegions.MessageHandler;
import Main.Parameters;

import java.net.SocketTimeoutException;

public class Main {
    private static final int PORT_NUM = Parameters.griPort;

    /**
     * Number of customers
     * */
    public static final int numCustomers = 30;

    /**
     * Number of mecahnics
     * */
    public static final int numMechanics = 2;

    /**
     * Number of car parts types
     * */
    public static final int numPartTypes = 3;

    /**
     * Stock of car parts for each type
     * */
    public static final int[] carParts = {0, 0, 0};

    /**
     * Name of the logger file
     */
    public static final String fileName = "log.txt";

    /**
     * Main
     * @param args  - arguments.
     * */
    public static void main(String[] args)
    {
        System.out.println("General Repository Information starting...");

        ServerCom sc, sci;
        MessageHandler mh;
        GeneralRepInformation generalRepInformation = new GeneralRepInformation(numCustomers, numMechanics, numPartTypes, carParts, fileName);
        GeneralRepInformationProxy generalRepInformationProxy = new GeneralRepInformationProxy(generalRepInformation);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!generalRepInformation.finish)
        {   try {
                sci = sc.accept();
                mh = new MessageHandler(sci,generalRepInformationProxy);
                mh.start();
            } catch (SocketTimeoutException e) {}
        }
        System.out.println("General Repository Information finished!");
    }
}
