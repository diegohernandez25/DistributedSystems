package Locals;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;
import Interfaces.ManagerOW;

/**
 * Dedicated class to communicate with Lounge using sockets.
 * */
public class OutsideWorld implements ManagerOW {
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
    public OutsideWorld(String server, int port)
    {   this.server = server;
        this.port   = port;
    }

    /**
     *  Managers alerts customer that car is fixed and it can be retrieved;
     *  @param customerId - ID of the customer to alert .
     * */
    public void alertCustomer(Integer customerId)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.ALERT_CUSTOMER, customerId);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Alert remaining customer whom hasn't been alerted (because they haven't arrived sooner at the outside world)
     * */
    public void alertRemainingCustomers()
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.ALERT_REMAINING_CUSTOMERS);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Checks if there are user's expected to arrive at the outside world.
     *  @return true/false if customer is already in the Outside World or not
     * */
    public boolean customersNotYetAtOutsideWorldisEmpty()
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.IS_CUSTOMER_IN_OW);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom,MessageType.IS_CUSTOMER_IN_OW_RES);
        return response.isCustAtOW();
    }



}
