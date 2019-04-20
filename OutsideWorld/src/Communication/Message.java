package Communication;

public class Message {
    /**
     *  Type of the message.
     * */
    private MessageType type;

    /**
     * Id of the customer
     * */
    private int customerId;

    /**
     * Flag customers at outside world
     * */
    private boolean custAtOW;

    /**
     * Message Constructor.
     * */
    public Message()
    {   customerId  = -1;
        custAtOW    = false;
    }

    /**
     * Message Constructor.
     * @param type  - type of message.
     * */
    public Message(MessageType type)
    {   this();
        this.type = type;
    }

    /**
     * Message Constructor.
     * @param type  - type of message.
     * @param var1  - first boolean variable.
     * */
    public Message(MessageType type, boolean var1)
    {   this(type);
        this.custAtOW = var1;
    }

    /**
     * Get message type.
     * @return message type (object of class MessageType).
     * */
    public MessageType getType(){return this.type;}

    /**
     * get customer Id.
     * @return customer Id.
     * */
    public  int getCustomerId(){ return customerId;}
}
