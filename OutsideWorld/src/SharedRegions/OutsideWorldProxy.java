package SharedRegions;

import Communication.Message;
import Communication.MessageType;
import Communication.ServerCom;
import Interfaces.SharedRegionInterface;

public class OutsideWorldProxy implements SharedRegionInterface {

    /**
     * Object class OutsideWorld
     * */
    private final OutsideWorld outsideWorld;

    /**
     * Outside World Constructor
     * @param outsideWorld - OutsideWorld object.
     * */
    public OutsideWorldProxy(OutsideWorld outsideWorld)
    {   this.outsideWorld = outsideWorld;
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
        boolean resBool;
        switch (msg.getType())
        {   case WAIT_FOR_REPAIR:
                this.outsideWorld.waitForRepair(msg.getCustomerId());
                response = new Message(MessageType.OK);
                break;

            case ALERT_CUSTOMER:
                this.outsideWorld.alertCustomer(msg.getCustomerId());
                response = new Message(MessageType.OK);
                break;

            case ALERT_REMAINING_CUSTOMERS:
                this.outsideWorld.alertRemainingCustomers();
                response = new Message(MessageType.OK);
                break;

            case IS_CUSTOMER_IN_OW:
                resBool = this.outsideWorld.customersNotYetAtOutsideWorldisEmpty();
                response = new Message(MessageType.IS_CUSTOMER_IN_OW, resBool);
                break;

            default:
                System.out.println("message not expected.");
                System.exit(1);
                break;
        }
        return response;
    }
}
