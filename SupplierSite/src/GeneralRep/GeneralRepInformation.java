package GeneralRep;

import Communication.ClientCom;
import Communication.Com;
import Communication.Message;
import Communication.MessageType;
import Interfaces.GriSS;

/**
 * Dedicated class to communicate with RepairArea using sockets.
 * */
public class GeneralRepInformation implements GriSS {
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
    public GeneralRepInformation(String server, int port)
    {
        this.server = server;
        this.port   = port;
    }

    /**
     *  Sets a flag signaling the Manager has been adviced that a specific part is missing
     *  @param part the part which needs to be set the flag
     *  @param flag the flag signaling if Manager has been adviced (T/F)
     * */
    public void setFlagMissingPart(int part, String flag)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_FLAG_MISSING_PART, part, flag);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Increments the number of bought parts of a specific part
     *  @param part the part that was bought
     *  @param num the number of bought parts
     * */
    public void setNumBoughtPart(int part, int num)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_NUM_BOUGHT_PART, part, num);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }
}
