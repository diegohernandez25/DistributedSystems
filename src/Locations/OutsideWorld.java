package Locations;

import Loggers.Logger;

public class OutsideWorld {

    /**
     *  Array with the id of the users which are waiting for the car to be repaired.
     *
     *      @serialField waitingForRepair.
     * */
    boolean[] waitingForRepair;

    /**
     *
     * Instantiation of the Outside World
     *
     *      @param numClients - Number of clients
     *
     * */
    public OutsideWorld(int numClients) throws Exception {
        if(numClients > 0)
        {
            waitingForRepair = new boolean[numClients];
            for(int i = 0; i < numClients; i++)
            { waitingForRepair[i] = false;}   //meaning that the customerId is not waiting for the repair

        }
        else throw new Exception("Number of clients is inferior");
    }

    /**
     *  Customer waits until the manager alerts him/her about the end of the service.
     *
     *      @param customerId - ID of the waiting customer.
     *
     * */
    public synchronized void waitForRepair(Integer customerId)
    {
        waitingForRepair[customerId] = true;
        while (waitingForRepair[customerId])
        {
            try {
                wait();
            } catch (InterruptedException e) {
                Logger.logException(e);
            }
        }
    }

    /**
     *  Managers alerts customer that car is fixed and it can be retrieved;
     *
     *      @param customerId - ID of the customer to alert .
     * */
    public synchronized void alertCustomer(Integer customerId) //TODO: Does it need to be synchronized?
    {
        waitingForRepair[customerId]=false;
        notifyAll();
    }
}
