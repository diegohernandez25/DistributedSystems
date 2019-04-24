package SharedRegions;

import Communication.Message;
import Communication.MessageType;
import Communication.ServerCom;
import Interfaces.SharedRegionInterface;
/**
 * Repair Area Proxy.
 * */
public class RepairAreaProxy implements SharedRegionInterface {

  /**
   * Object of class Repair Area
   * */
  private final RepairArea repairArea;

  /**
   * RepairAreaProxy Constructor
   * @param repairArea  repair area object
   * */
  public RepairAreaProxy(RepairArea repairArea)
  {
    this.repairArea = repairArea;
  }

  /**
   * Method from the SharedRegionInterface. It is used to receive, process incoming messages and respond to them.
   * @param msg    The received message.
   * @param sc     object ServerCom to receive and send message to the source of msg.
   * @return The reply of the correspondent message.
   * */
  @Override
  public Message processMessage(Message msg, ServerCom sc) {
      Message response = null;
      int resInt;
      boolean resBool;

      switch (msg.getType())
      {
        case CHECK_CAR:
          resInt = this.repairArea.checkCar(msg.getCarId(), msg.getMechanicId());
          response = new Message(MessageType.CHECK_CAR_RES, resInt);
          break;

        case REPAIR_CAR:
          resBool = this.repairArea.repairCar(msg.getCarId(), msg.getCarPart(), msg.getMechanicId());
          response = new Message(MessageType.REPAIR_CAR_RES, resBool);
          break;

        case REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE:
          resInt = this.repairArea.repairWaitingCarWithPartsAvailable(msg.getMechanicId());
          response = new Message(MessageType.REPAIR_WAITING_CAR_WITH_PARTS_AVAILABLE_RES, resInt);
          break;

        case CONCLUDE_CAR_REPAIR:
          this.repairArea.concludeCarRepair(msg.getCarId(), msg.getMechanicId());
          response = new Message(MessageType.OK);
          break;

        case REFILL_CAR_PART_STOCK:
          this.repairArea.refillCarPartStock(msg.getCarPart(), msg.getNumPart());
          response = new Message(MessageType.OK);
          break;

        case GET_MAX_PART_STOCK:
          resInt = this.repairArea.getMaxPartStock(msg.getCarPart());
          response = new Message(MessageType.GET_MAX_PART_STOCK_RES,resInt);
          break;

        case FIND_NEXT_TASK:
          resInt = this.repairArea.findNextTask(msg.getMechanicId());
          response = new Message(MessageType.FIND_NEXT_TASK_RES, resInt);
          break;

        case POST_JOB:
          this.repairArea.postJob(msg.getCarId());
          response = new Message(MessageType.OK);
          break;

        case SEND_HOME:
          this.repairArea.sendHome();
          response = new Message(MessageType.OK);
          break;

        case FINISH:
          this.repairArea.finish();
          response = new Message(MessageType.OK);
          break;

        default:
          System.out.println("message not expected.");
          System.exit(1);
          break;
      }
      return response;
  }
}
