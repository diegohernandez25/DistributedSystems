package SharedRegions;

import Communication.Message;
import Communication.MessageType;
import Communication.ServerCom;
import Interfaces.SharedRegionInterface;

/**
 * General Repository Information Proxy. Implements Shared Region Interface.
 * */
public class GeneralRepInformationProxy implements SharedRegionInterface {

    /**
     * Object class OutsideWorld
     * */
    private final GeneralRepInformation generalRepInformation;

    /**
     * General Repository Information Constructor
     * @param generalRepInformation - GeneralRepInformation object.
     * */
    public GeneralRepInformationProxy(GeneralRepInformation generalRepInformation)
    {   this.generalRepInformation = generalRepInformation;
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
        {
            case SET_STATE_MANAGER:
                this.generalRepInformation.setStateManager(msg.getState());
                response = new Message(MessageType.OK);
                break;

            case SET_STATE_CUSTOMER:
                this.generalRepInformation.setStateCustomer(msg.getCustomerId(), msg.getState());
                response = new Message(MessageType.OK);
                break;

            case SET_STATE_MECHANIC:
                this.generalRepInformation.setStateMechanic(msg.getMechanicId(), msg.getState());
                response = new Message(MessageType.OK);
                break;

            case SET_CUSTOMER_VEHICLE:
                this.generalRepInformation.setCustomerVehicle(msg.getCustomerId(), msg.getVehicle());
                response = new Message(MessageType.OK);
                break;

            case SET_CUSTOMER_NEEDS_REPLACEMENT:
                this.generalRepInformation.setCustomerNeedsReplacement(msg.getCustomerId());
                response = new Message(MessageType.OK);
                break;

            case SET_CUSTOMER_CAR_REPAIRED:
                this.generalRepInformation.setCustomerCarRepaired(msg.getCustomerId());
                response = new Message(MessageType.OK);
                break;

            case ADD_CUSTOMERS_QUEUE:
                this.generalRepInformation.addCustomersQueue();
                response = new Message(MessageType.OK);
                break;

            case REMOVE_CUSTOMERS_QUEUE:
                this.generalRepInformation.removeCustomersQueue();
                response = new Message(MessageType.OK);
                break;

            case ADD_CUSTOMERS_REPLACEMENT_QUEUE:
                this.generalRepInformation.addCustomersReplacementQueue();
                response = new Message(MessageType.OK);
                break;

            case REMOVE_CUSTOMERS_REPLACEMENT_QUEUE:
                this.generalRepInformation.removeCustomersReplacementQueue();
                response = new Message(MessageType.OK);
                break;

            case SET_NUM_CARS_REPAIRED:
                this.generalRepInformation.setNumCarsRepaired();
                response = new Message(MessageType.OK);
                break;

            case SET_NUM_CARS_PARKED:
                this.generalRepInformation.setNumCarsParked(msg.getNumber());
                response = new Message(MessageType.OK);
                break;

            case SET_NUM_REPLACEMENT_PARKED:
                this.generalRepInformation.setNumReplacementParked(msg.getNumber());
                response = new Message(MessageType.OK);
                break;

            case SET_NUM_POST_JOBS:
                this.generalRepInformation.setNumPostJobs();
                response = new Message(MessageType.OK);
                break;

            case ADD_NUM_PART_AVAILABLE:
                this.generalRepInformation.addNumPartAvailable(msg.getCarPart(), msg.getNumPart());
                response = new Message(MessageType.OK);
                break;

            case REMOVE_NUM_PART_AVAILABLE:
                this.generalRepInformation.removeNumPartAvailable(msg.getCarPart());
                response = new Message(MessageType.OK);
                break;

            case SET_NUM_CAR_WAITING_PART:
                this.generalRepInformation.setNumCarWaitingPart(msg.getCarPart(), msg.getNumber());
                response = new Message(MessageType.OK);
                break;

            case SET_FLAG_MISSING_PART:
                this.generalRepInformation.setFlagMissingPart(msg.getCarPart(), msg.getFlagMissingPart());
                response = new Message(MessageType.OK);
                break;

            case SET_NUM_BOUGHT_PART:
                this.generalRepInformation.setNumBoughtPart(msg.getCarPart(), msg.getNumber());
                response = new Message(MessageType.OK);
                break;

            case FINISH:
                this.generalRepInformation.finish();
                response = new Message(MessageType.OK);
                break;

            default:
                System.out.println("message not expected.");
                System.exit(1);
                break;
        }
        return response;
    }
}
