package Locations;

import Loggers.Logger;
import Resources.MemException;
import Resources.MemFIFO;
import Resources.MemStack;

import java.util.Arrays;
import java.util.HashMap;

public class Lounge {

    /**
     *  Type of Manager Tasks
     * */
    private static final int        READ_PAPER = 0,
                                    ATTEND_CUSTOMER = 1,
                                    CALL_CUSTOMER = 2,
                                    FILL_STOCK = 3;
    /**
     *  Created Constants for the Logger
     *  */
    private static final String     MANAGER     = "Manager",
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
                                GETTING_REPLACEMENT_CAR = 9,
                                WAITING_ATTENDENCE = 10;

    /**
     *  Mechanic states
     *  */
    private final static int    WAITING_FOR_WORK = 0,
                                FIXING_THE_CAR = 1,
                                CHECKING_STOCK = 2,
                                ALERTING_MANAGER = 3;

    /**
     *  Current state of Manager
     *
     *      @serialField stateManager
     *
     * */
    private int stateManager;

    /**
     *  Current states of customers.
     *
     *      @serialField stateCustomers
     * */
    private int[] stateCustomers;

    /**
     *  Current states of mechanics.
     *
     *      @serialField stateMechanics
     * */
    private int[] stateMechanics;

    /**
     *  All the Keys of Replacement Cars
     *
     *      @serialField replacementCarKeys
     *
     * */
    private MemFIFO<Integer> replacementCarKeys; //Car key id == key id
    /**
     *  Record all replacement Keys used by a Customer ID
     *
     *      @serialField  usedReplacementCarKeys
     * */
    private int[] usedReplacementCarKeys;

    /**
     *  All the Keys of Customer Cars
     *
     *      @serialField customerCarKeys
     *
     * */

    private int[] customerCarKeys; //TODO Should have the length of total users

    /**
     *  Queue of Car Keys to be repaired
     *
     *      @serialField carKeysQueue
     *
     * */

    private MemFIFO<Integer> carKeysRepairQueue;
    /**
     *  Queue of car parts needed to be replenished
     *
     *      @serialField carPartsQueue
     *
     * */
    private int[] requiredParts;

    /**
     *  All the Keys of Customer Fixed Cars
     *
     *      @serialField customerFixedCarKeys
     *
     * */
    private MemFIFO<Integer> customerFixedCarKeys;
    /**
     *  Queue of ids of waiting customers
     *
     *      @serialField replacementCarKeys
     *
     * */

    private MemFIFO<Integer> customerQueue;

    /**
     *      Queue of customer ids waiting for a replacement key.
     *
     *          @serialField waitingReplacementKey
     *
     * */
    private MemFIFO<Integer> waitingReplacementKey;


    /**
     *      Stack of clients which are waiting for payment
     *
     *      @serialField paymentQueue
     * */
    private MemFIFO<Integer> paymentQueue;

    /**
     *      Storage of car keys.
     *
     *      @serialField  repairedCarKeys
     * */
    //private HashMap<String,Key> repairedCarKeys;
    /**
     *      HashMap of customer cars and its associated keys.
     *
     *      @serialField repairedCarKeys
     *
     */
    private HashMap<Integer, Integer> repairedCarKeys;


    /**
     *      FIFO of all the current car to check and repair
     *
     *      @serialField carsToRepair
     *
     * */
    //private MemFIFO<Integer> carsToRepair; FIXME: Maybe not needed

    /**
     *      Array of car parts that needs to be refilled.
     *      Index represents the id of the part to refill stock
     *      Value represents the number of stock to refill.
     *      Alert: Be careful with concurrency.
     *
     *      @serialField carPartsToRefill
     * */
    private int[] carPartsToRefill;

    private boolean requestPart;

    /**
     *
     * Instantiation of the Lounge
     *      @param replacementKeys - Array of Key objects. NOTE: Must have the size equal
     *                               to the total number of replacement cars.
     *      @param clients - clients
     *      @param mechanics - mechanics
     *      @param customerKeys - customer's keys
     * */
    public Lounge(Integer[] replacementKeys, int[] clients, int[] mechanics, int[] customerKeys, int[] types)
    {
        this.stateManager = READ_PAPER;

        this.stateCustomers = new int[clients.length];
        for (int i = 0; i<stateCustomers.length; i++)
            this.stateCustomers[i] = NORMAL_LIFE;

        this.stateMechanics = new int[mechanics.length];
        for (int i = 0; i<stateMechanics.length; i++)
            this.stateMechanics[i] = WAITING_FOR_WORK;

        this.usedReplacementCarKeys = new int[replacementKeys.length];
        for (int i = 0; i<usedReplacementCarKeys.length; i++)
            this.usedReplacementCarKeys[i] = -1;

        this.customerCarKeys = new int[clients.length];
        for (int i = 0; i<customerCarKeys.length; i++)
            this.customerCarKeys[i] = -1;

        this.carPartsToRefill = new int[types.length];
        for (int i = 0; i<carPartsToRefill.length; i++)
            this.carPartsToRefill[i] = 0;

        this.repairedCarKeys = new HashMap<>(); //FIXME: Is it needed?

        try {
                this.carKeysRepairQueue = new MemFIFO<>(new Integer[clients.length]);
                this.customerFixedCarKeys = new MemFIFO<>(new Integer[clients.length]);
                this.customerQueue = new MemFIFO<>(new Integer[clients.length]);
                this.waitingReplacementKey = new MemFIFO<>(new Integer[clients.length]);
                this.paymentQueue = new MemFIFO<>(new Integer[clients.length]);
                this.replacementCarKeys = new MemFIFO<>(replacementKeys);
                //this.carsToRepair = new MemFIFO<>(new Integer[clients.length]);
        } catch (MemException e) {
                Logger.logException(e);
        }

        requestPart = false;

        System.out.println(this.toString());
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
    public synchronized boolean enterCustomerQueue(int customerId, boolean payment) // Waking function -> attendCustomer()
    {   if(payment)
        {   try { paymentQueue.write(customerId); }                 //paymentQueue and customerQueue needs to be
                                                                    // synchronized
            catch (MemException e) { Logger.logException(e); }
        }
        try {
            stateCustomers[customerId] = WAITING_ATTENDENCE;
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
            notifyAll();                                                    //Customer notified that is being attended.
            if(paymentQueue.peek() == customerId){                          //Customer wants to make payment
                paymentQueue.read();
                int i = 0;
                for(;i<usedReplacementCarKeys.length;i++) {
                    if (usedReplacementCarKeys[i] == customerId)            //Checks if it was given a replacement key
                        break;
                }

                while (usedReplacementCarKeys[i] == customerId
                        || customerCarKeys[customerId] != -1)               // Manager waits for the handling of the
                                                                            // replacement key and the client to
                                                                            // retrieve his/her keys
                { try {
                        wait();
                    } catch (InterruptedException e) {
                        Logger.logException(e);
                    }
                }
            }
            else
            {
                //TODO: Patricia
                carKeysRepairQueue.write(customerCarKeys[customerId]);      // add car keys to repair queue
                customerCarKeys[customerId] = -1;                           // removes customers keys
                notifyAll();                                                // notify Mechanic that a car is available to be repaired
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
    public synchronized int getReplacementCarKey(int customerId)
    {   //  If there are keys available, the customer will take one.
        if(!replacementCarKeys.isEmpty())
        {   try {
            int tmp = replacementCarKeys.read();
            this.usedReplacementCarKeys[tmp] = customerId;      //User registers which key he/she took.
            stateCustomers[customerId] = ATTENDED_W_SUBCAR;
            return tmp;
        }
        catch (MemException e) {
            Logger.logException(e);
            return -1;
        }
        }
        //  Else the customer waits for a key.
        try
        {   stateCustomers[customerId] = WAITING_REPLACEMENT_CAR;
            waitingReplacementKey.write(customerId);
        }
        catch (MemException e)
        {
            Logger.logException(e);
            return -1;
        }
        while (stateCustomers[customerId] != GET_REPLACEMENT_CAR)
        {   try { wait(); }
            catch (InterruptedException e) { }
        }
        stateCustomers[customerId] = ATTENDED_W_SUBCAR;
        try {
            int tmp = replacementCarKeys.read();
            this.usedReplacementCarKeys[tmp] = customerId;      //User registers which key he/she took.
            return tmp;
        } catch (MemException e){ }
        return -1;
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
    public synchronized boolean returnReplacementCarKey(int key)
    {   try {
            replacementCarKeys.write(key);
            usedReplacementCarKeys[key] = -1; //Replacement car is now available. //FIXME Becareful with key! the id is not the same as the index
            if(!waitingReplacementKey.isEmpty())
            {   int waitingCustomerId = waitingReplacementKey.read();
                stateCustomers[waitingCustomerId] = GET_REPLACEMENT_CAR;
                notifyAll();
            }
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

    public void exitLounge(int customerId) { stateCustomers[customerId] = ATTENDED_WO_SUBCAR; }

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
     *  Checks if queue of keys of cars to be repaired is empty
     *
     *      @return boolean (true/false) No available cars to be repaired/Available cars to be repaired.
     * */
    public boolean isCarKeysRepairQueueEmpty() { return carKeysRepairQueue.isEmpty(); }

    /**
     *  Checks if queue of car parts needed to be replenished is empty
     *
     *      @return boolean (true/false) No car parts need to be replenished/Car parts available to be replenished.
     * */
    //public boolean isCarPartsQueueEmpty() { return carPartsQueue.isEmpty(); }

    /**
     *  Customer gives Manager his/hers car key.
     *
     *      @param key - Customer's car key.
     */
    public synchronized void giveManagerCarKey(int key, int customerId)
    {
        customerCarKeys[customerId] = key;
        notifyAll();
    }


    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *
     *      @param customerId - ID of the customer.
     *
     *      @return the Customer's car key.
     * */

    public synchronized int payForTheService(int customerId)
    {
        Logger.log(CUSTOMER,LOCAL,"Payment given.",0,Logger.SUCCESS);
        Logger.log(MANAGER,LOCAL,"Payment received.",0,Logger.SUCCESS);
        int key = customerCarKeys[customerId];
        customerCarKeys[customerId] = -1;
        return key;
    }
    /**
     *
     *  Mechanic gets keys of car to be repaired
     *
     *      @param mechanicId ID of the Mechanic
     *
     *      @return key of the car to repair
     *
     *  */
    /*public synchronized int getCarKey(int mechanicId)
    {
        if(isCustomerCarKeysEmpty()) return null;
        try
        {
            stateMechanics[mechanicId] = FIXING_THE_CAR;
            return carKeysRepairQueue.read();
        }
        catch (Exception e){ return null; }
    }*/
    /**
     *
     *  Mechanic alerts the manager for missing car part that is needed
     *
     *  @param carPart car part that is needed to be restocked
     *
     *  @param mechanicId id of the Mechanic
     *
     * */

   /* public synchronized void alertManager(int carPart, int mechanicId)
    {
        stateMechanics[mechanicId] = ALERTING_MANAGER;
        try {
            carPartsQueue.write(carPart);                               // Add car part to queue of need to replenish
        } catch (MemException e) {
            e.printStackTrace();
        }
        notifyAll();                                                // Notifies Manager that car part is needed
    }

    /**
     *
     *  Manager gets first car part waiting to be replenished and removes it
     *
     *  @return car part needed to be replenished
     *
     * */

    /*public synchronized int getPartFromQueue()
    {
        try {
            return carPartsQueue.read();
        } catch (MemException e) {
            e.printStackTrace();
        }
        return null;
    }*/


    /**
     * ---------------------------Mechanic---------------------
     * */

    /**
     *      Mechanic checks if a new car needs repair
     *
     *      @return id of the car to repair. If -1, there is still no car
     *      to repair
     * */
    public synchronized int checkCarToRepair()
    {   if( !carKeysRepairQueue.isEmpty())
        {   try { return carKeysRepairQueue.read(); }
            catch (MemException e) { Logger.logException(e); }
        }
        return -1;
    }

    /**
     *      Mechanic asks for a type of car parts for the repair
     *
     *      @param idType   - the id of the part to refill stock
     *      @param number   - the number of stock needed    //FIXME: maybe not needed
     *
     *      @return the status of the operation
     * */
   public synchronized boolean requestPart(int idType, int number)
   {
       if(carPartsToRefill[idType] == 0)
       {    carPartsToRefill[idType] = number;
            requestPart = true;
            return true;
       }
       return false;
   }

   /**
    *       Manager checks if parts needs to be refilled
    *
    *       @return id of the part to refill. Returns -1 if no part is needed to refill
    * */
    public synchronized int checksPartsRequest()
    {
        for(int i= 0; i<carPartsToRefill.length; i++)
        {   if(carPartsToRefill[i] != 0) return i;
        }
        return -1;
    }

    /**
     *      Mechanic checks stock of a specific part
     *
     *      @param idType   - id of the car part.
     *
     *      @return True if there is stock available for the part. False otherwise.
     * */
    public synchronized boolean checksPartStock(int idType) { return carPartsToRefill[idType] != 0; }

    /**
     *      Manager alerts that stock was replenished.
     *
     *      @param idType   - id of the part whom stock was replenished
     * */
    public synchronized void alertStockRefill(int idType)
    {
        carPartsToRefill[idType] = 0;
    }

}
