package Locations;

import Loggers.Logger;
import Objects.Key;
import Resources.MemException;
import Resources.MemFIFO;
import Resources.MemStack;
import sun.rmi.runtime.Log;

import java.util.HashMap;

public class Lounge {

    /**
     *  Type of Manager Tasks
     * */
    private static final Integer    ATTEND_CUSTOMER = 0,
                                    CALL_CUSTOMER   = 1,
                                    FILL_STOCK      = 2,
                                    READ_PAPER      = 3;
    /**
     *  Created Constants for the Logger
     *  */
    private String  MANAGER     = "Manager",
                    CUSTOMER    = "Customer",
                    LOCAL       = "Lounge";

    /**
     * Customer constants states.
     * */
    private final static int    NORMAL_LIFE = 0,
                                WAITING_REQUEST_REPAIR = 1,
                                WAITING_REPLACEMENT_CAR = 2,
                                GET_REPLACEMENT_CAR = 3,
                                ATTENDED_W_SUBCAR = 4,
                                ATTENDED_WO_SUBCAR = 5,
                                ATTENDED = 6,
                                ATTENDING = 7,
                                CAR_FIXED = 8,
                                GETTING_REPLACEMENT_CAR = 9;

    /**
     *  Current states of customers.
     *
     *      @serialField stateCustomers
     * */
    private int[] stateCustomers;

    /**
     *  All the Keys of Replacement Cars
     *
     *      @serialField replacementCarKeys
     *
     * */
    private MemStack<Key> replacementCarKeys;

    /**
     *  Record all replacement Keys used by a Customer ID
     *
     *      @serialField  usedReplacementCarKeys
     * */
    private Integer[] usedReplacementCarKeys;

    /**
     *  All the Keys of Customer Cars
     *
     *      @serialField customerCarKeys
     *
     * */

    private Key[] customerCarKeys;

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
     *  Stack of clients which are waiting for payment
     *
     *      @serialField paymentQueue
     * */
    private MemFIFO<Integer> paymentQueue;

    /**
     *  Storage of car keys.
     *
     *      @serialField  repairedCarKeys
     * */
    private HashMap<String,Key> repairedCarKeys;

    /**
     *
     * Instantiation of the Lounge
     *      @param replacementKeys - Array of Key objects. NOTE: Must have the size equal
     *                               to the total number of replacement cars.
     *      @param clients - clients
     *      @param customerKeys - customer's keys
     * */
    public Lounge(Key[] replacementKeys, Integer[] clients, Key[] customerKeys)
    {   try {
            this.customerQueue = new MemFIFO<>(clients);
            //this.customerCarKeys = new MemFIFO<>(customerKeys);
            this.customerCarKeys = customerKeys;
            this.customerFixedCarKeys = new MemFIFO<>(customerKeys);
            this.usedReplacementCarKeys = new Integer[replacementKeys.length];
            this.stateCustomers = new int[clients.length];

            //Initiate the state of all customers.
            for( int i = 0; i< clients.length; i++) { stateCustomers[i] = NORMAL_LIFE; }

            //Initiate all usage of replacement car keys
            for( int i = 0; i<replacementKeys.length; i++)
            {   replacementKeys[i].setId(i);
                this.usedReplacementCarKeys[i] = -1;                                //Meaning not used by any Customer.
            }
            this.replacementCarKeys = new MemStack<>(replacementKeys);
            //TODO Log everything
        } catch (MemException e) {
            Logger.logException(e);
        }
    }


    /**
     *  Customer enters queue to be attended by the Manager.
     *
     *      @param customerId - Id of the customer to be attended
     *      @param payment - type of attendance. (true/false) Pay for repair/Request repair.
     *
     *      @note Customer invokes this method
     *
     *      @return operation success so the thread can move on to the next operation.
     * */
    public synchronized boolean enterCustomerQueue(Integer customerId, boolean payment)
    {   if(payment)
        {
            try { paymentQueue.write(customerId); }
            catch (MemException e) { Logger.logException(e); }
        }

        try {
            stateCustomers[customerId] = WAITING_REQUEST_REPAIR;    //FIXME: Not needed (?)
            customerQueue.write(customerId);
        } catch (MemException e) {
            Logger.logException(e);
            return false;
        }

        while (stateCustomers[customerId] != ATTENDING)
        {
            try { wait();}
            catch(InterruptedException e){ }
        }
        stateCustomers[customerId] = ATTENDED;
        return true;
    }

    /**
     *  Operation to attend customer. Can be for receive payment or to initiate the repair of a car.
     *
     *      @note Manager invokes this method.
     *
     *      @return success of the operation so the Mechanic can move on or not to the next operation.
     * */
    public synchronized boolean attendCustomer()
    {   if (customerQueue.isEmpty()) { return (false); }
        try {
            int customerId = customerQueue.read();
            stateCustomers[customerId] = ATTENDING;
            notifyAll(); //Customer notified that is being attended.

            if(paymentQueue.peek() == customerId){                      //Customer want to make payment
                paymentQueue.read();
                int i = 0;
                for(;i<usedReplacementCarKeys.length;i++) {
                    if (usedReplacementCarKeys[i] == customerId) break; //Checks if it was given a replacement key
                }

                while (usedReplacementCarKeys[i] == customerId
                            || customerCarKeys[customerId] != null)
                {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Logger.logException(e);
                    }
                }
            }
            else
            {
                //TODO: Post Job?
            }

        }
        catch (MemException e) { Logger.logException(e); }
        return (true);
    }

    /**
     *  Get replacement car key.
     *
     *      @note Client invokes this method.
     *
     *      @param customerId - ID of the clie125$ Digitnt who needs the replacement car.
     *
     *      @return the key of the replacement car.
     * */
    public synchronized Key getReplacementCarKey(Integer customerId)
    {   //  If there are keys available, the customer will take one.
        if(!replacementCarKeys.isEmpty())
        {   try {
                Key tmp = replacementCarKeys.read();
                this.usedReplacementCarKeys[tmp.getId()] = customerId;      //User registers which key he/she took.
                return tmp;
            }
            catch (MemException e) {
                Logger.logException(e);
                return null;
            }
        }
        //  Else the customer waits for a key.
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
            try { wait(); }
            catch (InterruptedException e) { }
        }
        stateCustomers[customerId] = ATTENDED_W_SUBCAR;
        try { return replacementCarKeys.read(); }           //Returns Key.
        catch (MemException e) {
            Logger.logException(e);
            return  null;
        }
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
    {   try {
            replacementCarKeys.write(key);
            if(!waitingReplacementKey.isEmpty())
            {
                int waitingCustomerId = waitingReplacementKey.read();
                stateCustomers[waitingCustomerId] = GET_REPLACEMENT_CAR;
                notifyAll();
            }
            usedReplacementCarKeys[key.getId()] = -1; //Replacement car is now available.
        } catch (MemException e) {
            Logger.logException(e);
            return false;
        }
        return true;
    }

    /**
     *  Exit Lounge
     *
     *      @param customerId - Id of the customer who will exit the lounge
     *
     *      @note Customer without the need of a replacement car invokes this method.
     *
     * */

    public void exitLounge(Integer customerId) { stateCustomers[customerId] = ATTENDED_WO_SUBCAR; }

    /**
     *  Checks if customer queue is empty
     *
     *      @note Manager invokes this method
     *
     * */
    public boolean isCustomerQueueEmpty() { return customerQueue.isEmpty(); }


    /**
     *  Checks if Replacement Car Keys is Empty
     *
     *      @return boolean (true/false) Available replacement cars/No replacement cars.
     *
     * */
    public boolean isReplacementCarKeysEmpty() { return replacementCarKeys.isEmpty(); }

    /**
     *  Checks if Customer car keys are empty
     *
     *     @return boolean (true/false) Available customers cars/No customers cars.
     * */
    public boolean isCustomerCarKeysEmpty() { return customerCarKeys.length == 0; }

    /**
     *  Customer gives Manager his/hers car key.
     *
     *      @param key - Customer's car key.
     */
    public synchronized void giveManagerCarKey(Key key, Integer customerId)
    {
        customerCarKeys[customerId] = key;
        notifyAll();
    }

    /**
     *  Customer retrieves his/hers car key.
     *
     *      @param idKey - ID of the key to retrieve
     *
     *      @return the Customer's car key.
     * */
    public synchronized Key retrieveCarKey(String idKey)
    {   if(!repairedCarKeys.containsKey(idKey)) return null;
        try
        {   Key tmp = repairedCarKeys.remove(idKey);
            notifyAll();
            return tmp;
        }
        catch (Exception e){ return null;}
    }

    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *
     *      @param customerId - ID of the customer.
     *
     *      @return the Customer's car key.
     * */
    public synchronized Key payForTheService(Integer customerId)
    {
        Logger.log(CUSTOMER,LOCAL,"Payment given.",0,Logger.SUCCESS);
        Logger.log(MANAGER,LOCAL,"Payment received.",0,Logger.SUCCESS);
        Key key = customerCarKeys[customerId];
        customerCarKeys[customerId] = null;
        return key;
    }
    public Integer getNextTask()
    {
        //if(!callCustomerQueue.isEmpty()) return CALL_CUSTOMER;
        if(!customerQueue.isEmpty()) return ATTEND_CUSTOMER; //FIXME This might be an else if instead
        //else if(!fillStockType.isEmpty()) return FILL_STOCK;
        else return READ_PAPER;
    }
}
