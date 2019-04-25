package Main;

/**
* Parameters for the deployment of Assignment 2 on local or remote machines.
*/
public class Parameters {
  /**
  * General Repository Information host name
  */
  public final static String griHost = "l040101-ws01.ua.pt";

  /**
  * General Repository Information port number
  */
  public final static int griPort = 22460;

  /**
  * Lounge host name
  */
  public final static String loungeHost = "l040101-ws02.ua.pt";

  /**
  * Lounge port number
  */
  public final static int loungePort = 22461;

  /**
  * Park host name
  */
  public final static String parkHost = "l040101-ws03.ua.pt";

  /**
  * Park port number
  */
  public final static int parkPort = 22462;

  /**
  * Outside World host name
  */
  public final static String owHost = "l040101-ws04.ua.pt";

  /**
  * Outside World port number
  */
  public final static int owPort = 22463;

  /**
  * Repair Area host name
  */
  public final static String raHost = "l040101-ws05.ua.pt";

  /**
  * Repair Area port number
  */
  public final static int raPort = 22464;

  /**
  * Supplier Site host name
  */
  public final static String ssHost = "l040101-ws06.ua.pt";

  /**
  * Supplier Site port number
  */
  public final static int ssPort = 22465;

  /**
  * Manager host name
  */
  public final static String managerHost = "l040101-ws07.ua.pt";

  /**
  * Mechanic host name
  */
  public final static String mechanicHost = "l040101-ws08.ua.pt";

  /**
  * Customer host name
  */
  public final static String customerHost = "l040101-ws09.ua.pt";

  /**
  * Total number of Customers
  */
  public final static int numCustomers = 30;

  /**
  * Total number of Mechanics
  */
  public final static int numMechanics = 2;

  /**
  * Total number of Replacement Cars
  */
  public final static int numReplacementCars = 3;

  /**
  * Total number of Car Parts types.
  * */
  public static final int numCarTypes = 3;

  /**
   * Car parts
   */
  public static final int[] carParts = {0, 0, 0};

  /**
   * Maximum number of storage for each Car Part
   */
  public static final int[] maxCarParts = {1, 1, 1};
}
