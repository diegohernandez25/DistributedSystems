package SharedRegions;

import Interfaces.*;
import GeneralRep.GeneralRepInformation;

public class SupplierSite<R> implements ManagerSS{

    /**
    * Initialize General Repository Information
    */
    private GeneralRepInformation gri;

    /**
     *      Stock
     *      @serialField stockType
     * */
    private int stockType;

    /**
     *  Instantiation of Supplier Site
     *  @param stockType number of total types of parts that are going to be available in stock
     * */
    public SupplierSite(int stockType, GeneralRepInformation gri)
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
            gri.setNumBoughtPart(idType, number);
            gri.setFlagMissingPart(idType, "F");
            return number;
        }
        return 0;
    }

}
