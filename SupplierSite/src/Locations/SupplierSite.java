package Locations;

import Interfaces.*;

public class SupplierSite<R> implements ManagerSS{
    public static String    LOCAL   = "Supplier Site",
            MANAGER = "Manager";

    /**
     *  Initialize GeneralRepInformation
     * */
    private GriSS gri;

    /**
     *      Stock
     *      @serialField stockType
     * */
    private int stockType;

    /**
     *  Instantiation of Supplier Site
     *  @param stockType number of total types of parts that are going to be available in stock
     *  @param gri - logger
     * */
    public SupplierSite(int stockType, GriSS gri)
    {
        this.gri = gri;
        this.stockType = stockType;

    }

    /**
     *      Restocks car part. Always gets 3 of the part.
     *
     *      @param idType - type of the part to replenish
     *      @param number - requested number
     *
     *      @return number of parts that were restocked of the specific type of part
     * */
    public synchronized int restockPart(int idType, int number)
    {   String FUNCTION = "restockPart";
        if(idType<=stockType && idType>=0)
        {
            gri.setNumBoughtPart(idType, number);   // Log added number of specific car part restocked
            gri.setFlagMissingPart(idType, "F");    // Log part is no longer needed for restock
            return number;
        }
\        return 0;
    }

}
