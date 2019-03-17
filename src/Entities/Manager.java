package Entities;

public class Manager {

    /**
     *  Manager identification
     *
     *      @serialField managerId
     *
     * */
    private int managerId;

    /**
     *  Lounge
     *
     *      @serialField lounge
     * */
    private Lounge lounge;

    /**
     *  SupplierSite
     *
     *      @serialField supplierSite
     * */
    private SupplierSite supplierSite;

    /**
     *  Instantiation of Manager Thread.
     *
     *      @param managerId identification of Manager.
     *      @param lounge used lounge.
     *      @param supplierSite used supplier site.
     * */
    public Manager(int managerId, Lounge lounge, SupplierSite supplierSite)
    {
        this.managerId = managerId;
        this.lounge = lounge;
        this.supplierSite = supplierSite;
    }

    /**
     *
     *  Life cycle of Manager
     *
     *  */
    @Override
    public void run()
    {
        while(lounge.isCarPartsQueueEmpty())                    // While there's no car parts to be replenished
        {
            wait();                                             // Do nothing
        }

        if(!lounge.isCarPartsQueueEmpty())                      // Checks if it was awoken to replenish car parts
        {
            CarPart carPart = lounge.getPartFromQueue();        // Get first car part waiting to be replenished
            supplierSite.restockPart();                         // Replenishes the needed car part
            notifyAll();                                        // Notify Mechanic for available car part
        }
    }

}
