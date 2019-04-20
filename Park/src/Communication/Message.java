package Communication;

public class Message {

    /**
     *  Type of the message.
     * */
    private MessageType type;

    /**
     * Car Id
     * */
    private int carId;
    /**
     * Message Constructor.
     * */
    public Message()
    {   carId = -1;
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
     * Message Constructor
     * @param var1  - first integer variable
     * */
    public Message(MessageType type, int var1)
    {   this(type);
        switch (type)
        {   case GET_CAR:
                this.carId = var1;
                break;
            default:
                System.out.println("Not expecting this message type.");
                System.exit(1);
        }
    }

    /**
     * Get message type.
     * @return message type (object of class MessageType).
     * */
    public MessageType getType(){return this.type;}

    /**
     * Get car Id
     * @return car id
     * */
    public int getCarId(){ return carId;}


}
