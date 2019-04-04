package Locations;

import Interfaces.*;
import Resources.MemException;
import Resources.MemFIFO;

public class OutsideWorld implements ManagerOW, CustomerOW {


    String  LOCAL       = "OutsideWorld",
            MANAGER     = "Manager",
            CUSTOMER    = "Customer";
    /**
     *  Array with the id of the users which are waiting for the car to be repaired.
     *  @serialField waitingForRepair.
     * */
    private volatile boolean[] waitingForRepair;

    /**
     * FIFO of all customers who haven't been arrived yet to the outside world (mainly because it waits for
     * a replacement car key).
     * @serialField customerNotYetAtOutsideWorld.
     * */
    private volatile MemFIFO<Integer> customersNotYetAtOutsideWorld;

    /**
     * Instantiation of the Outside World
     * @param numClients - Number of clients
     * */
    public OutsideWorld(int numClients){

        waitingForRepair = new boolean[numClients];
        for(int i = 0; i < numClients; i++) { waitingForRepair[i] = false;}
        try {
            customersNotYetAtOutsideWorld = new MemFIFO<>(new Integer[numClients]);
        } catch (MemException e) {
            ////Logger.logException(e);
            System.exit(1);
        }
    }

    /**
     *  Customer waits until the manager alerts him/her about the end of the service.
     *  @param customerId - ID of the waiting customer.
     * */
    public synchronized void waitForRepair(Integer customerId)
    {
        String FUNCTION = "waitForRepair";
        ////Logger.log(CUSTOMER,LOCAL,FUNCTION,"Waiting for the car to get fixed",customerId,10);
        waitingForRepair[customerId] = true;
        while (waitingForRepair[customerId])
        {
            ////Logger.log(CUSTOMER,LOCAL,FUNCTION,"Waiting for the car to get fixed",customerId,////Logger.WARNING);
            try {
                wait();
            } catch (InterruptedException e) {
                ////Logger.logException(e);
            }
        }
        ////Logger.log(CUSTOMER,LOCAL,FUNCTION,"Customer waken and ready to get car",customerId,////Logger.SUCCESS);
    }

    /**
     *  Managers alerts customer that car is fixed and it can be retrieved;
     *  @param customerId - ID of the customer to alert .
     * */
    public synchronized void alertCustomer(Integer customerId)
    {
        String FUNCTION = "alertCustomer";
        //////Logger.log(CUSTOMER,LOCAL,FUNCTION,"Notifying customer "+customerId+" about fixed car",0, ////Logger.WARNING);
        if(!waitingForRepair[customerId]) //if customer not yet on the outside world
        {
            try {
                customersNotYetAtOutsideWorld.write(customerId);
                ////Logger.log(CUSTOMER,LOCAL,FUNCTION,"Customer "+customerId+" not yet at the outside world",0, ////Logger.WARNING);
                //return;
            } catch (MemException e) {
                ////Logger.logException(e);
                System.exit(1);
            }
        }
        else
        {
            ////Logger.log(CUSTOMER,LOCAL,FUNCTION,"Notifying customer "+customerId+" about fixed car",0, ////Logger.WARNING);
            waitingForRepair[customerId]=false;
        }
        //////Logger.log(CUSTOMER,LOCAL,FUNCTION,"Notifying customer.",0, ////Logger.WARNING);
        notifyAll();
    }
    /**
     *  Alert remaining customer whom hasn't been alerted (because they haven't arrived sooner at the outside world)
     * */
    public synchronized void alertRemainingCustomers()
    {
        String FUNCTION = "alertRemainingCustomers";
        for(int i = 0;i<customersNotYetAtOutsideWorld.numElements();i++)
        {
            try {
                int tmpCustomerId =customersNotYetAtOutsideWorld.read();
                if(waitingForRepair[tmpCustomerId])
                {
                    ////Logger.log(MANAGER,LOCAL,FUNCTION,"Notifying customer "+tmpCustomerId+" about fixed car",0, ////Logger.WARNING);
                    waitingForRepair[tmpCustomerId] = false;
                    continue;
                }
                ////Logger.log(MANAGER,LOCAL,FUNCTION,"Customer "+tmpCustomerId+" not yet at the outside world",0, ////Logger.WARNING);
                customersNotYetAtOutsideWorld.write(tmpCustomerId);
            } catch (MemException e) {
                ////Logger.logException(e);
            }
        }
        //////Logger.log(CUSTOMER,LOCAL,FUNCTION,"Notifying customer.",0, ////Logger.WARNING);
        notifyAll();
    }

    /**
     *  Checks if there are user's expected to arrive at the outside world.
     *  @return true/false if customer is already in the Outside World or not
     * */
    public synchronized boolean customersNotYetAtOutsideWorldisEmpty()
    {
        return customersNotYetAtOutsideWorld.isEmpty();
    }
}
