package SharedRegions;

import Interfaces.*;
import Locals.OutsideWorld;
import Locals.Park;
import Locals.RepairArea;
import Locals.SupplierSite;
import Resources.MemException;
import Resources.MemFIFO;

public class Lounge {

    /**
     *  Type of Manager Tasks
     * */
    private static final int    READ_PAPER = 0,
                                ATTEND_CUSTOMER = 1,
                                CALL_CUSTOMER = 2,
                                FILL_STOCK = 3;

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
    * Initialize General Repository Information
    */
    private GriLounge gri;

    /**
     *  Current state of Manager
     *
     *
     * */
    private volatile int stateManager;

    /**
     *  Current states of customers.
     *
     * */
    private volatile int[] stateCustomers;

    /**
     *  Current states of mechanics.
     *
     * */
    private volatile int[] stateMechanics;

    /**
     *  All the Keys of Replacement Cars
     *
     *
     * */
    private volatile MemFIFO<Integer> replacementCarKeys; //Car key id == key id
    /**
     *  Record all replacement Keys used by a Customer ID
     *
     * */
    private volatile int[] usedReplacementCarKeys;

    /**
     *  All the Keys of Customer Cars
     *
     *
     * */

    private volatile int[] customerCarKeys;

    /**
     *  Queue of Car Keys to be repaired
     *
     *
     * */

    private volatile MemFIFO<Integer> carKeysToRepairQueue;
    /**
     *  Queue of car parts needed to be replenished
     *
     *
     * */
    private volatile int numTypes;

    /**
     *      All the Keys of Customer Fixed Cars
     *
     *
     * */
    private volatile MemFIFO<Integer> customerFixedCarKeys;
    /**
     *      Queue of ids of waiting customers
     *
     *
     * */
    private volatile MemFIFO<Integer> customerQueue;

    /**
     *      Queue of customer ids waiting for a replacement key.
     *
     *
     * */
    private volatile MemFIFO<Integer> waitingReplacementKey;


    /**
     *      Stack of clients which are waiting for payment
     * */
    private volatile MemFIFO<Integer> paymentQueue;

    /**
     * Array of car parts that needs to be refilled.
     * Index represents the id of the part to refill stock
     * Value represents the number of stock to refill.
     * Alert: Be careful with concurrency.
     * */
    private volatile int[] carPartsToRefill;


    /**
     * Array with the purpose of getting the id of the customer with the car Key id as an index
     * */
    private volatile int[] memKeysCustomers;

    /**
     * Header of the headReplacementKeys.
     * */
    private volatile int headReplacementKeys;

    /**
     *Array whom index represents de ID of a customer and its value a boolean which tells if service has been concluded.
     * */
    private volatile boolean[] customerFinished;

    /**
     * Active mechanics.
     * */
    private volatile int activeMechanics;

    /**
     * Finish flag
     * */
    public volatile boolean finish;

    /**
     * Outside world
     * */
    private volatile OutsideWorld outsideWorld;

    /**
     * Park
     * */
    private volatile Park park;

    /**
     * Repair Area
     * */
    private volatile RepairArea repairArea;

    /***
     * SupplierSite
     */
    private volatile SupplierSite supplierSite;

    /**
     * Lounge
     * @param numCustomers      number of customer
     * @param numMechanics      number of mechanics
     * @param replacementKeys   array with the replacement Keys.
     * @param numTypes          number of existing car types.
     * @param gri               General Repository Information
     * @param outsideWorld      Outside World
     * @param park              Park
     * @param repairArea        Repair Area
     * @param supplierSite      Supplier Site.
     * */
    public Lounge(int numCustomers, int numMechanics, int[] replacementKeys, int numTypes,
                  GriLounge gri, OutsideWorld outsideWorld, Park park, RepairArea repairArea,
                  SupplierSite supplierSite)
    {
        this.gri = gri;
        gri.setNumReplacementParked(replacementKeys.length);

        this.stateManager = READ_PAPER;
        this.numTypes = numTypes;
        this.stateCustomers = new int[numCustomers];
        this.customerCarKeys = new int[numCustomers];
        this.memKeysCustomers = new int[numCustomers];
        this.customerFinished = new boolean[numCustomers];

        this. activeMechanics = numMechanics;
        this.finish = false;
        this.outsideWorld = outsideWorld;
        this.park = park;
        this.repairArea = repairArea;
        this.supplierSite = supplierSite;

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
     *  @param customerId Id of the customer to be attended
     *  @param payment type of attendance. (true/false) Pay for repair/Request repair.
     * */
    public synchronized void enterCustomerQueue(int customerId, boolean payment)
    {
        gri.addCustomersQueue();
        if(payment)
        {   try {   paymentQueue.write(customerId);}
            catch (MemException e) {  }
        }
        try
        {   stateCustomers[customerId] = WAITING_ATTENDENCE;
            gri.setStateCustomer(customerId, stateCustomers[customerId]);
            customerQueue.write(customerId);
        } catch (MemException e) { System.exit(1);}

        while (stateCustomers[customerId] != ATTENDING)
        {   try { wait();}
            catch(InterruptedException e){ }
        }
        stateCustomers[customerId] = ATTENDED;
        gri.setStateCustomer(customerId, stateCustomers[customerId]);
        gri.removeCustomersQueue();
    }

    /**
     *  Operation to attend customer. Can be for receive payment or to initiate the repair of a car.
     *  Manager invokes this method.
     *  @return success of the operation so the Mechanic can move on or not to the next operation.
     * */
    public synchronized int attendCustomer()
    {
        gri.setStateManager(CALL_CUSTOMER);
        if (customerQueue.isEmpty())
        {   if(!paymentQueue.isEmpty())
            {   System.exit(1);
            }
            gri.setStateManager(READ_PAPER);
            return -1; //-1 : No waiting customers.
        }
        try
        {   int customerId = customerQueue.read();
            gri.setStateManager(ATTEND_CUSTOMER);
            stateCustomers[customerId] = ATTENDING;
            gri.setStateCustomer(customerId, stateCustomers[customerId]);
            notifyAll();
            if(paymentQueue.numElements()!=0 && paymentQueue.peek() == customerId)
            {   if(paymentQueue.read()!=customerId)
                {   System.exit(1);
                }
                boolean flag = false;
                int i = 0;
                for(;i<usedReplacementCarKeys.length;i++)
                {   if (usedReplacementCarKeys[i] == customerId)
                    {   flag = true;
                        break;
                    }
                }
                if(flag)
                {   while (usedReplacementCarKeys[i] == customerId
                            || customerCarKeys[customerId] != -1)
                    {   try { wait(); }
                        catch (InterruptedException e) { }
                    }
                }
                else
                {   while(customerCarKeys[customerId] != -1)
                    {   try { wait(); }
                        catch (InterruptedException e) { }
                    }
                }
            }
            else
            {   int toRepairCarKey;
                while((toRepairCarKey = customerCarKeys[customerId])==-1)
                {   try { wait(); }
                    catch (InterruptedException e) { }
                }
                carKeysToRepairQueue.write(toRepairCarKey);
                customerCarKeys[customerId] = -1;
                gri.setStateManager(READ_PAPER);
                return toRepairCarKey;
            }

        }
        catch (MemException e) {  }
        gri.setStateManager(READ_PAPER);
        return -2; // -2 : Customer operation done successful.
    }

    /**
     * Get replacement car key.
     * @param customerId ID of the client who needs the replacement car.
     * @return the key of the replacement car.
     * */
    public synchronized int getReplacementCarKey(int customerId)
    {
        gri.setCustomerNeedsReplacement(customerId);
        gri.addCustomersReplacementQueue();

        if(!replacementCarKeys.isEmpty())
        {   try
            {   int tmp = replacementCarKeys.read();
                if(tmp - headReplacementKeys < 0) { System.exit(1); }
                usedReplacementCarKeys[tmp - headReplacementKeys] = customerId;
                stateCustomers[customerId] = ATTENDED_W_SUBCAR;
                gri.setStateCustomer(customerId, stateCustomers[customerId]);
                gri.removeCustomersReplacementQueue();
                return tmp;
            }
            catch (MemException e)
            {   System.exit(1);
            }
        }
        try
        {   stateCustomers[customerId] = WAITING_REPLACEMENT_CAR;
            gri.setStateCustomer(customerId, stateCustomers[customerId]);
            waitingReplacementKey.write(customerId);
        }
        catch (MemException e)
        {   System.exit(1);
        }
        while (stateCustomers[customerId] != GET_REPLACEMENT_CAR)
        {   try { wait(); }
            catch (InterruptedException e) { }
        }
        stateCustomers[customerId] = ATTENDED_W_SUBCAR;
        gri.setStateCustomer(customerId, stateCustomers[customerId]);
        gri.removeCustomersReplacementQueue();
        try
        {   int tmp = replacementCarKeys.read();
            if(tmp - headReplacementKeys < 0) {System.exit(1); }
            this.usedReplacementCarKeys[tmp - headReplacementKeys] = customerId;
            stateCustomers[customerId] = ATTENDED_W_SUBCAR;
            return tmp;
        } catch (MemException e){
            System.exit(1);
        }
        return -1;
    }

    /**
     *  Return Replacement car key.
     *  Customer with the need of a replacement car invokes this method.
     *  @param key Key of the replacement car key.
     *  @param customerId ID of the current Customer returning the replacement car key.
     * */
    public synchronized void returnReplacementCarKey(int key, int customerId)
    {   if(replacementCarKeys.containsValue(key)) { System.exit(1); }
        try
        {   replacementCarKeys.write(key);
            usedReplacementCarKeys[key - headReplacementKeys] = -1;
            if(!waitingReplacementKey.isEmpty())
            {   int waitingCustomerId = waitingReplacementKey.read();
                stateCustomers[waitingCustomerId] = GET_REPLACEMENT_CAR;
                gri.setStateCustomer(customerId, stateCustomers[customerId]);
            }
            notifyAll();
        } catch (MemException e) { System.exit(1); }
    }
    /**
     * Exit Lounge
     * @param customerId Id of the customer who will exit the lounge
     * */
    public synchronized void exitLounge(int customerId) {
        stateCustomers[customerId] = ATTENDED_WO_SUBCAR;
        gri.setStateCustomer(customerId, stateCustomers[customerId]);
    }

    /**
     *  Checks if Customer car keys are empty
     *  @return boolean (true/false) Available customers cars/No customers cars.
     * */
    private boolean isCustomerCarKeysEmpty() { return customerCarKeys.length == 0; }

    /**
     * Customer gives Manager his/hers car key.
     * @param key Customer's car key.
     * @param customerId current Customer giving their car key
     */
    public synchronized void giveManagerCarKey(int customerId, int key)
    {   customerCarKeys[customerId] = key;
        memKeysCustomers[key] = customerId;
        notifyAll();
    }

    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *  @param customerId ID of the customer.
     *  @return the Customer's car key.
     * */
    public synchronized int payForTheService(int customerId)
    {   int key = customerCarKeys[customerId];
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
    {   if(isCustomerCarKeysEmpty()) { return -1; }
        try
        {   stateMechanics[mechanicId] = FIXING_THE_CAR;
            gri.setStateMechanic(mechanicId, stateMechanics[mechanicId]);
            int tmpKey = carKeysToRepairQueue.read();
            return tmpKey;
        }
        catch (Exception e){System.exit(1); }
        System.exit(1);
        return -1;
    }

    /**
     *      Mechanic asks for a type of car parts for the repair
     *      @param idType       the id of the part to refill stock
     *      @param number       the number of stock needed
     *      @param mechanicId   the id of the mechanic
     * */
    public synchronized void requestPart(int idType, int number, int mechanicId)
    {
        gri.setFlagMissingPart(idType, "T");
        gri.setNumCarWaitingPart(idType, 1);
        carPartsToRefill[idType] += number;
    }

    /**
     *  register refill of stock
     *  @param idType the type of Car Part
     *  @param numberParts number of Car Parts being refilled
     * */
    public synchronized void registerStockRefill(int idType, int numberParts)
    {
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
            //return true;
        }

        gri.setStateManager(READ_PAPER);
        //return false;
    }

    /**
     * Manager checks if parts needs to be refilled
     * @return id of the part to refill. Returns -1 if no part is needed to refill
     * */
    public synchronized int checksPartsRequest()
    {   for(int i = 0; i<carPartsToRefill.length; i++)
        {   if(carPartsToRefill[i] != 0)
                return i;
        }
        return -1;
    }

    /**
     * Mechanic return key of the repaired car
     * @param idKey         the id of the key (= idCar)
     * @param mechanicId    the id of the mechanic.
     * */
    public synchronized void alertManagerRepairDone(int idKey, int mechanicId)
    {
        gri.setStateMechanic(mechanicId, ALERTING_MANAGER);
        if(!customerFixedCarKeys.isEmpty())
        {   if(customerFixedCarKeys.containsValue(idKey))
                System.exit(1);
        }
        try { customerFixedCarKeys.write(idKey); }
        catch (MemException e) { System.exit(1); }
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
    {   if(!isCustomerCarKeysEmpty())
        {   try
            {   return customerFixedCarKeys.read();
            } catch (MemException e)
            {   System.exit(1);
            }
        }
        return -1;
    }

    /**
     * Gets customer given the id of the key whom the customer belongs-
     * @param idKey id of the key.
     * @return the id of the customer
     * */
    public synchronized int getCustomerFromKey(int idKey)
    {   if(memKeysCustomers[idKey] == -1)
            System.exit(1);
        int customerId = memKeysCustomers[idKey];
        memKeysCustomers[idKey] = -1;
        return customerId;
    }
    /**
     * Make key ready to give back to customer.
     * @param idCustomer id of the customer.
     * @param idKey id of the key.
     * */
    public synchronized void readyToDeliverKey(int idCustomer, int idKey) {   customerCarKeys[idCustomer] = idKey; }

    /**
     *  Get the requested number of a part
     *  @param partId ID of the part Car.
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

    /**
     *  Mechanic declares that he/she is going home. Function is used for the locals/server termination.
     * */
    public synchronized void finish()
    {   if(--activeMechanics==0)
        {   this.outsideWorld.finish();
            this.park.finish();
            this.repairArea.finish();
            this.supplierSite.finish();
            this.gri.finish();
            this.finish = true;
        }
    }
}
