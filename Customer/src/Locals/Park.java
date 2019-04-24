package Locals;

import Communication.ClientCom;
import Communication.Com;
import Communication.Message;
import Communication.MessageType;
import Interfaces.CustomerPark;

/**
 * Dedicated class to communicate with Park using sockets.
 * */
public class Park implements CustomerPark {
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
    public Park(String server, int port)
    {   this.server = server;
        this.port   = port;
    }

    /**
     *  Park Car.
     *  @param carId ID of the car.
     *  @param id customer id.
     *  @param customerPark flag if it was a customer who parked the car
     * */
    public void parkCar(Integer carId, int id, boolean customerPark)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.PARK_CAR, carId, id, customerPark);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     *  Gets Car.
     *  @param carId id of the car.
     *  @param id customer id.
     *  @param customerGet flag if it was a customer who got the car
     *  @return id of the car
     * */
    public Integer getCar(Integer carId, int id, boolean customerGet)
    {   ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.GET_CAR, carId, id, customerGet);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.GET_CAR_RES);
        return response.getCarId();
    }

}
