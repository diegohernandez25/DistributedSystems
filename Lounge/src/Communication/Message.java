package Communication;

import Resources.MemFIFO;

import java.io.Serializable;

public class Message implements Serializable {

    /**
     *  Type of the message.
     * */
    private MessageType type;

    /**
     *  Customer Id.
     * */
    private int customerId;
    /**
     *  Payment flag
     * */
    private boolean payment;

    /**
     *  Customer key.
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
     *  Car part Id.
     * */
    private int carPart;

    /**
     *  Number of car parts
     * */
    private int numPart;

    /**
     *  Done flag
     * */
    private boolean done;

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
     * Message constructor.
     * @param msgType   - type of the message.
     * @param var1      - first integer variable.
     * */
    public Message(MessageType msgType, int var1)
    {   this(msgType);
        switch (msgType)
        {   case ATTEND_CUSTOMER:
            case PAY_FOR_THE_SERVICE:
            case GET_CAR_TO_REPAIR_KEY:
                this.customerKey = var1;
                break;
            case GET_REPLACEMENT_CAR_KEY:
                this.replacementCarKey = var1;
                break;

            case CHECKS_PARTS_REQUEST:
                this.carPart = var1;
                break;

            case REQUEST_NUMBER_PART:
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
        {   case ALL_DONE:
                this.done   = var1;
            default:
                System.out.println("message type not expected.");
                System.exit(1);
                break;
        }
    }

    /**
     * Get message type.
     * @return message type (object of class MessageType).
     * */
    public MessageType getType(){return this.type;}

    /**
     * Get customer Id.
     * @return customer Id
     * */
    public int getCustomerId(){ return this.customerId;}

    /**
     * Get payment flag.
     * @return payment flag
     * */
    public boolean getPayment(){ return payment;}

    /**
     * Get customer key.
     * @return customer key.
     * */
    public int getCustomerKey(){ return customerKey;}

    /**
     * Get replacement car key.
     * @return replacement car key.
     * */
    public int getReplacementCarKey(){ return replacementCarKey;}

    /**
     *  Get mechanic id.
     * @return mechanic id
     * */
    public int getMechanicId(){ return  mechanicId;}

    /**
     *  Get car part.
     * @return car part.
     * */
    public int getCarPart(){ return carPart;}

    /**
     *  Get number of parts.
     * @return requested number of parts.
     * */
    public int getNumPart(){ return numPart;}

    /**
     *  Get done flag
     * @return done flag.
     * */
    public boolean getDone(){ return done;}
}
