package Locals;

import Communication.ClientCom;
import Communication.Com;
import Communication.Message;
import Communication.MessageType;
import Interfaces.CustomerOW;

/**
 * Dedicated class to communicate with OutsideWorld using sockets.
 * */
public class OutsideWorld implements CustomerOW {
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
     * @param server    server.
     * @param port      server port.
     * */
    public OutsideWorld(String server, int port)
    {   this.server = server;
        this.port   = port;
    }

    /**
     *  Customer waits until the manager alerts him/her about the end of the service.
     *  @param customerId ID of the waiting customer.
     * */
    public synchronized void waitForRepair(Integer customerId)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request     = new Message(MessageType.WAIT_FOR_REPAIR, customerId);
        clientCom.writeObject(request);
        Message response    = Com.expectMessageType(clientCom, MessageType.OK);
    }
}
