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
     *  Set the vehicle currently used by a Customer
     *  @param customer the Customer who is changing vehicles
     *  @param vehicle the vehicle that the specified Customer is changing to. Values can be: own car - customer ID; replacement car - R# (where # is the number of the replacement car); none - '-'
     * */
    public void setCustomerVehicle(int customer, String vehicle)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_CUSTOMER_VEHICLE, customer, vehicle);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Set the number of Customer cars parked
     *  @param num the number of cars parked
     * */
    public void setNumCarsParked(int num)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_NUM_CARS_PARKED, num);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Set the number of replacement cars parked
     *  @param num the number of replacement cars parked
     * */
    public void setNumReplacementParked(int num)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_NUM_REPLACEMENT_PARKED, num);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }
}
