package Locals;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;

/**
 * Dedicated class to communicate with Lounge using sockets.
 * */
public class Lounge {
    /**
     * Server Name
     * */
    private final String server;

    /**
     * Server port.
     * */
    private final int port;

    /**
     * Lounge constructor
     * @param server    - server.
     * @param port      - server port.
     * */
    public Lounge(String server, int port)
    {   this.server = server;
        this.port   = port;
    }

    /**
     *  Customer enters queue to be attended by the Manager.
     *  @param customerId - Id of the customer to be attended
     *  @param payment - type of attendance. (true/false) Pay for repair/Request repair.
     * */
    public void enterCustomerQueue(int customerId, boolean payment)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.ENTER_CUSTOMER_QUEUE, customerId, payment);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     * Get replacement car key.
     * @param customerId - ID of the client who needs the replacement car.
     * @return the key of the replacement car.
     * */
    public int getReplacementCarKey(int customerId)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.GET_REPLACEMENT_CAR_KEY, customerId);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.GET_REPLACEMENT_CAR_KEY_RES);
        return response.getReplacementCarKey();
    }

    /**
     *  Return Replacement car key.
     *  Customer with the need of a replacement car invokes this method.
     *  @param key - Key of the replacement car key.
     *  @param customerId ID of the current Customer returning the replacement car key.
     *  @return status of the operation.
     * */
    public void returnReplacementCarKey(int key, int customerId)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.RETURN_REPLACEMENT_CAR_KEY,key, customerId);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     * Exit Lounge
     * @param customerId - Id of the customer who will exit the lounge
     * */
    public void exitLounge(int customerId)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.EXIT_LOUNGE, customerId);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     * Customer gives Manager his/hers car key.
     * @param key - Customer's car key.
     * @param customerId current Customer giving their car key
     */
    public void giveManagerCarKey(int customerId, int key)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.GIVE_MANAGER_CAR_KEY, customerId, key);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *  @param customerId - ID of the customer.
     *  @return the Customer's car key.
     * */
    public int payForTheService(int customerId)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.PAY_FOR_THE_SERVICE, customerId);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom,MessageType.PAY_FOR_THE_SERVICE_RES);
        return response.getCustomerKey();
    }



}
