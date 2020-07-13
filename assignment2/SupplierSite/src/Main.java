import Communication.ServerCom;
import SharedRegions.SupplierSite;
import SharedRegions.SupplierSiteProxy;
import SharedRegions.MessageHandler;
import Main.Parameters;
import Interfaces.*;
import GeneralRep.GeneralRepInformation;

import java.net.SocketTimeoutException;

public class Main {
    private static final int PORT_NUM = Parameters.ssPort;

    /**
     * Number of car parts.
     * */
    public static final int numPartTypes = Parameters.numCarTypes;

    /**
     * Main
     * @param args  arguments.
     * */
    public static void main(String[] args)
    {   System.out.println("Supplier Site starting...");
        ServerCom sc, sci;
        MessageHandler mh;

        GeneralRepInformation gri = new GeneralRepInformation(Parameters.griHost, Parameters.griPort);

        SupplierSite supplierSite = new SupplierSite(numPartTypes, (GriSS) gri);
        SupplierSiteProxy supplierSiteProxy = new SupplierSiteProxy(supplierSite);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!supplierSite.finish)
        {   try {
                sci = sc.accept();
                mh = new MessageHandler(sci,supplierSiteProxy);
                mh.start();
            } catch (SocketTimeoutException e) { }

        }
        System.out.println("Supplier Site finished!");
    }
}
