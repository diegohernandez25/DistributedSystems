package Communication;

import java.io.Serializable;

public class Message implements Serializable {
    /**
     * Serial version of the class.
     */
    private static final long serialVersionUID = 220419L;

    /**
     *  Type of the message.
     * */
    private MessageType type;

    /**
     * Customer Id
     * */
    private int customerId;

    /**
     * Payment flag
     * */
    private boolean payment;

    /**
     * Customer key
     * */
    private int customerKey;

    /**
     *  Replacement car key.
     * */
    private int replacementCarKey;

    /**
     *  Mechanic Id.
     * */
    private int mechanicId;

    /**
     * Car Part Type Id
     * */
    private int carPart;

    /**
     * Requested number of part;
     * */
    private int numPart;

    /**
     *  Done flag
     * */
    private boolean done;

    /**
     * Available fixed cars flag.
     * */
    private boolean availableFixedCars;

    /**
     * Car Id
     * */
    private int carId;

    /**
     * Flag customers at outside world
     * */
    private boolean custAtOW;

    /**
     * Flag car ready for repair
     */
    private boolean readyForRepair;

    /**
     * Maximum number of storage for car part
     */
    private int maxPartNumber;

    /**
     * Next task to be done
     */
    private int nextTask;

    /**
     * Number of parts that were restocked
     */
    private int numRestocked;

    /**
     * Current state
     */
    private int state;
    /**
     * Type of vehicle.
     * Values can be:
     *   own car - customer ID.
     *   replacement car - R# (where # is the number of the replacement car).
     *   none - '-'.
     */
    private String vehicle;
    /**
     * Number to increment/decrement.
     * Values can be:
     *    1 - increment.
     *   -1 - decrement.
     */
    private int number;
    /**
     * Flag signaling if Manager has been adviced for missing part.
     * Values can be:
     *   T - true.
     *   F - false.
     */
    private String flagMissingPart;

    /**
     * Message Constructor.
     * */
    public Message()
    {   customerId              = -1;
        payment                 = false;
        customerKey             = -1;
        replacementCarKey       = -1;
        mechanicId              = -1;
        carPart                 = -1;
        numPart                 = 0;
        done                    = false;
        availableFixedCars      = false;
        carId                   = -1;
        custAtOW                = false;
        readyForRepair          = false;
        maxPartNumber           = -1;
        nextTask                = -1;
        numRestocked            = -1;
        state                   = -1;
        vehicle                 = "-";
        number                  = 0;
        flagMissingPart         = "F";
    }

    /**
     * Message constructor
     * @param msgType   - type of the message.
     * */
    public Message(MessageType msgType)
    {   this();
        this.type = msgType;
    }


    /**
     * Message Constructor
     * @param msgType   - type of the message
     * @param var1      - first integer variable
     * */
    public Message(MessageType msgType, int var1)
    {   this(msgType);
        switch (msgType)
        {   case ATTEND_CUSTOMER_RES:
            case PAY_FOR_THE_SERVICE_RES:
            case GET_CAR_TO_REPAIR_KEY_RES:
            case GET_CUSTOMER_FROM_KEY:
            case GET_FIXED_CAR_KEY_RES:
                this.customerKey = var1;
                break;

            case GET_REPLACEMENT_CAR_KEY_RES:
                this.replacementCarKey = var1;
                break;

            case GET_CUSTOMER_FROM_KEY_RES:
            case GET_REPLACEMENT_CAR_KEY:
            case EXIT_LOUNGE:
            case PAY_FOR_THE_SERVICE:
            case WAIT_FOR_REPAIR:
            case ALERT_CUSTOMER:
            case SET_CUSTOMER_NEEDS_REPLACEMENT:
            case SET_CUSTOMER_CAR_REPAIRED:
                this.customerId = var1;
                break;

            case CHECKS_PARTS_REQUEST_RES:
            case CHECK_CAR_RES:
            case REQUEST_NUMBER_PART:
            case GET_MAX_PART_STOCK:
            case REMOVE_NUM_PART_AVAILABLE:
                this.carPart = var1;
                break;

            case REQUEST_NUMBER_PART_RES:
                this.numPart = var1;
                break;

            case GET_CAR:
            case PARK_CAR:
            case REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE_RES:
            case POST_JOB:
            case GET_CAR_RES:
                this.carId = var1;
                break;

            case GET_CAR_TO_REPAIR_KEY:
            case REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE:
            case FIND_NEXT_TASK:
                this.mechanicId = var1;
                break;

            case GET_MAX_PART_STOCK_RES:
                this.maxPartNumber = var1;
                break;

            case FIND_NEXT_TASK_RES:
                this.nextTask = var1;
                break;

            case RESTOCK_PART_RES:
                this.numRestocked = var1;
                break;

            case SET_STATE_MANAGER:
                this.state = var1;
                break;

            case SET_NUM_REPLACEMENT_PARKED:
            case SET_NUM_CARS_PARKED:
                this.number = var1;
                break;

            default:
                System.out.println("message type not expected.");
                System.exit(1);
                break;
        }
    }

    /**
     * Message constructor.
     * @param msgType   - type of the message.
     * @param var1      - first boolean variable.
     * */
    public Message(MessageType msgType, boolean var1)
    {   this(msgType);
        switch (msgType)
        {   case ALL_DONE_RES:
            this.done   = var1;
            break;

            case ARE_CARS_FIXED_RES:
                this.availableFixedCars = var1;
                break;

            case IS_CUSTOMER_IN_OW_RES:
                this.custAtOW = var1;
                break;

            case REPAIR_CAR_RES:
                this.readyForRepair = var1;
                break;

            default:
                System.out.println("message type not expected.");
                System.exit(1);
                break;
        }
    }

    /**
     * Message constructor
     * @param var1      - first integer variable
     * @param var2      - second integer variable.
     * */
    public Message(MessageType msgType, int var1, int var2)
    {   this(msgType);
        switch (msgType)
        {   case REGISTER_STOCK_REFILL:
            case REFILL_CAR_PART_STOCK:
            case RESTOCK_PART:
                this.carPart        = var1;
                this.numPart        = var2;
                break;

            case READY_TO_DELIVER_KEY:
            case GIVE_MANAGER_CAR_KEY:
                this.customerId     = var1;
                this.customerKey    = var2;
                break;

            case RETURN_REPLACEMENT_CAR_KEY:
                this.replacementCarKey  = var1;
                this.customerId         = var2;
                break;

            case ALERT_MANAGER_REPAIR_DONE:
                this.customerKey = var1;
                this.mechanicId  = var2;
                break;

            case CHECK_CAR:
            case CONCLUDE_CAR_REPAIR:
                this.carId      = var1;
                this.mechanicId = var2;
                break;

            case SET_STATE_CUSTOMER:
                this.customerId = var1;
                this.state      = var2;
                break;
            case SET_STATE_MECHANIC:
                this.mechanicId = var1;
                this.state      = var2;
                break;

            case SET_NUM_CAR_WAITING_PART:
            case SET_NUM_BOUGHT_PART:
            case ADD_NUM_PART_AVAILABLE:
                this.carPart = var1;
                this.number  = var2;
                break;

            default:
                System.out.println("message type not expected.");
                System.exit(1);
                break;
        }
    }

    /**
     * Message Constructor
     * @param msgType   - type of the message
     * @param var1      - first integer variable
     * @param var2      - second boolean variable.
     * */
    public Message(MessageType msgType, int var1, boolean var2)
    {   this(msgType);
        switch (msgType)
        {   case ENTER_CUSTOMER_QUEUE:
            this.customerId = var1;
            this.payment    = var2;
            break;

            default:
                System.out.println("message type not expected.");
                System.exit(1);
                break;
        }

    }

    /**
     * Message constructor
     * @param var1      - first integer variable
     * @param var2      - second integer variable.
     * @param var3      - third integer variable.
     * */
    public Message(MessageType msgType, int var1, int var2, int var3)
    {
        this(msgType);
        switch (msgType)
        {
            case REQUEST_PART:
                this.carPart    = var1;
                this.numPart    = var2;
                this.mechanicId = var3;
                break;

            case REPAIR_CAR:
                this.carId      = var1;
                this.carPart    = var2;
                this.mechanicId = var3;
                break;

            default:
                System.out.println("message type not expected.");
                System.exit(1);
                break;
        }
    }

    /**
     * Message constructor
     * @param var1      - first integer variable
     * @param var2      - second string flag.
     * */
    public Message(MessageType msgType, int var1, String var2)
    {
        this(msgType);
        switch (msgType)
        {
            case SET_FLAG_MISSING_PART:
                this.carPart         = var1;
                this.flagMissingPart = var2;
                break;
            case SET_CUSTOMER_VEHICLE:
                this.customerId = var1;
                this.vehicle    = var2;
            default:
                System.out.println("message type not expected.");
                System.exit(1);
                break;
        }
    }

    /**
     * Get message type
     * @return message type
     * */
    public MessageType getType() { return type; }

    /**
     * Get customer Id.
     * @return customer Id.
     * */
    public int getCustomerId() { return customerId; }

    /**
     * Get payment flag.
     * @return payment flag.
     * */
    public boolean isPayment() { return payment; }

    /**
     * Get customer key.
     * @return customer key.
     * */
    public int getCustomerKey() { return customerKey; }

    /**
     * Get car part type
     * @return car part type.
     * */
    public int getCarPart() { return carPart; }

    /**
     * Get requested number of car part.
     * @return requested number of car part.
     * */
    public int getNumPart() { return numPart; }

    /**
     * Get available replacement car key
     * @return available replacement car key
     * */
    public int getReplacementCarKey() { return replacementCarKey; }

    /**
     * Get done flag
     * @return done flag.
     * */
    public boolean isDone(){ return done;}

    /**
     * Get available fixed cars flag.
     * @return available fixed cars flag.
     * */
    public boolean isAvailableFixedCars() { return availableFixedCars; }

    /**
     *  Get mechanic id.
     * @return mechanic id
     * */
    public int getMechanicId(){ return  mechanicId;}

    /**
     * Get car Id
     * @return car id
     * */
    public int getCarId(){ return carId;}

    /**
     * Get custAtOW flag
     * @return custAtOW flag
     * */
    public boolean isCustAtOW() { return custAtOW; }

    /**
     * Get readyForRepair flag
     * @return readyForRepair flag
     * */
    public boolean isReadyForRepair() { return readyForRepair; }

    /**
     * Get maximum number of storage for car part
     * @return maximum number of storage for part
     */
    public int getMaxPartNumber() { return maxPartNumber; }

    /**
     * Get next task to be done
     * @return next task
     */
    public int getNextTask() { return nextTask; }

    /**
     * Get number of restocked parts
     * @return number of parts that were restocked
     */
    public int getNumRestocked() { return numRestocked; }

    /**
     * Get current state
     * @return current state
     */
    public int getState() { return state; }
    /**
     * Get type of vehicle
     * @return type of vehicle
     */
    public String getVehicle() { return vehicle; }
    /**
     * Get number
     * @return number
     */
    public int getNumber() { return number; }
    /**
     * Get flag signaling Manager adviced from missing part
     * @return flag
     */
    public String getFlagMissingPart() { return flagMissingPart; }
}
