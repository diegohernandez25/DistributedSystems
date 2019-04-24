package GeneralRep;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;
import Interfaces.GriLounge;
import Communication.Com;

/**
 * Dedicated class to communicate with RepairArea using sockets.
 * */
public class GeneralRepInformation implements GriLounge {
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
     *  Set the state of the Manager
     *  @param state the current state of the Manager
     * */
    public void setStateManager(int state)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_STATE_MANAGER, state);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Set the state of a Customer
     *  @param customer the Customer that is going to change state
     *  @param state the current state of specified Customer
     * */
    public void setStateCustomer(int customer, int state)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_STATE_CUSTOMER, customer, state);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);

    }

    /**
     *  Set the state of a Mechanic
     *  @param mechanic the Mechanic that is going to change state
     *  @param state the current state of specified Mechanic
     * */
    public void setStateMechanic(int mechanic, int state)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_STATE_MECHANIC, mechanic, state);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Set if a Customer needs a replacement car. Default value is "F", this sets it to "T"
     *  @param customer the Customer who needs a replacement car
     * */
    public void setCustomerNeedsReplacement(int customer)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.SET_CUSTOMER_NEEDS_REPLACEMENT, customer);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Adds a Customer to the queue
     * */
    public void addCustomersQueue()
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.ADD_CUSTOMERS_QUEUE);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Removes a Customer from the queue
     * */
    public void removeCustomersQueue()
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.REMOVE_CUSTOMERS_QUEUE);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Adds a Customer to the replacement car queue
     * */
    public void addCustomersReplacementQueue()
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.ADD_CUSTOMERS_REPLACEMENT_QUEUE);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Removes a Customer from the replacement car queue
     * */
    public void removeCustomersReplacementQueue()
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.REMOVE_CUSTOMERS_REPLACEMENT_QUEUE);
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
     * Terminates GeneralRepInformation server
     * */
    public void finish()
    {   Com.finish(this.server, this.port);
    }
}
