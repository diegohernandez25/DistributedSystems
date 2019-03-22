package Locations;

public class SupplierSite<R> {

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
    {   if(idType<stockType && idType>=0) return number;
        return 0;
    }

}
