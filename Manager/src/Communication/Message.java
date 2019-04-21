package Communication;

import java.io.Serializable;

public class Message implements Serializable {
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
                this.customerId = var1;
                break;

            case CHECKS_PARTS_REQUEST_RES:
                this.carPart = var1;
                break;

            case REQUEST_NUMBER_PART_RES:
                this.numPart = var1;
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
                this.carPart        = var1;
                this.numPart        = var2;
                break;
            case READY_TO_DELIVER_KEY:
                this.customerId     = var1;
                this.customerKey    = var2;
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

}
