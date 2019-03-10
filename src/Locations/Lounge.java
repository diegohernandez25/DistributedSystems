package Locations;

import Entities.Customer;
import Loggers.Logger;
import Objects.Key;
import Resources.MemException;
import Resources.MemFIFO;
import Resources.MemStack;
import org.omg.PortableInterceptor.LOCATION_FORWARD;

public class Lounge {

    /**
     *  Types of attending constants
     * */

    private final static int    REQ_FIX_CAR = 0,
                                PAYMENT = 1;

    /**
     * Customer constants states.
     * */
    private final static int    NORMAL_LIFE = 0,
                                WAITING_REQUEST_REPAIR = 1,
                                WAITING_REPLACEMENT_CAR = 2,
                                GET_REPLACEMENT_CAR = 0,
                                ATTENDED_W_SUBCAR = 2,
                                ATTENDED_WO_SUBCAR = 3,
                                WAITING_PAYMENT = 4,
                                ATTENDING = 6,
                                END_SERVICE = 5;

    /**
     *  Current states of customers.
     *
     *      @serialField stateCustomers
     * */
    private int[] stateCustomers;

    /**
     *  Manager constants states.
     * */
    private final static int    READING_PAPER = 0,
                                CHECK_CUSTOMER = 1,
                                ATTENDING_CUSTOMER = 2,
                                CUST_KEY_GIVEN = 8,
                                CHECK_STOCK = 3,
                                REFILL_STOCK = 4,
                                CHECK_REPAIRED_CARS = 5,
                                ALERT_REPAIRED_CAR = 6,
                                RECV_PAYMENT = 7;

    /**
     *  Current state of Manager.
     *
     *      @serialField stateManager
     * */
    private int stateManager;
    /**
     *  All the Keys of Replacement Cars
     *
     *      @serialField replacementCarKeys
     *
     * */
    private MemStack<Key> replacementCarKeys;

    /**
     *  All the Keys of Customer Cars
     *
     *      @serialField customerCarKeys
     *
     * */
    private MemFIFO<Key> customerCarKeys;

    /**
     *  All the Keys of Customer Fixed Cars
     *
     *      @serialField customerFixedCarKeys
     *
     * */
    private MemFIFO<Key> customerFixedCarKeys;
    /**
     *  Queue of ids of waiting customers
     *
     *      @serialField replacementCarKeys
     *
     * */

    private MemFIFO<Integer> customerQueue;


    private MemFIFO<Integer> waitingReplacementKey;


    /**
     *
     * Instantiation of the Lounge
     *      @param replacementKeys - Array of Key objects. NOTE: Must have the size equal
     *                to the total number of replacement cars.
     *      @param clients clients
     *      @param customerKeys customer's keys
     * */


    public Lounge(Key[] replacementKeys, Integer[] clients, Key[] customerKeys)
    {
        try {
            this.replacementCarKeys = new MemStack<>(replacementKeys);
            this.customerQueue = new MemFIFO<>(clients);
            this.customerCarKeys = new MemFIFO<>(customerKeys);
            this.customerFixedCarKeys = new MemFIFO<>(customerKeys);

            this.stateCustomers = new int[clients.length];
            for( int i = 0; i< clients.length; i++)
            {
                stateCustomers[i] = NORMAL_LIFE;
            }

            this.stateManager = READING_PAPER;

        } catch (MemException e) {
            Logger.logException(e);
        }
    }


    /**
     *
     * Definition of the carried out operations over the monitors:
     *  - Go to the lounge
     *  - Attend the customer
     *  - Receive Payment
     *  - Check missing stock.
     *  - Check and alert about fixed cars.
     * */

    /**
     * @note Customer invokes this method
     * */
    public synchronized boolean enterCustomerQueue(Integer customerId, int typeOperation)
    {
        //TODO: Reminder - Customer must wait
        try {
            stateCustomers[customerId] = WAITING_REQUEST_REPAIR;
            customerQueue.write(customerId);
        } catch (MemException e) {
            Logger.logException(e);
            return false;
        }

        while (stateCustomers[customerId] != ATTENDING)
        {
            try
            {
                wait();
            }
            catch(InterruptedException e){
                return false; //FIXME ??
            }
        }
        return true;
    }

    /**
     * @note Manager invokes this method.
     * @return the ID of the next customer to attend
     * */
    public synchronized boolean attendCustomer()
    {
        if(customerQueue.isEmpty()) { return (false); }
        try
        {
            int customerId = customerQueue.read();
            stateCustomers[customerId] = ATTENDING;
            notifyAll();
        }
        catch (MemException e) {
            Logger.logException(e);
        }
        return (true);
    }

    /**
     *  Make available the key of the car to be fixed.
     *
     *      @note Client invokes this method
     *
     *      @param key - Key of the car to fix.
     *
     *      @return boolean about end operation status.
     * */
    public synchronized boolean giveKey(Key key)
    {
        try {
            customerCarKeys.write(key);
        } catch (MemException e) {
            Logger.logException(e);
            return false;
        }
        return true;
    }

    /**
     *  Get replacement car key.
     *
     *      @note Client invokes this method.
     *
     *      @param customerId - ID of the client who needs the replacement car.
     *
     *      @return the key of the replacement car.
     * */
    public synchronized Key getReplacementCarKey(Integer customerId)
    {
        if(!replacementCarKeys.isEmpty())
        {   try { return replacementCarKeys.read(); }
            catch (MemException e) {
                Logger.logException(e);
                return null;
            }
        }
        try
        {
            stateCustomers[customerId] = WAITING_REPLACEMENT_CAR;
            waitingReplacementKey.write(customerId);
        }
        catch (MemException e)
        {
            Logger.logException(e);
            return null;
        }
        while (stateCustomers[customerId] != GET_REPLACEMENT_CAR)
        {
            try {
                wait();
            }
            catch (InterruptedException e) { }
        }
        stateCustomers[customerId] = ATTENDED_W_SUBCAR;
        Key tmpKey = null;
        try {
            tmpKey = replacementCarKeys.read();
        } catch (MemException e) {
            Logger.logException(e);
            return  null;
        }
        //TODO LogStatus
        return tmpKey;
    }

    /**
     *  Return Replacement car key.
     *
     *      @note Customer with the need of a replacement car invokes this method.
     *
     *      @param key - Key of the replacement car key.
     *
     *      @return status of the operation.
     * */
    public synchronized boolean returnReplacementCarKey(Key key)
    {
        try {
            replacementCarKeys.write(key);
            int customerId = waitingReplacementKey.read();
            stateCustomers[customerId] = GET_REPLACEMENT_CAR;
            notifyAll();
        } catch (MemException e) {
            Logger.logException(e);
            return false;
        }
        return true;
    }

    /**
     *  Exit Lounge
     *
     *      @note Customer without the need of a replacement car invokes this method.
     *
     *      @param customerId - Id of the customer who will exit the lounge
     *
     * */

    public void exitLounge(Integer customerId)
    {
        stateCustomers[customerId] = ATTENDED_WO_SUBCAR;
    }

    /**
     * @note Manager invokes this method
     * */
    public boolean isCustomerQueueEmpty()
    {
        return customerQueue.isEmpty();
    }


    /**
     * @note Customer invokes this method
     * */
    public boolean isReplacementCarKeysEmpty()
    {
        return replacementCarKeys.isEmpty();
    }

    /**
     * @note Mechanic invokes this method
     * */
    public boolean isCustomerCarKeysEmpty()
    {
        return customerCarKeys.isEmpty();
    }

}
