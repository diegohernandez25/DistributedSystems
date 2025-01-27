package SharedRegions;

import Interfaces.*;

public class SupplierSite<R>  {

    /**
    * Initialize General Repository Information
    */
    private GriSS gri;

    /**
     *      Stock
     * */
    private int stockType;

    /**
     * Finish flag
     * */
    public volatile boolean finish;

    /**
     *  Instantiation of Supplier Site
     *  @param stockType number of total types of parts that are going to be available in stock
     *  @param gri general repository object.
     * */
    public SupplierSite(int stockType, GriSS gri)
    {
        this.gri = gri;
        this.stockType = stockType;
        this.finish = false;
    }

    /**
     *      Restocks car part. Always gets 3 of the part.
     *
     *      @param idType type of the part to replenish
     *      @param number requested number
     *
     *      @return number of parts that were restocked of the specific type of part
     * */
    public synchronized int restockPart(int idType, int number)
    {   if(idType<=stockType && idType>=0)
        {
            gri.setNumBoughtPart(idType, number);
            gri.setFlagMissingPart(idType, "F");
            return number;
        }
        return 0;
    }

    /**
     * Terminates Supplier Site Server
     * */
    public synchronized void finish()
    {   this.finish = true;
    }

}
