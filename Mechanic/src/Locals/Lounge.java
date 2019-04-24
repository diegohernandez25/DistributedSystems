package Locals;

import Communication.ClientCom;
import Communication.Message;
import Communication.MessageType;
import Interfaces.MechanicLounge;

/**
 * Dedicated class to communicate with Lounge using sockets.
 * */
 public class Lounge implements MechanicLounge {
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
     {
        this.server = server;
        this.port   = port;
     }

     /**
      *  Mechanic gets keys of car to be repaired
      *  @param mechanicId ID of the Mechanic
      *  @return key of the car to repair
      *  */
     public int getCarToRepairKey(int mechanicId)
     {
        ClientCom clientCom = Com.openChannel(server,port);
        Message request = new Message(MessageType.GET_CAR_TO_REPAIR_KEY, mechanicId);
        clientCom.writeObject(request);
        Message response = Com.expectMessageType(clientCom, MessageType.GET_CAR_TO_REPAIR_KEY_RES);
        return response.getCustomerKey();
     }

     /**
      *      Mechanic asks for a type of car parts for the repair
      *      @param idType       - the id of the part to refill stock
      *      @param number       - the number of stock needed
      *      @param mechanicId   - the id of the mechanic
      * */
     public void requestPart(int idType, int number, int mechanicId)
     {
       ClientCom clientCom = Com.openChannel(server,port);
       Message request = new Message(MessageType.REQUEST_PART, idType, number, mechanicId);
       clientCom.writeObject(request);
       Message response = Com.expectMessageType(clientCom, MessageType.OK);
     }

     /**
      * Mechanic return key of the repaired car
      * @param idKey         - the id of the key (= idCar)
      * @param mechanicId    - the id of the mechanic.
      * */
     public void alertManagerRepairDone(int idKey, int mechanicId)
     {
       ClientCom clientCom = Com.openChannel(server,port);
       Message request = new Message(MessageType.ALERT_MANAGER_REPAIR_DONE, idKey, mechanicId);
       clientCom.writeObject(request);
       Message response = Com.expectMessageType(clientCom, MessageType.OK);
     }
 }
