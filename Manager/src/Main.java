import Locals.Lounge;
import Locals.OutsideWorld;
import Locals.RepairArea;
import Locals.SupplierSite;
import Main.Parameters;
import Main.Manager;

public class Main {

    public static void main(String[] args)
    {   Lounge lounge = new Lounge(Parameters.loungeHost, Parameters.loungePort);
        OutsideWorld outsideWorld = new OutsideWorld(Parameters.owHost, Parameters.owPort);
        RepairArea repairArea = new RepairArea(Parameters.raHost, Parameters.raPort);
        SupplierSite supplierSite = new SupplierSite(Parameters.ssHost, Parameters.ssPort);
        Manager manager = new Manager(0,lounge,supplierSite,
                outsideWorld,repairArea);
        manager.start();
        try { manager.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
