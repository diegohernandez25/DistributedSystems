package Locals;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;

/**
 * Dedicated class to communicate with SupplierSite using sockets.
 * */
public class SupplierSite {
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
    public SupplierSite(String server, int port)
    {
        this.server = server;
        this.port   = port;
    }

    /**
     *      Restocks car part. Always gets 3 of the part.
     *
     *      @param idType - type of the part to replenish
     *      @param number - requested number
     *
     *      @return number of parts that were restocked of the specific type of part
     * */
    public int restockPart(int idType, int number)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.RESTOCK_PART, idType, number);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.RESTOCK_PART_RES);
      return response.getNumRestocked();
    }
}
