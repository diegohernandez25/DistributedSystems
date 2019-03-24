package Entities;

import Locations.*;
import Loggers.Logger;

public class Manager extends Thread
{
    public String   MANAGER = "Manager",
                    RUN     = "Run";

    /**
     *  Initialize GeneralRepInformation
     * */
    private GeneralRepInformation gri;

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
     * Repair Area
     * @serialField repairArea;
     * */
    private RepairArea repairArea;


    /**
     * Outside World
     * @serialField outsideWorld;
     * */
    private OutsideWorld outsideWorld;

    /**
     *  Instantiation of Manager Thread.
     *
     *      @param managerId identification of Manager.
     *      @param lounge used lounge.
     *      @param supplierSite used supplier site.
     * */
    public Manager(int managerId, Lounge lounge, SupplierSite supplierSite, GeneralRepInformation gri)
    {
        this.managerId = managerId;
        this.lounge = lounge;
        this.supplierSite = supplierSite;
        this.gri = gri;
    }

    /**
     *
     *  Life cycle of Manager
     *
     *  */
    @Override
    public void run()
    {   while (true)
        {
            /**
             *      Refill car parts stock
             * */
            int indexPart = 0;
            int val = -1;
            if((indexPart =lounge.checksPartsRequest(indexPart))!=-1)       // First checks if there is a need to refill
                                                                            // the stock
            {
                int numberParts = supplierSite.restockPart(                 // Gets parts from the supplier site
                        indexPart,lounge.requestedNumberPart(indexPart));
                repairArea.refillCarPartStock(indexPart,numberParts);       // Store parts at the Repair Area storage
                lounge.alertStockRefill(indexPart);                         // Alert mechanics that stock has been
                                                                            // renewed
            }

            /**
             *      Alert customers to get their fixed cars
             * */
            else if(!lounge.isCustomerFixedCarKeysEmpty())                  // Checks if there are car fixed
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
            else if((val = lounge.attendCustomer())!=-1)                    // Attends customer
            {
                if(val == -2)
                {
                    Logger.log(MANAGER,MANAGER,RUN,"Payment received",0,Logger.SUCCESS);
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
