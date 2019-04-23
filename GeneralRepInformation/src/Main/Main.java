package Main;

import Communication.ServerCom;
import SharedRegions.GeneralRepInformation;
import SharedRegions.GeneralRepInformationProxy;
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
    {   ServerCom sc, sci;
        ServiceProvider sp;
        GeneralRepInformation generalRepInformation = new GeneralRepInformation(numCustomers, numMechanics, numPartTypes, carParts, fileName);
        GeneralRepInformationProxy generalRepInformationProxy = new GeneralRepInformationProxy(generalRepInformation);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!serviceEnd)
        {   sci = sc.accept();
            sp = new ServiceProvider(sci,generalRepInformationProxy);
            sp.start();
        }
    }
}
