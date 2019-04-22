import Communication.ServerCom;
import SharedRegions.SupplierSite;
import SharedRegions.SupplierSiteProxy;
import SharedRegions.ServiceProvider;

public class Main {
    private static final int PORT_NUM = 22464;  //TODO: Replace by port number.

    /**
     * Service Termination flag;
     * */
    public static boolean serviceEnd = false;

    /**
     * Number of car parts.
     * */
    public static final int numPartTypes = 3;

    /**
     * Main
     * @param args  - arguments.
     * */
    public static void main(String[] args)
    {   ServerCom sc, sci;
        ServiceProvider sp;

        SupplierSite supplierSite = new SupplierSite(numPartTypes);
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
