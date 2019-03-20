package Entities;

import Locations.Lounge;
import Locations.SupplierSite;

public class Manager extends Thread
{
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
//    @Override
//    public void run()
//    {
//        while(lounge.isCarPartsQueueEmpty())                    // While there's no car parts to be replenished
//        {
//            try {
//                wait();                                             // Do nothing
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        if(!lounge.isCarPartsQueueEmpty())                      // Checks if it was awoken to replenish car parts
//        {
//            CarPart carPart = lounge.getPartFromQueue();        // Get first car part waiting to be replenished
//            //supplierSite.restockPart();                         // Replenishes the needed car part FIXME
//            notifyAll();                                        // Notify Mechanic for available car part
//        }
//    }
    @Override
    public void run()
    {
        while(lounge.isCarPartsQueueEmpty())                    // While there's no car parts to be replenished
        {
            try {
                wait();                                             // Do nothing
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if(!lounge.isCarPartsQueueEmpty())                      // Checks if it was awoken to replenish car parts
        {
            int carPart = lounge.getPartFromQueue();        // Get first car part waiting to be replenished
            //supplierSite.restockPart();                         // Replenishes the needed car part FIXME
            notifyAll();                                        // Notify Mechanic for available car part
        }
    }

    private void readPaper()
    {
        try
        { sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){}
    }
}
