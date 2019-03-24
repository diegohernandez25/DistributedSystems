package Locations;

import Loggers.Logger;
import Resources.MemException;
import Resources.MemFIFO;

import javax.security.auth.callback.LanguageCallback;
import java.nio.file.Watchable;
import java.util.function.LongUnaryOperator;

public class Lounge {

    /**
     *  Type of Manager Tasks FIXME
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
                                    LOCAL       = "Lounge",
                                    MECHANIC    = "Mechanic";

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

    private MemFIFO<Integer> carKeysToRepairQueue;
    /**
     *  Queue of car parts needed to be replenished
     *
     *      @serialField carPartsQueue
     *
     * */
    private int[] requiredParts;

    /**
     *      All the Keys of Customer Fixed Cars
     *
     *      @serialField customerFixedCarKeys
     *
     * */
    private MemFIFO<Integer> customerFixedCarKeys;
    /**
     *      Queue of ids of waiting customers
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
     *      @serialField paymentQueue
     * */
    private MemFIFO<Integer> paymentQueue;

    /**
     * Array of car parts that needs to be refilled.
     * Index represents the id of the part to refill stock
     * Value represents the number of stock to refill.
     * Alert: Be careful with concurrency.
     * @serialField carPartsToRefill
     * */
    private int[] carPartsToRefill;


    /**
     * Array with the purpose of getting the id of the customer with the car Key id as an index
     * @serialField memKeyCustomers
     * */
    private int[] memKeysCustomers;


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

        this.memKeysCustomers = new int[clients.length];
        for(int i = 0; i<memKeysCustomers.length;i++)
            this.memKeysCustomers[i] = -1;

        try {
                this.carKeysToRepairQueue = new MemFIFO<>(new Integer[clients.length]);
                this.customerFixedCarKeys = new MemFIFO<>(new Integer[clients.length]);
                this.customerQueue = new MemFIFO<>(new Integer[clients.length]);
                this.waitingReplacementKey = new MemFIFO<>(new Integer[clients.length]);
                this.paymentQueue = new MemFIFO<>(new Integer[clients.length]);
                this.replacementCarKeys = new MemFIFO<>(replacementKeys);
                //this.carsToRepair = new MemFIFO<>(new Integer[clients.length]);
        } catch (MemException e) {
                Logger.logException(e);
        }


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
    public synchronized boolean enterCustomerQueue(int customerId, boolean payment)
    {   String FUNCTION = "enterCustomerQueue";
        if(payment)
        {   try {
            paymentQueue.write(customerId);
            Logger.log(CUSTOMER, LOCAL, FUNCTION,
                    "Customer Entered waiting queue for payment", customerId, 10);

            }
            catch (MemException e) { Logger.logException(e); }
        }
        try {
            stateCustomers[customerId] = WAITING_ATTENDENCE;
            customerQueue.write(customerId);
            Logger.log(CUSTOMER, LOCAL, FUNCTION,
                    "Customer Entered waiting queue for attendance", customerId, 10);
        } catch (MemException e) {
            Logger.logException(e);
            return false;
        }

        while (stateCustomers[customerId] != ATTENDING)
        {
            Logger.log(CUSTOMER, LOCAL, FUNCTION,
                    "Customer Waiting for its turn", customerId, Logger.WARNING);

            try { wait();}
            catch(InterruptedException e){ }
        }
        Logger.log(CUSTOMER, LOCAL, FUNCTION,
                "Customer Attended", customerId, Logger.SUCCESS);
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
    public synchronized int attendCustomer()
    {   String FUNCTION = "attendCustomer";
        if (customerQueue.isEmpty()) {
            Logger.log(MANAGER, LOCAL,FUNCTION,"There are no clients on queue",0,10);
            if(!paymentQueue.isEmpty())
            {
                Logger.log(MANAGER, LOCAL,FUNCTION,"Payment queue should be empty",0,Logger.ERROR);
                System.exit(1);
            }
            return -1;
        }
        try {
            int customerId = customerQueue.read();
            stateCustomers[customerId] = ATTENDING;
            Logger.log(MANAGER, LOCAL,FUNCTION,"Customer "+customerId+" getting attended. Notifying all"
                    ,0,Logger.ERROR);
            notifyAll();
            if(paymentQueue.peek() == customerId){                          //Customer wants to make payment
                Logger.log(MANAGER, LOCAL,FUNCTION,"Customer "+customerId+" wants to make payment"
                        ,0,10);
                if(paymentQueue.read()!=customerId)
                {
                    Logger.log(MANAGER, LOCAL,FUNCTION,
                            "Customer to be attended should be the same customer to receive payment"
                            ,0,Logger.ERROR);
                    System.exit(1);
                }
                int i = 0;
                for(;i<usedReplacementCarKeys.length;i++) {
                    if (usedReplacementCarKeys[i] == customerId)            //Checks if it was given a replacement key
                        break;
                }
                if(i == usedReplacementCarKeys.length)
                {
                    Logger.log(MANAGER, LOCAL,FUNCTION,
                            "Customer "+customerId+" did not request a replacement car"
                            ,0,10);
                    i--;                 //FIXME: Fita cola
                }
                else {
                    Logger.log(MANAGER, LOCAL,FUNCTION,
                            "Manager is waiting for the replacement key taken by customer "+customerId
                            ,0,Logger.WARNING);
                }
                Logger.log(MANAGER, LOCAL,FUNCTION,
                        "Manager is waiting for the payment by customer "+customerId
                        ,0,Logger.WARNING);
                while (usedReplacementCarKeys[i] == customerId
                        || customerCarKeys[customerId] != -1)               // Manager waits for the handling of the
                                                                            // replacement key and the client to
                                                                            // retrieve his/her keys
                { try {
                    Logger.log(MANAGER, LOCAL,FUNCTION,
                            "Manager waiting..."
                            ,0,Logger.WARNING);
                        wait();
                    } catch (InterruptedException e) {
                        Logger.logException(e);
                    }
                }
                Logger.log(MANAGER, LOCAL,FUNCTION,
                        "Manager waked up. Payment from"+customerId+"was successful!"
                        ,0,Logger.SUCCESS);
            }
            else
            {
                Logger.log(MANAGER, LOCAL,FUNCTION,
                        "Customer "+customerId+" wants its car to get repaired"
                        ,0,Logger.SUCCESS);
                int toRepairCarKey;
                while((toRepairCarKey = customerCarKeys[customerId])==-1)
                {
                    Logger.log(MANAGER, LOCAL,FUNCTION,
                            "Manager is waiting for Customer "+customerId+" to give his keys"
                            ,0,Logger.WARNING);
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        Logger.logException(e);
                    }
                }
                Logger.log(MANAGER, LOCAL,FUNCTION,
                        "Manager waken and received key of Customer "+customerId
                        ,0,Logger.SUCCESS);

                carKeysToRepairQueue.write(toRepairCarKey);                 // add car keys to repair queue
                customerCarKeys[customerId] = -1;                           // removes customers keys
                Logger.log(MANAGER, LOCAL,FUNCTION,
                        "Got customer key "+toRepairCarKey+ "for post job"
                        ,0,10);
                return toRepairCarKey;

            }

        }
        catch (MemException e) { Logger.logException(e); }
        Logger.log(MANAGER, LOCAL,FUNCTION, "Ended payment operation",0,10);
        return -2;
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
    public synchronized int getReplacementCarKey(int customerId)
    {   //  If there are keys available, the customer will take one.
        String FUNCTION = "getReplacementCarKey";
        if(!replacementCarKeys.isEmpty())
        {   Logger.log(CUSTOMER,LOCAL,FUNCTION,"Replacement Car keys is not empty",customerId,10);
            try
            {   int tmp = replacementCarKeys.read();
                Logger.log(CUSTOMER,LOCAL,FUNCTION,"Got replacement Key "+tmp,customerId,Logger.SUCCESS);

                this.usedReplacementCarKeys[tmp] = customerId;      //User registers which key he/she took.
                stateCustomers[customerId] = ATTENDED_W_SUBCAR;
                return tmp;                                         //ENDS Here
            }
            catch (MemException e)
            {   Logger.logException(e);
                System.exit(1);
                return -1;
            }
        }
        //  Else the customer waits for a key.
        try
        {   Logger.log(CUSTOMER,LOCAL,FUNCTION,"No replacement Car keys available",customerId,10);
            stateCustomers[customerId] = WAITING_REPLACEMENT_CAR;
            Logger.log(CUSTOMER,LOCAL,FUNCTION,"Enter queue for replacement Car",customerId,10);
            waitingReplacementKey.write(customerId);
        }
        catch (MemException e)
        {   Logger.logException(e);
            System.exit(1);
            return -1;
        }
        while (stateCustomers[customerId] != GET_REPLACEMENT_CAR)
        {   try {
                Logger.log(CUSTOMER,LOCAL,FUNCTION,"Waiting for replacement Car",customerId,Logger.WARNING);
                wait();
            }
            catch (InterruptedException e) { }
        }
        Logger.log(CUSTOMER,LOCAL,FUNCTION,"Customer waken & getting replacement car",customerId,Logger.WARNING);
        stateCustomers[customerId] = ATTENDED_W_SUBCAR;
        try {
            int tmp = replacementCarKeys.read();
            this.usedReplacementCarKeys[tmp] = customerId;      //User registers which key he/she took.
            Logger.log(CUSTOMER,LOCAL,FUNCTION,"Got replacement Key "+tmp,customerId,Logger.SUCCESS);
            return tmp;
        } catch (MemException e){
            Logger.logException(e);
            System.exit(1);
        }
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
    public synchronized boolean returnReplacementCarKey(int key, int customerId)
    {
        String FUNCTION = "returnReplacementCarKey";
        if(replacementCarKeys.containsValue(key))
        {
            Logger.log(CUSTOMER,LOCAL,FUNCTION,"Error: replacement car key is already available. " +
                    "This should not happen",customerId,Logger.ERROR);
            System.exit(1);
        }
        try {
            Logger.log(CUSTOMER,LOCAL,FUNCTION,"Customer returns replacement key",
                    0,Logger.SUCCESS);
            replacementCarKeys.write(key);
            usedReplacementCarKeys[key] = -1;       //unregistered loan
            if(!waitingReplacementKey.isEmpty())    //alert thread waiting for a key
            {   int waitingCustomerId = waitingReplacementKey.read();
                Logger.log(CUSTOMER,LOCAL,FUNCTION,"Customer alerts "+waitingCustomerId+" return replacement key",
                        0,Logger.SUCCESS);
                stateCustomers[waitingCustomerId] = GET_REPLACEMENT_CAR;
            }
            Logger.log(CUSTOMER,LOCAL,FUNCTION,"Manager and Customer waiting for replacement car Key alerted",
                    0,Logger.SUCCESS);
            notifyAll();    //Notifies manager and Customer!
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
    public boolean iscarKeysToRepairQueueEmpty() { return carKeysToRepairQueue.isEmpty(); }

    /**
     *      Customer gives Manager his/hers car key.
     *
     *      @param key - Customer's car key.
     */
    public synchronized void giveManagerCarKey(int key, int customerId)
    {   String FUNCTION = "giveManagerCarKey";
        Logger.log(CUSTOMER,LOCAL,FUNCTION,"Customer gives car key"+key+" to manager",customerId,10);
        customerCarKeys[customerId] = key;
        memKeysCustomers[key] = customerId; //TODO: Should be unregistered at checkout
        Logger.log(CUSTOMER,LOCAL,FUNCTION,"Customer notifies that he/she gave the key to manager",customerId,10);
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
    {   String FUNCTION = "payForTheService";
        Logger.log(CUSTOMER,LOCAL,FUNCTION,"Payment given.",customerId,Logger.SUCCESS);
        int key = customerCarKeys[customerId];
        Logger.log(CUSTOMER,LOCAL,FUNCTION,"Got key "+key,customerId,Logger.SUCCESS);
        customerCarKeys[customerId] = -1;
        Logger.log(CUSTOMER,LOCAL,FUNCTION,"Notifying manager that the customer has taken the key",customerId,Logger.WARNING);
        notifyAll();
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
    public synchronized int getCarToRepairKey(int mechanicId)
    {
        String FUNCTION  = "getCarKey";
        if(isCustomerCarKeysEmpty())
        {
            Logger.log(MECHANIC,LOCAL,FUNCTION,"Error: There is no key, this should not happen!",mechanicId,Logger.ERROR);
            return -1;
        }
        try
        {
            stateMechanics[mechanicId] = FIXING_THE_CAR;
            int tmpKey = carKeysToRepairQueue.read();
            Logger.log(MECHANIC,LOCAL,FUNCTION,"Got the keys "+carKeysToRepairQueue.read()
                    +" to fix correspondent car",mechanicId,Logger.SUCCESS);
            return tmpKey;
        }
        catch (Exception e){
            Logger.logException(e);
            System.exit(1);
        }
        Logger.log(MECHANIC,LOCAL,FUNCTION,"Error: There is no key at the end, this should not happen!",mechanicId,Logger.ERROR);
        System.exit(1);
        return -1;
    }


    /**
     *      Mechanic asks for a type of car parts for the repair
     *
     *      @param idType   - the id of the part to refill stock
     *      @param number   - the number of stock needed    //FIXME: maybe not needed
     *
     *      @return true - request done. false - request has already been made.
     * */
   public synchronized boolean requestPart(int idType, int number, int mechanicId)
   {   String FUNCTION = "requestPart";
        Logger.log(MECHANIC,LOCAL,FUNCTION,"Requesting car parts of type "+idType,mechanicId,10);
       if(carPartsToRefill[idType] == 0)
       {    Logger.log(MECHANIC,LOCAL,FUNCTION,"Registered request of type part:"+idType,mechanicId,Logger.SUCCESS);
            carPartsToRefill[idType] = number;
            return true;
       }
       Logger.log(MECHANIC,LOCAL,FUNCTION,"Request has already been made of type part:"+idType,mechanicId,Logger.WARNING);
       return false;
   }

   public synchronized boolean registerStockRefill(int idType)
   {   String FUNCTION = "registerStockRefill";
       if(carPartsToRefill[idType] != 0)
       {    Logger.log(MECHANIC,LOCAL,FUNCTION,"Done request of type part:"+idType,0,Logger.SUCCESS);
           carPartsToRefill[idType] = 0;
            return true;
       }
       Logger.log(MANAGER, LOCAL, "Error: stock refill has already been made. THis should not happen",0,Logger.ERROR);
       return false;
   }

   /**
    *       Manager checks if parts needs to be refilled
    *
    *       @return id of the part to refill. Returns -1 if no part is needed to refill
    * */
    public synchronized int checksPartsRequest(int index)
    {   String FUNCTION = "checksPartsRequest";
        Logger.log(MANAGER,LOCAL,FUNCTION,"Checking if refill of stock is needed",0,10);
        for(int i= index; i<carPartsToRefill.length; i++)
        {   if(carPartsToRefill[i] != 0)
            {   Logger.log(MANAGER,LOCAL,FUNCTION,"Part type "+i+"requires stock refill",0,Logger.WARNING);
                return i;
            }

        }
        Logger.log(MANAGER,LOCAL,FUNCTION,"No stock refill needed",0,10);
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
     *Mechanic return key of the repaired car
     *@param idKey    - the id of the key (= idCar)
     * */
    public void alertManagerRepairDone(int idKey, int mechanicId)
    {   String FUNCTION = "alertRepairDone";
        if(customerFixedCarKeys.containsValue(idKey))
        {
            Logger.log(MECHANIC,LOCAL,FUNCTION,
                    "Car was already registered as repaired. This should not happen",mechanicId,Logger.ERROR);
        }
        try {
            customerFixedCarKeys.write(idKey);
            Logger.log(MECHANIC,LOCAL,FUNCTION,
                    "Car registered as fixed. alert to customer need to be done",mechanicId,10);

        } catch (MemException e) {
            Logger.logException(e);
            System.exit(1);
        }
    }

    /**
     *Checks if there are cars repaired
     * */
    public boolean isCustomerFixedCarKeysEmpty() { return customerFixedCarKeys.isEmpty(); }



    public synchronized int getFixedCarKey()
    {   String FUNCTION = "getFixedCarKey";
        if(!isCustomerCarKeysEmpty())
        {   Logger.log(MANAGER,LOCAL,FUNCTION,"getting car key of repaired car",0,10);
            try {
                return customerFixedCarKeys.read();
            } catch (MemException e) {
                Logger.logException(e);
                System.exit(1);
            }
        }
        Logger.log(MANAGER,LOCAL,FUNCTION,"There are no repaired cars registered",0, Logger.WARNING);
        return -1;
    }

    public synchronized int getCustomerFromKey(int idKey)
    {
        String FUNCTION = "getCustomerFromKey";
        if(memKeysCustomers[idKey] == -1)
        {
            Logger.log(MANAGER,LOCAL,FUNCTION,
                    "Error: Key does not have a customer registered!. This should no happend",
                    0,Logger.ERROR);
            System.exit(1);
        }
        int customerId = memKeysCustomers[idKey];
        Logger.log(MANAGER, LOCAL,FUNCTION,"Key "+idKey+" belongs to "+customerId,0,10);
        memKeysCustomers[idKey] = -1;
        return customerId;
    }

    public synchronized void readyToDeliverKey(int idCustomer, int idKey)
    {   String FUNCTION= "readyToDeliverKey";
        Logger.log(MANAGER, LOCAL,FUNCTION,"Ready to deliver key "+idKey+" to customer "+idCustomer,0,10);
        customerCarKeys[idCustomer] = idKey;
    }
    /**
     * Get the number of existing part car types.
     * @return number of type parts.
     * */
    public int getNumberOfPartTypes(){ return requiredParts.length;}

    /**
     *  Get the requested number of a part
     * */
    public int requestedNumberPart(int partId) { return carPartsToRefill[partId]; }

}
