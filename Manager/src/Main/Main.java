package Main;

import Locals.Lounge;
import Locals.OutsideWorld;
import Locals.RepairArea;
import Locals.SupplierSite;

public class Main {

    public static void main(String[] args)
    {   Lounge lounge = new Lounge("localhost",22460);
        OutsideWorld outsideWorld = new OutsideWorld("localhost", 22462);
        RepairArea repairArea = new RepairArea("localhost",22463);
        SupplierSite supplierSite = new SupplierSite("localhost",22464);
        Manager manager = new Manager(0,lounge,supplierSite,
                outsideWorld,repairArea);
        manager.start();
        try { manager.join();
        } catch (InterruptedException e) { e.printStackTrace(); }
    }
}
