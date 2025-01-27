package Main;

/**
* Parameters for the deployment of Assignment 2 on local or remote machines.
*/
public class Parameters {
  /**
  * General Repository Information host name
  */
  public final static String griHost = "localhost";

  /**
  * General Repository Information port number
  */
  public final static int griPort = 22460;

  /**
  * Lounge host name
  */
  public final static String loungeHost = "localhost";

  /**
  * Lounge port number
  */
  public final static int loungePort = 22461;

  /**
  * Park host name
  */
  public final static String parkHost = "localhost";

  /**
  * Park port number
  */
  public final static int parkPort = 22462;

  /**
  * Outside World host name
  */
  public final static String owHost = "localhost";

  /**
  * Outside World port number
  */
  public final static int owPort = 22463;

  /**
  * Repair Area host name
  */
  public final static String raHost = "localhost";

  /**
  * Repair Area port number
  */
  public final static int raPort = 22464;

  /**
  * Supplier Site host name
  */
  public final static String ssHost = "localhost";

  /**
  * Supplier Site port number
  */
  public final static int ssPort = 22465;

  /**
  * Manager host name
  */
  public final static String managerHost = "localhost";

  /**
  * Mechanic host name
  */
  public final static String mechanicHost = "localhost";

  /**
  * Customer host name
  */
  public final static String customerHost = "localhost";

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
  * Total number of Car Parts types
  */
  public final static int numCarTypes = 3;

  /**
  * Car parts
  */
  public final static int[] carParts = {0, 0, 0};

  /**
  * Maximum number of storage for each Car Part
  */
  public final static int[] maxCarParts = {1, 1, 1};
}
