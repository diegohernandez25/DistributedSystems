package Locations;

import Objects.CarPart;

import java.util.ArrayList;

public class SupplierSite<R> {

    /**
     *  Repair Area
     *
     *      @serialField repairArea
     * */
    private RepairArea repairArea;

    /**
     *
     * Instantiation of the Supplier Site
     *      @param repairArea used repair area
     *
     * */
    public SupplierSite(RepairArea repairArea)
    {
        this.repairArea = repairArea;
    }

    /**
     * Restocks car part. Always gets 3 of the part.
     * @param carPart car part to be replenished.
     * */
    public synchronized void restockPart(CarPart carPart)
    {
        repairArea.addToStock(carPart);                         // add parts to stock of repair area
    }
}