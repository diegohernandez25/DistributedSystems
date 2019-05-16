package Entities;

import Interfaces.*;
import Loggers.Logger;

public class Manager extends Thread
{
    private String  MANAGER = "Manager",
                    RUN     = "Run";

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
    private ManagerLounge lounge;

    /**
     *  SupplierSite
     *
     *      @serialField supplierSite
     * */
    private ManagerSS supplierSite;

    /**
     * Repair Area
     * @serialField repairArea;
     * */
    private ManagerRA repairArea;


    /**
     * Outside World
     * @serialField outsideWorld;
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
    public Manager(int managerId, ManagerLounge lounge, ManagerSS supplierSite, ManagerOW outsideWorld, ManagerRA repairArea)
    {
        this.managerId = managerId;
        this.lounge = lounge;
        this.supplierSite = supplierSite;
        this.outsideWorld = outsideWorld;
        this.repairArea = repairArea;
    }

    /**
     *
     *  Life cycle of Manager
     *
     *  */
    @Override
    public void run()
    {
        while (true)
        {
            /**
             *      Refill car parts stock
             * */
            readPaper();
            int indexPart = 0;
            int val = -1;
            if((indexPart =lounge.checksPartsRequest(indexPart))!=-1)       // First checks if there is a need to refill
                                                                            // the stock
            {
                int numberParts = supplierSite.restockPart(                 // Gets parts from the supplier site
                        indexPart,lounge.requestedNumberPart(indexPart));
                repairArea.refillCarPartStock(indexPart,numberParts);       // Store parts at the Repair Area storage
                //lounge.registerStockRefill(indexPart,numberParts);
                lounge.registerStockRefill(indexPart,numberParts);
                continue;                                                   //Refills all the asked stock

            }

            /**
             *      Alert remaining customers to get their fixed cars
             * */
            if(!outsideWorld.customersNotYetAtOutsideWorldisEmpty())
            {
                outsideWorld.alertRemainingCustomers();
            }
            /**
             *      Alert Current Customer.
             * */
            if(!lounge.isCustomerFixedCarKeysEmpty())                  // Checks if there are car fixed
            {
                int keyId = lounge.getFixedCarKey();                        // Gets key of fixed car
                int customerId = lounge.getCustomerFromKey(keyId);          // Gets the customer whom key belongs to
                                                                            // him/her
                lounge.readyToDeliverKey(customerId,keyId);                 // Makes available the key to take
                outsideWorld.alertCustomer(customerId);                     // Alert Customer
            }
            /**
             *      Attend Customer
             * */
            if((val = lounge.attendCustomer())!=-1)                    // Attends customer
            {
                if(val == -2)
                {
                    Logger.log(MANAGER,MANAGER,RUN,"Payment received",0,Logger.SUCCESS);
                    if(lounge.allDone())
                    {
                        repairArea.sendHome();
                        Logger.log(MANAGER,MANAGER,RUN,"Manager done.",0,Logger.SUCCESS);
                        break;
                    }
                }
                else {
                    repairArea.postJob(val);
                }
            }
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
