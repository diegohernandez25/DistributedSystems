package Locals;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;
import Interfaces.MechanicPark;

/**
 * Dedicated class to communicate with Park using sockets.
 * */
public class Park implements MechanicPark {
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
    public Park(String server, int port)
    {   this.server = server;
        this.port   = port;
    }

    /**
     *  Park Car.
     *  @param carId ID of the car.
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
