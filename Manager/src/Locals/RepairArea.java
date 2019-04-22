package Locals;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;

/**
 * Dedicated class to communicate with RepairArea using sockets.
 * */
public class RepairArea {
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
    public RepairArea(String server, int port)
    {
        this.server = server;
        this.port   = port;
    }

    /**
     *      Refill car Part stock
     *      @param idPart   - ID of car part.
     *      @param quantity - number of car parts to refill
     * */
    public  void  refillCarPartStock(int idPart, int quantity)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.REFILL_CAR_PART_STOCK, idPart, quantity);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     * Alerts that a new car needs to be checked.
     * @param carID the ID of the car that needs to be repaired
     * */
    public void postJob(int carID)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.POST_JOB, carID);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Sends the Mechanics home
     * */
    public void sendHome()
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SEND_HOME);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }
}
