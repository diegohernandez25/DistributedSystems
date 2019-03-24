package Locations;

import Loggers.Logger;

public class SupplierSite<R> {
    public static String    LOCAL   = "Supplier Site",
                            MANAGER = "Manager";
    /**
     *  Repair Area
     *
     *      @serialField repairArea
     * */
    private RepairArea repairArea;

    /**
     *      Stock
     *
     *      @serialField stockType
     * */
    private int stockType;

    /**
     *
     * Instantiation of the Supplier Site
     *      @param repairArea used repair area
     *
     * */
    public SupplierSite(RepairArea repairArea, int[] typeParts)
    {
        this.repairArea = repairArea;
        this.stockType = typeParts.length;

    }

    /**
     *      Restocks car part. Always gets 3 of the part.
     *
     *      @param idType - type of the part to replenish
     *      @param number - requested number
     * */
    public synchronized int restockPart(int idType, int number)
    {   String FUNCTION = "restockPart";
        Logger.log(MANAGER,LOCAL,"Restocking part. PartId: "+idType+", Number: "+number,0,Logger.SUCCESS);
        if(idType<=stockType && idType>=0) return number;
        Logger.log(MANAGER,LOCAL,"Id type not in range of types",0,Logger.ERROR);
        return 0;
    }

}
