package Locations;

import Interfaces.*;
import Resources.MemException;
import Resources.MemFIFO;

public class Lounge implements ManagerLounge, CustomerLounge, MechanicLounge {

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
     *  Initialize GeneralRepInformation
     * */
    private GriLounge gri;

    /**
     *  Current state of Manager
     *
     *      @serialField stateManager
     *
     * */
    private volatile int stateManager;

    /**
     *  Current states of customers.
     *
     *      @serialField stateCustomers
     * */
    private volatile int[] stateCustomers;

    /**
     *  Current states of mechanics.
     *
     *      @serialField stateMechanics
     * */
    private volatile int[] stateMechanics;

    /**
     *  All the Keys of Replacement Cars
     *
     *      @serialField replacementCarKeys
     *
     * */
    private volatile MemFIFO<Integer> replacementCarKeys; //Car key id == key id
    /**
     *  Record all replacement Keys used by a Customer ID
     *
     *      @serialField  usedReplacementCarKeys
     * */
    private volatile int[] usedReplacementCarKeys;

    /**
     *  All the Keys of Customer Cars
     *
     *      @serialField customerCarKeys
     *
     * */

    private volatile int[] customerCarKeys; //TODO Should have the length of total users

    /**
     *  Queue of Car Keys to be repaired
     *
     *      @serialField carKeysQueue
     *
     * */

    private volatile MemFIFO<Integer> carKeysToRepairQueue;
    /**
     *  Queue of car parts needed to be replenished
     *
     *      @serialField carPartsQueue
     *
     * */
    private volatile int numTypes;

    /**
     *      All the Keys of Customer Fixed Cars
     *
     *      @serialField customerFixedCarKeys
     *
     * */
    private volatile MemFIFO<Integer> customerFixedCarKeys;
    /**
     *      Queue of ids of waiting customers
     *
     *      @serialField customerQueue
     *
     * */
    private volatile MemFIFO<Integer> customerQueue;

    /**
     *      Queue of customer ids waiting for a replacement key.
     *
     *          @serialField waitingReplacementKey
     *
     * */
    private volatile MemFIFO<Integer> waitingReplacementKey;


    /**
     *      Stack of clients which are waiting for payment
     *      @serialField paymentQueue
     * */
    private volatile MemFIFO<Integer> paymentQueue;

    /**
     * Array of car parts that needs to be refilled.
     * Index represents the id of the part to refill stock
     * Value represents the number of stock to refill.
     * Alert: Be careful with concurrency.
     * @serialField carPartsToRefill
     * */
    private volatile int[] carPartsToRefill;


    /**
     * Array with the purpose of getting the id of the customer with the car Key id as an index
     * @serialField memKeyCustomers
     * */
    private volatile int[] memKeysCustomers;

    /**
     * Header of the headReplacementKeys.
     * @serialField headReplacementKeys.
     * */
    private volatile int headReplacementKeys;

    /**
     *Array whom index represents de ID of a customer and its value a boolean which tells if service has been concluded.
     * @serialField customerFinished
     * */
    private volatile boolean[] customerFinished;


    /**
     * Lounge
     * @param numCustomers - number of customer
     * @param numMechanics - number of mechanics
     * @param replacementKeys - array with the replacement Keys.
     * @param numTypes - number of existing car types.
     * @param gri - logger
     * */
    public Lounge(int numCustomers, int numMechanics,int[] replacementKeys, int numTypes, GriLounge gri)
    {
        this.gri = gri;
        gri.setNumReplacementParked(replacementKeys.length);

        this.stateManager = READ_PAPER;
        this.numTypes = numTypes;
        this.stateCustomers = new int[numCustomers];
        this.customerCarKeys = new int[numCustomers];
        this.memKeysCustomers = new int[numCustomers];
        this.customerFinished = new boolean[numCustomers];
        for (int i = 0; i<stateCustomers.length; i++) {
            this.stateCustomers[i] = NORMAL_LIFE;
            this.customerCarKeys[i] = -1;
            this.memKeysCustomers[i] = -1;
            this.customerFinished[i] = false;
        }

        this.stateMechanics = new int[numMechanics];
        for (int i = 0; i<stateMechanics.length; i++)
            this.stateMechanics[i] = WAITING_FOR_WORK;


        this.usedReplacementCarKeys = new int[replacementKeys.length];
        for(int i = 0;i <replacementKeys.length; i++)
        {
            this.usedReplacementCarKeys[i] = -1;    //replacement keys not being used by anyone
        }
        this.headReplacementKeys = numCustomers;

        this.carPartsToRefill = new int[numTypes];
        for (int i = 0; i<carPartsToRefill.length; i++)
            this.carPartsToRefill[i] = 0;


        try {
            this.carKeysToRepairQueue = new MemFIFO<>(new Integer[numCustomers]);
            this.customerFixedCarKeys = new MemFIFO<>(new Integer[numCustomers]);
            this.customerQueue = new MemFIFO<>(new Integer[numCustomers]);
            this.waitingReplacementKey = new MemFIFO<>(new Integer[numCustomers]);
            this.paymentQueue = new MemFIFO<>(new Integer[numCustomers]);
            this.replacementCarKeys = new MemFIFO<>(new Integer[replacementKeys.length]);
            for(int i= 0; i<replacementKeys.length; i++)
            {
                replacementCarKeys.write(replacementKeys[i]);   //making replacement keys available
            }

        } catch (MemException e) {
        }

    }

    /**
     *  Customer enters queue to be attended by the Manager.
     *  Customer invokes this method
     *      @param customerId - Id of the customer to be attended
     *      @param payment - type of attendance. (true/false) Pay for repair/Request repair.
     *      @return operation success so the thread can move on to the next operation.
     * */
    public synchronized boolean enterCustomerQueue(int customerId, boolean payment)
    {   String FUNCTION = "enterCustomerQueue";
        gri.addCustomersQueue();                                    //Logs Customer entering queue
        if(payment)
        {   try {
            paymentQueue.write(customerId);

        }
        catch (MemException e) {  }
        }
        try {
            stateCustomers[customerId] = WAITING_ATTENDENCE;
            gri.setStateCustomer(customerId, stateCustomers[customerId]);
            customerQueue.write(customerId);
        } catch (MemException e) {
            return false;
        }

        while (stateCustomers[customerId] != ATTENDING)
        {

            try { wait();}
            catch(InterruptedException e){ }
        }
        stateCustomers[customerId] = ATTENDED;
        gri.setStateCustomer(customerId, stateCustomers[customerId]);
        gri.removeCustomersQueue();                                 //Logs Customer exists queue
        return true;
    }

    /**
     *  Operation to attend customer. Can be for receive payment or to initiate the repair of a car.
     *  Manager invokes this method.
     *  @return success of the operation so the Mechanic can move on or not to the next operation.
     * */
    public synchronized int attendCustomer()
    {   String FUNCTION = "attendCustomer";
        gri.setStateManager(CALL_CUSTOMER);
        if (customerQueue.isEmpty()) {
            if(!paymentQueue.isEmpty())
            {
                System.exit(1);
            }
            gri.setStateManager(READ_PAPER);
            return -1;
        }
        try {
            int customerId = customerQueue.read();

            gri.setStateManager(ATTEND_CUSTOMER);
            stateCustomers[customerId] = ATTENDING;
            gri.setStateCustomer(customerId, stateCustomers[customerId]);
            notifyAll();
            //if(!paymentQueue.isEmpty() && paymentQueue.peek() == customerId){                          //Customer wants to make payment
            if(paymentQueue.numElements()!=0 && paymentQueue.peek() == customerId){
                if(paymentQueue.read()!=customerId)
                {
                    System.exit(1);
                }
                int i = 0;
                boolean flag = false;
                for(;i<usedReplacementCarKeys.length;i++) {
                    if (usedReplacementCarKeys[i] == customerId)
                    {   flag = true;
                        break;
                    }
                }


                if(flag)
                {
                    while (usedReplacementCarKeys[i] == customerId
                            || customerCarKeys[customerId] != -1)               // Manager waits for the handling of the
                    // replacement key and the client to
                    // retrieve his/her keys
                    { try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                    }
                }
                else{
                    while(customerCarKeys[customerId] != -1)
                    {
                        try {
                            wait();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
            else
            {
                int toRepairCarKey;
                while((toRepairCarKey = customerCarKeys[customerId])==-1)
                {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }

                carKeysToRepairQueue.write(toRepairCarKey);                 // add car keys to repair queue
                customerCarKeys[customerId] = -1;                           // removes customers keys
                gri.setStateManager(READ_PAPER);
                return toRepairCarKey;

            }

        }
        catch (MemException e) {  }
        gri.setStateManager(READ_PAPER);
        return -2;
    }

    /**
     * Get replacement car key.
     * @param customerId - ID of the client who needs the replacement car.
     * @return the key of the replacement car.
     * */
    public synchronized int getReplacementCarKey(int customerId)
    {   //  If there are keys available, the customer will take one.
        String FUNCTION = "getReplacementCarKey";
        gri.setCustomerNeedsReplacement(customerId);            //Logs Customer needs a replacement vehicle
        gri.addCustomersReplacementQueue();                     //Logs Customer enters replacement car queue
        if(!replacementCarKeys.isEmpty())
        {
            try
            {   int tmp = replacementCarKeys.read();

                if(tmp - headReplacementKeys < 0)
                {
                    System.exit(1);
                }
                usedReplacementCarKeys[tmp - headReplacementKeys] = customerId;
                stateCustomers[customerId] = ATTENDED_W_SUBCAR;
                gri.setStateCustomer(customerId, stateCustomers[customerId]);
                gri.removeCustomersReplacementQueue();                  //Logs Customer exits replacement car queue

                return tmp;                                         //ENDS Here
            }
            catch (MemException e)
            {
                System.exit(1);
                return -1;
            }
        }
        //  Else the customer waits for a key.
        try
        {
            stateCustomers[customerId] = WAITING_REPLACEMENT_CAR;
            gri.setStateCustomer(customerId, stateCustomers[customerId]);
            waitingReplacementKey.write(customerId);
        }
        catch (MemException e)
        {
            System.exit(1);
            return -1;
        }
        while (stateCustomers[customerId] != GET_REPLACEMENT_CAR)
        {   try {
            wait();
        }
        catch (InterruptedException e) { }
        }
        stateCustomers[customerId] = ATTENDED_W_SUBCAR;
        gri.setStateCustomer(customerId, stateCustomers[customerId]);
        gri.removeCustomersReplacementQueue();                  //Logs Customer exits replacement car queue
        try {
            int tmp = replacementCarKeys.read();
            if(tmp - headReplacementKeys < 0)
            {
                System.exit(1);
            }
            this.usedReplacementCarKeys[tmp - headReplacementKeys] = customerId;
            return tmp;
        } catch (MemException e){
            System.exit(1);
        }
        return -1;
    }

    /**
     *  Return Replacement car key.
     *  Customer with the need of a replacement car invokes this method.
     *
     *      @param key - Key of the replacement car key.
     *      @param customerId ID of the current Customer returning the replacement car key
     *
     *      @return status of the operation.
     * */
    public synchronized boolean returnReplacementCarKey(int key, int customerId)
    {
        String FUNCTION = "returnReplacementCarKey";

        if(replacementCarKeys.containsValue(key))
        {
            System.exit(1);
        }
        try {
            replacementCarKeys.write(key);
            //FIXME usedReplacementCarKeys[key] = -1;       //unregistered loan
            usedReplacementCarKeys[key - headReplacementKeys] = -1;
            if(!waitingReplacementKey.isEmpty())    //alert thread waiting for a key
            {   int waitingCustomerId = waitingReplacementKey.read();
                stateCustomers[waitingCustomerId] = GET_REPLACEMENT_CAR;
                gri.setStateCustomer(customerId, stateCustomers[customerId]);
            }
            notifyAll();    //Notifies manager and Customer!!!
        } catch (MemException e) {
            return false;
        }
        return true;
    }
    /**
     * Exit Lounge
     * @param customerId - Id of the customer who will exit the lounge
     * */
    public synchronized void exitLounge(int customerId) {
        stateCustomers[customerId] = ATTENDED_WO_SUBCAR;
        gri.setStateCustomer(customerId, stateCustomers[customerId]);
    }

//    /**
//     *  Checks if customer queue is empty
//     *  @return customer
//     * */
//    public boolean isCustomerQueueEmpty() { return customerQueue.isEmpty(); }


//    /**
//     *  Checks if Replacement Car Keys is Empty
//     *  @return boolean (true/false) Available replacement cars/No replacement cars.
//     * */
//    public boolean isReplacementCarKeysEmpty() { return replacementCarKeys.isEmpty(); }

    /**
     *  Checks if Customer car keys are empty
     *  @return boolean (true/false) Available customers cars/No customers cars.
     * */
    private boolean isCustomerCarKeysEmpty() { return customerCarKeys.length == 0; }

//    /**
//     * Checks the size of Customer Car Keys
//     * @return int size of the array, useful to check the total number of clients
//     * */
//    public int customerCarKeysSize() { return customerCarKeys.length; }

//    /**
//     *  Checks if queue of keys of cars to be repaired is empty
//     *  @return boolean (true/false) No available cars to be repaired/Available cars to be repaired.
//     * */
//    public boolean iscarKeysToRepairQueueEmpty() { return carKeysToRepairQueue.isEmpty(); }

    /**
     * Customer gives Manager his/hers car key.
     * @param key - Customer's car key.
     * @param customerId current Customer giving their car key
     */
    public synchronized void giveManagerCarKey(int key, int customerId)
    {   String FUNCTION = "giveManagerCarKey";
        customerCarKeys[customerId] = key;
        memKeysCustomers[key] = customerId; //TODO: Should be unregistered at checkout
        notifyAll();
    }


    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *  @param customerId - ID of the customer.
     *  @return the Customer's car key.
     * */
    public synchronized int payForTheService(int customerId)
    {   String FUNCTION = "payForTheService";
        int key = customerCarKeys[customerId];
        customerCarKeys[customerId] = -1;
        notifyAll();
        customerFinished[customerId] = true;
        return key;
    }
    /**
     *  Mechanic gets keys of car to be repaired
     *  @param mechanicId ID of the Mechanic
     *  @return key of the car to repair
     *  */
    public synchronized int getCarToRepairKey(int mechanicId)
    {
        String FUNCTION  = "getCarKey";
        if(isCustomerCarKeysEmpty())
        {
            return -1;
        }
        try
        {
            stateMechanics[mechanicId] = FIXING_THE_CAR;
            gri.setStateMechanic(mechanicId, stateMechanics[mechanicId]);
            int tmpKey = carKeysToRepairQueue.read();
            return tmpKey;
        }
        catch (Exception e){
            System.exit(1);
        }
        System.exit(1);
        return -1;
    }


    /**
     *      Mechanic asks for a type of car parts for the repair
     *      @param idType       - the id of the part to refill stock
     *      @param number       - the number of stock needed
     *      @param mechanicId   - the id of the mechanic
     *      @return true - request done. false - request has already been made.
     * */
    public synchronized boolean requestPart(int idType, int number, int mechanicId)
    {   String FUNCTION = "requestPart";
        //if(carPartsToRefill[idType] == 0)
        //{
        gri.setFlagMissingPart(idType, "T");                        // Log Manager has been advised for missing part
        gri.setNumCarWaitingPart(idType, 1);                        // Log new car waiting for part
        //carPartsToRefill[idType] = number;
        carPartsToRefill[idType] += number;

        return true;
        //}
        //return false;
    }
    /**
     *  register refill of stock
     *  @param idType - the type of Car Part
     *  @param numberParts number of Car Parts being refilled
     *  @return completed with success
     * */
    public synchronized boolean registerStockRefill(int idType, int numberParts)
    {   String FUNCTION = "registerStockRefill";
        gri.setStateManager(FILL_STOCK);
        if(carPartsToRefill[idType] != 0)
        {
            if(carPartsToRefill[idType] == numberParts) {
                carPartsToRefill[idType] = 0;
                gri.setStateManager(READ_PAPER);
            }
            else
            {
                carPartsToRefill[idType] -= numberParts;
                gri.setStateManager(READ_PAPER);
            }
            return true;
        }
        gri.setStateManager(READ_PAPER);
        return false;
    }

    /**
     * Manager checks if parts needs to be refilled
     * @param index - start index of search
     * @return id of the part to refill. Returns -1 if no part is needed to refill
     * */
    public synchronized int checksPartsRequest(int index)
    {   String FUNCTION = "checksPartsRequest";
        for(int i= index; i<carPartsToRefill.length; i++)
        {   if(carPartsToRefill[i] != 0)
        {
            return i;
        }

        }
        return -1;
    }

//    /**
//     * Mechanic checks stock of a specific part.
//     * @param idType   - id of the car part.
//     * @return True if there is stock available for the part. False otherwise.
//     * */
//    public synchronized boolean checksPartStock(int idType) { return carPartsToRefill[idType] != 0; }



    /**
     * Mechanic return key of the repaired car
     * @param idKey         - the id of the key (= idCar)
     * @param mechanicId    - the id of the mechanic.
     * */
    public synchronized void alertManagerRepairDone(int idKey, int mechanicId)
    {   String FUNCTION = "alertRepairDone";
        gri.setStateMechanic(mechanicId, ALERTING_MANAGER);
        if(!customerFixedCarKeys.isEmpty())
        {
            if(customerFixedCarKeys.containsValue(idKey)) {
                System.exit(1);
            }
        }
        try {
            customerFixedCarKeys.write(idKey);

        } catch (MemException e) {
            System.exit(1);
        }
    }

    /**
     * Checks if there are cars repaired
     * @return true - customer fixed car keys empty
     * */
    public synchronized boolean isCustomerFixedCarKeysEmpty() { return customerFixedCarKeys.isEmpty(); }


    /**
     * Gets key of a fixed car.
     * @return id of the key
     * */
    public synchronized int getFixedCarKey()
    {   String FUNCTION = "getFixedCarKey";
        if(!isCustomerCarKeysEmpty())
        {
            try {
                return customerFixedCarKeys.read();
            } catch (MemException e) {
                System.exit(1);
            }
        }
        return -1;
    }
    /**
     * Gets customer given the id of the key whom the customer belongs-
     * @param idKey - id of the key.
     * @return the id of the customer
     * */
    public synchronized int getCustomerFromKey(int idKey)
    {
        String FUNCTION = "getCustomerFromKey";
        if(memKeysCustomers[idKey] == -1)
        {
            System.exit(1);
        }
        int customerId = memKeysCustomers[idKey];
        memKeysCustomers[idKey] = -1;
        return customerId;
    }
    /**
     * Make key ready to give back to customer.
     * @param idCustomer - id of the customer.
     * @param idKey - id of the key.
     * */
    public synchronized void readyToDeliverKey(int idCustomer, int idKey)
    {   String FUNCTION= "readyToDeliverKey";
        customerCarKeys[idCustomer] = idKey;
    }

//    /**
//     * Get the number of existing part car types.
//     * @return number of type parts.
//     * */
//    public synchronized int getNumberOfPartTypes(){ return numTypes;}

    /**
     *  Get the requested number of a part
     *  @param partId - ID of the part Car.
     *  @return number of parts requested.
     * */
    public synchronized int requestedNumberPart(int partId) { return carPartsToRefill[partId]; }


    /**
     * Checks if all customer have been attended.
     * @return true - all services done to all customers for the dat. False - otherwise
     * */
    public synchronized boolean allDone()
    {
        for(int i = 0; i<customerFinished.length;i++) {if(!customerFinished[i]) return false; }
        return true;
    }
}
