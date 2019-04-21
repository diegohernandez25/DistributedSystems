package SharedRegions;

import Communication.Message;
import Communication.MessageType;
import Communication.ServerCom;
import Interfaces.SharedRegionInterface;

public class ParkProxy implements SharedRegionInterface {

    /**
     * Object of class Park
     * */
    private final Park park;

    /**
     * ParkProxy Constructor
     * @param park  - park object
     * */
    public ParkProxy(Park park)
    {   this.park = park;
    }

    /**
     * Method from the SharedRegionInterface. It is used to receive, process incoming messages and respond to them.
     * @param msg   - The received message.
     * @param sc    - object ServerCom to receive and send message to the source of msg.
     * @return The reply of the correspondent message.
     * */
    @Override
    public Message processMessage(Message msg, ServerCom sc) {
        Message response = null;
        int resInt;
        switch (msg.getType())
        {   case PARK_CAR:
                this.park.parkCar(msg.getCarId());
                response = new Message(MessageType.OK);
                break;

            case GET_CAR:
                resInt = this.park.getCar(msg.getCarId());
                response = new Message(MessageType.GET_CAR, resInt);
                break;

            default:
                System.out.println("message not expected.");
                System.exit(1);
                break;
        }
        return response;
    }
}
