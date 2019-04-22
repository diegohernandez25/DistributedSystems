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
     *      Checks the needed parts for the car to repair
     *
     *      @param idCar    - id of the car to check
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return the id of the part needed for repair
     * */
    public int checkCar(int idCar, int mechanicId)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.CHECK_CAR, idCar, mechanicId);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.CHECK_CAR_RES);
      return response.getCarPart();
    }

    /**
     *      Checks availability of car parts and if available repairs the car
     *
     *      @param carId    - The Id of car to repair.
     *      @param partId   - The id of the car part needed for the repair.
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return true ready for repair. False otherwise
     * */
    public boolean repairCar(int carId, int partId, int mechanicId)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.REPAIR_CAR, carId, partId, mechanicId);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.REPAIR_CAR_RES);
      return response.isReadyForRepair();
    }

    /**
     *      Checks which of the waiting cars are ready for repair
     *
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return id of the car ready for repair.
     * */
    public int repairWaitingCarWithPartsAvailable(int mechanicId) {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE, mechanicId);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE_RES);
      return response.getCarId();
    }

    /**
     *      Conclude repair of the car.
     *
     *      @param idCar    - Id of the car.
     *      @param mechanicId - id of the mechanic doing the task
     * */
    public void concludeCarRepair(int idCar, int mechanicId) {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.CONCLUDE_CAR_REPAIR, idCar, mechanicId);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.OK);
    }

    /**
     * Get maximum number of a specific part that can be stored on the repairArea
     * @param partId    - Id of the car part.
     * @return the maximum number of storage for the part
     * */
    public int getMaxPartStock(int partId)
    {
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.GET_MAX_PART_STOCK, partId);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.GET_MAX_PART_STOCK_RES);
      return response.getMaxPartNumber();
    }

    /**
     *  Mechanic checks what has to do next
     *  @param mechanicId - id of the mechanic doing the task
     *  @return the task that has to be done
     * */
    public int findNextTask(int mechanicId)
    {   System.out.println("Finding next task");
      ClientCom clientCom = Com.openChannel(server,port);
      Message request = new Message(MessageType.FIND_NEXT_TASK, mechanicId);
      clientCom.writeObject(request);
      Message response = Com.expectMessageType(clientCom, MessageType.FIND_NEXT_TASK_RES);
      return response.getNextTask();
    }
}
