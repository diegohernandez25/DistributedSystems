import Communication.ServerCom;
import SharedRegions.SupplierSite;
import SharedRegions.SupplierSiteProxy;
import SharedRegions.ServiceProvider;
import Main.Parameters;
import GeneralRep.GeneralRepInformation;

public class Main {
    private static final int PORT_NUM = Parameters.ssPort;

    /**
     * Service Termination flag;
     * */
    public static boolean serviceEnd = false;

    /**
     * Number of car parts.
     * */
    public static final int numPartTypes = Parameters.numCarTypes;

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

        SupplierSite supplierSite = new SupplierSite(numPartTypes, gri);
        SupplierSiteProxy supplierSiteProxy = new SupplierSiteProxy(supplierSite);
        sc = new ServerCom(PORT_NUM);
        sc.start();
        while(!serviceEnd)
        {   sci = sc.accept();
            sp = new ServiceProvider(sci,supplierSiteProxy);
            sp.start();
        }
    }
}
