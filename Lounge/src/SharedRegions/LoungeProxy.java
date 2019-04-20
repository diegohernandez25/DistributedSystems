package SharedRegions;

import Communication.Message;
import Communication.MessageType;
import Communication.ServerCom;
import Interfaces.SharedRegionInterface;

public class LoungeProxy implements SharedRegionInterface {
    /**
     * Object of class lounge.
     * */
    private final Lounge lounge;

    /**
     * Lounge constructor.
     * @param lounge - object lounge.
     * */
    public LoungeProxy(Lounge lounge)
    { this.lounge = lounge;
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
        boolean resBool;
        switch(msg.getType())
        {   case ENTER_CUSTOMER_QUEUE:
                this.lounge.enterCustomerQueue(msg.getCustomerId(),msg.getPayment());
                response = new Message(MessageType.OK);
                break;

            case ATTEND_CUSTOMER:
                resInt = this.lounge.attendCustomer();
                response = new Message(MessageType.ATTEND_CUSTOMER, resInt);
                break;

            case GET_REPLACEMENT_CAR_KEY:
                resInt     = this.lounge.getReplacementCarKey(msg.getCustomerId());
                response    = new Message(MessageType.GET_REPLACEMENT_CAR_KEY, resInt);
                break;

            case RETURN_REPLACEMENT_CAR_KEY:
                this.lounge.returnReplacementCarKey(msg.getReplacementCarKey(), msg.getCustomerId());
                response    = new Message(MessageType.OK);
                break;

            case EXIT_LOUNGE:
                this.lounge.exitLounge(msg.getCustomerId());
                response    = new Message(MessageType.OK);
                break;

            case IS_CUSTOMER_CAR_KEYS_EMPTY:
                //FIXME: avoid using it
                break;

            case GIVE_MANAGER_CAR_KEY:
                this.lounge.giveManagerCarKey(msg.getCustomerKey(),msg.getCustomerId());
                response    = new Message(MessageType.OK);
                break;

            case PAY_FOR_THE_SERVICE:
                resInt = this.lounge.payForTheService(msg.getCustomerId());
                response    = new Message(MessageType.PAY_FOR_THE_SERVICE, resInt);
                break;

            case GET_CAR_TO_REPAIR_KEY:
                resInt = this.lounge.getCarToRepairKey(msg.getMechanicId());
                response    = new Message(MessageType.GET_CAR_TO_REPAIR_KEY, resInt);
                break;

            case REQUEST_PART:
                this.lounge.requestPart(msg.getCarPart(),msg.getNumPart(), msg.getMechanicId());
                response    = new Message(MessageType.OK);
                break;

            case REGISTER_STOCK_REFILL:
                this.lounge.registerStockRefill(msg.getCarPart(), msg.getNumPart());
                response    = new Message(MessageType.OK);
                break;

            case CHECKS_PARTS_REQUEST:
                resInt         = this.lounge.checksPartsRequest();
                response    = new Message(MessageType.CHECKS_PARTS_REQUEST,resInt);
                break;

            case ALERT_MANAGER_REPAIR_DONE:
                this.lounge.alertManagerRepairDone(msg.getCustomerKey(),msg.getMechanicId());
                response    = new Message(MessageType.OK);
                break;

            case READY_TO_DELIVER_KEY:
                this.lounge.readyToDeliverKey(msg.getCustomerId(),msg.getCustomerKey());
                response    = new Message(MessageType.OK);
                break;

            case REQUEST_NUMBER_PART:
                resInt      = this.lounge.requestedNumberPart(msg.getCarPart());
                response    = new Message(MessageType.REQUEST_NUMBER_PART, resInt);
                break;

            case ALL_DONE:
                resBool     = this.lounge.allDone();
                response    = new Message(MessageType.ALL_DONE, resBool);
                break;
        }
        return response;
    }
}
