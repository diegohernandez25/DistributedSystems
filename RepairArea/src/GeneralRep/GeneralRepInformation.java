package GeneralRep;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;

/**
 * Dedicated class to communicate with RepairArea using sockets.
 * */
public class GeneralRepInformation {
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
    public GeneralRepInformation(String server, int port)
    {
        this.server = server;
        this.port   = port;
    }

    /**
     *  Set if a Customer's car is repaired. Default value is "F", this sets it to "T"
     *  @param customer the Customer whose car has been repaired
     * */
    public void setCustomerCarRepaired(int customer)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_CUSTOMER_CAR_REPAIRED, customer);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Increments the number of cars that have been repaired
     * */
    public void setNumCarsRepaired()
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_NUM_CARS_REPAIRED);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Increments the number of requests the Manager has made to the Repair Area
     * */
    public void setNumPostJobs()
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_NUM_POST_JOBS);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Adds the number of parts available in stock (restocked part)
     *  @param part the part which needs to be set
     *  @param num the number of the specified part available
     * */
    public void addNumPartAvailable(int part, int num)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.ADD_NUM_PART_AVAILABLE, part, num);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Removes one of the parts available in stock (used part)
     *  @param part the part which was used
     * */
    public void removeNumPartAvailable(int part)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.REMOVE_NUM_PART_AVAILABLE, part);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Sets the number of cars waiting for a specific part
     *  @param part the part the cars are waiting for
     *  @param num the number of cars waiting for the part. +1 if new car waiting for part, -1 if car no longer needs part
     * */
    public void setNumCarWaitingPart(int part, int num)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_NUM_CAR_WAITING_PART, part, num);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }
}
