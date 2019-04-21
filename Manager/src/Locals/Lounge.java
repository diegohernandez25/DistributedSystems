package Locals;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;

/**
 * Dedicated class to communicate with Lounge using sockets.
 * */
public class Lounge {
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
    public Lounge(String server, int port)
    {   this.server = server;
        this.port   = port;
    }


    /**
     *  Operation to attend customer. Can be for receive payment or to initiate the repair of a car.
     *  Manager invokes this method.
     *  @return success of the operation so the Mechanic can move on or not to the next operation.
     * */
    public int attendCustomer()
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.ATTEND_CUSTOMER);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.ATTEND_CUSTOMER_RES);
        return response.getCustomerKey();
    }

    /**
     *  register refill of stock
     *  @param idType - the type of Car Part
     *  @param numberParts number of Car Parts being refilled
     * */
    public void registerStockRefill(int idType, int numberParts)
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.REGISTER_STOCK_REFILL, idType, numberParts);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.OK);
    }

    /**
     * Manager checks if parts needs to be refilled
     * @return id of the part to refill. Returns -1 if no part is needed to refill
     * */
    public int checksPartsRequest()
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.CHECKS_PARTS_REQUEST);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.CHECKS_PARTS_REQUEST_RES);
        return response.getCarPart();
    }

    /**
     * Checks if there are cars repaired
     * @return true - customer fixed car keys empty
     * */
    public boolean isCustomerFixedCarKeysEmpty()
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.ARE_CARS_FIXED);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.ARE_CARS_FIXED_RES);
        return response.isAvailableFixedCars();
    }

    /**
     * Gets key of a fixed car.
     * @return id of the key
     * */
    public int getFixedCarKey()
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.GET_FIXED_CAR_KEY);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.GET_FIXED_CAR_KEY_RES);
        return response.getCustomerKey();
    }

    /**
     * Gets customer given the id of the key whom the customer belongs-
     * @param idKey - id of the key.
     * @return the id of the customer
     * */
    public int getCustomerFromKey(int idKey)
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.GET_CUSTOMER_FROM_KEY, idKey);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.GET_CUSTOMER_FROM_KEY_RES);
        return  response.getCustomerId();
    }

    /**
     * Make key ready to give back to customer.
     * @param idCustomer - id of the customer.
     * @param idKey - id of the key.
     * */
    public void readyToDeliverKey(int idCustomer, int idKey)
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.READY_TO_DELIVER_KEY,idCustomer, idKey);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Get the requested number of a part
     *  @param partId - ID of the part Car.
     *  @return number of parts requested.
     * */
    public int requestedNumberPart(int partId)
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.REQUEST_NUMBER_PART, partId);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.REQUEST_NUMBER_PART_RES);
        return response.getNumPart();
    }

    /**
     * Checks if all customer have been attended.
     * @return true - all services done to all customers for the dat. False - otherwise
     * */
    public boolean allDone()
    {   ClientCom clientCom = openChannel();
        Message request = new Message(MessageType.ALL_DONE);
        clientCom.writeObject(request);
        Message response = expectMessageType(clientCom, MessageType.ALL_DONE_RES);
        return response.isDone();
    }

    /**
     *  Open socket communication.
     *  @return client communication object.
     * */
    private ClientCom openChannel()
    {   ClientCom clientCom = new ClientCom(server,port);
        while (!clientCom.open())
        {   try
        {   Thread.sleep((long) 10);
        } catch (InterruptedException ignored){}

        }
        return clientCom;
    }

    /**
     *  Closes socket communication.
     *  @param clientCom - client communication object.
     * */
    private void closeChannel(ClientCom clientCom){ clientCom.close();}

    /**
     * Checks if message is according to the expected message. If it is, returns response message.
     * Closes connection.
     * @param clientCom - client socket communication object.
     * @param msgType   - expected message type.
     * @return response message.
     * */
    private Message expectMessageType(ClientCom clientCom, MessageType msgType)
    {   Message response = (Message) clientCom.readObject();
        if(response.getType() != msgType)
        {   System.out.println("Expecting message of type "+ msgType.toString());
            System.exit(1);
        }
        clientCom.close();
        return response;
    }
}
