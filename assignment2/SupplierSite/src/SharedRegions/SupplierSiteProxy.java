package SharedRegions;

import Communication.Message;
import Communication.MessageType;
import Communication.ServerCom;
import Interfaces.SharedRegionInterface;

public class SupplierSiteProxy implements SharedRegionInterface {

  /**
   * Object of class Supplier Site
   * */
  private final SupplierSite supplierSite;

  /**
   * SupplierSiteProxy Constructor
   * @param supplierSite - repair area object
   * */
  public SupplierSiteProxy(SupplierSite supplierSite)
  {
    this.supplierSite = supplierSite;
  }

  /**
   * Method from the SharedRegionInterface. It is used to receive, process incoming messages and respond to them.
   * @param msg   The received message.
   * @param sc    object ServerCom to receive and send message to the source of msg.
   * @return The reply of the correspondent message.
   * */
  @Override
  public Message processMessage(Message msg, ServerCom sc) {
      Message response = null;
      int resInt;

      switch (msg.getType())
      {   case RESTOCK_PART:
            resInt = this.supplierSite.restockPart(msg.getCarPart(), msg.getNumPart());
            response = new Message(MessageType.RESTOCK_PART_RES, resInt);
            break;

          case FINISH:
              this.supplierSite.finish();
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
