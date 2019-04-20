package SharedRegions;

import Interfaces.*;
import Resources.MemException;
import Resources.MemFIFO;

public class OutsideWorld implements ManagerOW, CustomerOW {

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
            System.exit(1);
        }
    }

    /**
     *  Customer waits until the manager alerts him/her about the end of the service.
     *  @param customerId - ID of the waiting customer.
     * */
    public synchronized void waitForRepair(Integer customerId)
    {   waitingForRepair[customerId] = true;
        while (waitingForRepair[customerId])
        {   try {
                wait();
            } catch (InterruptedException e) {}
        }
    }

    /**
     *  Managers alerts customer that car is fixed and it can be retrieved;
     *  @param customerId - ID of the customer to alert .
     * */
    public synchronized void alertCustomer(Integer customerId)
    {   if(!waitingForRepair[customerId]) //if customer not yet on the outside world
        {   try {
                customersNotYetAtOutsideWorld.write(customerId);
            } catch (MemException e) {
                System.exit(1);
            }
        }
        else
        {   waitingForRepair[customerId]=false;
        }
        notifyAll();
    }
    /**
     *  Alert remaining customer whom hasn't been alerted (because they haven't arrived sooner at the outside world)
     * */
    public synchronized void alertRemainingCustomers()
    {   for(int i = 0;i<customersNotYetAtOutsideWorld.numElements();i++)
        {   try
            {   int tmpCustomerId =customersNotYetAtOutsideWorld.read();
                if(waitingForRepair[tmpCustomerId])
                {   waitingForRepair[tmpCustomerId] = false;
                    continue;
                }
                customersNotYetAtOutsideWorld.write(tmpCustomerId);
            } catch (MemException e) {
                System.exit(1);
            }
        }
        notifyAll();
    }

    /**
     *  Checks if there are user's expected to arrive at the outside world.
     *  @return true/false if customer is already in the Outside World or not
     * */
    public synchronized boolean customersNotYetAtOutsideWorldisEmpty()
    {   return customersNotYetAtOutsideWorld.isEmpty();
    }
}
