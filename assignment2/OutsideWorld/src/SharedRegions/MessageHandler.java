package SharedRegions;

import Communication.Message;
import Communication.ServerCom;
import Interfaces.SharedRegionInterface;

/**
 * MessageHandler:    Thread that processes and replies to an unique incoming message. Each message will invoke a
 *                      thread of type of this class.
 * */
public class MessageHandler extends Thread{
    /**
     * Communication channel to the source of the message.
     * */
    private final ServerCom sc;

    /**
     * Shared region implementation
     * */
    private final SharedRegionInterface sharedRegion;

    /**
     * Message Handler constructor.
     * @param sc            - object of class type ServerComm
     * @param sharedRegion  - object of class with implementation SharedRegionInterface
     * */
    public MessageHandler(ServerCom sc, SharedRegionInterface sharedRegion)
    {   this.sc             = sc;
        this.sharedRegion   = sharedRegion;
    }

    /**
     * Thread's lifecycle.
     * */
    @Override
    public void run() {
        Message rcvMsg = (Message) sc.readObject();                             //Receive message.
        Message sndMsg = sharedRegion.processMessage(rcvMsg, sc);               //Creates response message.
        sc.writeObject(sndMsg);                                                 //Send message to source.
        sc.close();                                                             //Closes channel.
    }

}