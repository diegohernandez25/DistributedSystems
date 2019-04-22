package Interfaces;

import Communication.Message;
import Communication.ServerCom;

/**
 *  Interface used for the processing of received messages.
 * */
public interface SharedRegionInterface {
    /**
     * Method from the SharedRegionInterface. It is used to receive, process incoming messages and respond to them.
     * @param msg   - The received message.
     * @param sc    - object ServerCom to receive and send message to the source of msg.
     * @return The reply of the correspondent message.
     * */
    Message processMessage(Message msg, ServerCom sc);
}
