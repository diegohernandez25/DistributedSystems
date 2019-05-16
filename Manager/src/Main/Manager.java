package Main;

import Interfaces.*;

public class Manager extends Thread
{   /**
 *  Life cycle of Manager
 *  */
    /**
     *  Manager identification
     *
     * */
    private int managerId;

    /**
     *  Lounge
     * */
    private ManagerLounge lounge;

    /**
     *  SupplierSite
     * */
    private ManagerSS supplierSite;

    /**
     * Repair Area
     * */
    private ManagerRA repairArea;

    /**
     * Outside World
     * */
    private ManagerOW outsideWorld;

    /**
     *  Instantiation of Manager Thread.
     *
     *      @param managerId identification of Manager.
     *      @param lounge used Lounge.
     *      @param supplierSite used Supplier Site.
     *      @param outsideWorld used Outside World
     *      @param repairArea used Repair Area
     * */
    public Manager(int managerId, ManagerLounge lounge, ManagerSS supplierSite,
                   ManagerOW outsideWorld, ManagerRA repairArea)
    {
        this.managerId = managerId;
        this.lounge = lounge;
        this.supplierSite = supplierSite;
        this.outsideWorld = outsideWorld;
        this.repairArea = repairArea;
    }

    /**
     *  Life cycle of Manager.
     * */
    @Override
    public void run()
    {   while(true)
    {   readPaper();
        int indexPart = 0;
        int val = -1;
        /**
         *  Refill car parts stock;
         * */
        if((indexPart = lounge.checksPartsRequest()) != -1)
        {   System.out.println("Parts to refill");
            int numberParts = supplierSite.restockPart(indexPart,
                    lounge.requestedNumberPart(indexPart));
            System.out.println("Parts bought");
            repairArea.refillCarPartStock(indexPart,numberParts);
            System.out.println("Parts restocked");
            lounge.registerStockRefill(indexPart,numberParts);
            System.out.println("Parts restocked registered");
            continue;
        }
        /**
         *  Alert remaining customers to get their fixed cars
         * */
        if(!outsideWorld.customersNotYetAtOutsideWorldisEmpty())
        {   System.out.println("Alerting remaining customers");
            outsideWorld.alertRemainingCustomers();
        }
        /**
         *      Alert Current Customer.
         * */
        if(!lounge.isCustomerFixedCarKeysEmpty())
        {   System.out.println("Parts restocked registered");
            int keyId = lounge.getFixedCarKey();
            System.out.println("Getting key");
            int customerId = lounge.getCustomerFromKey(keyId);
            System.out.println("Getting customer");
            lounge.readyToDeliverKey(customerId,keyId);
            System.out.println("Ready to deliver key to customer");
            outsideWorld.alertCustomer(customerId);
            System.out.println("Alerting customer");
        }
        /**
         *      Attend Customer
         * */
        if((val = lounge.attendCustomer())!=-1)                    // Attends customer
        {   System.out.println("Customer attended");
            if(val == -2)
            {   if(lounge.allDone())
            {   System.out.println("All done");
                repairArea.sendHome();
                break;
            }
            }
            else
            {   System.out.println("Posting job to mechanics");
                repairArea.postJob(val);
                System.out.println("Posting job done.");
            }
        }
    }
        System.out.println("FINISHED!");
    }

    private void readPaper()
    {
        try
        { sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){}
    }
}
