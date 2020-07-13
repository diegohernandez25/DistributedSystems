package Main;

public class Parameters {
  public final static int REGISTRY_PORT           = 22460;
  public final static int LOUNGE_PORT             = 22461;
  public final static int REPAIRAREA_PORT         = 22462;
  public final static int PARK_PORT               = 22463;
  public final static int OW_PORT                 = 22464;
  public final static int SS_PORT                 = 22465;
  public final static int GENERALREP_PORT         = 22466;
  public final static int SERVER_REGISTRY_PORT    = 22467;

  public final static String REGISTRY_NAME            = "Registry";
  public final static String REGISTRY_NAME_ENTRY      = "RegisterHandler";
  public final static String REPAIRAREA_NAME          = "RepairArea";
  public final static String LOUNGE_NAME              = "Lounge";
  public final static String PARK_NAME                = "Park";
  public final static String OW_NAME                  = "OutsideWorld";
  public final static String SS_NAME                  = "SupplierSite";
  public final static String GENERALREP_NAME          = "GeneralRepository";

  public final static String LOCALHOST            = "localhost";
  public final static String GENERALREP_HOST      = "l040101-ws02.ua.pt";
  public final static String LOUNGE_HOST          = "l040101-ws03.ua.pt";
  public final static String PARK_HOST            = "l040101-ws04.ua.pt";
  public final static String OW_HOST              = "l040101-ws05.ua.pt";
  public final static String REPAIRAREA_HOST      = "l040101-ws06.ua.pt";
  public final static String SS_HOST              = "l040101-ws07.ua.pt";
  public final static String MECHANIC_HOST        = "l040101-ws09.ua.pt";
  public final static String MANAGER_HOST         = "l040101-ws08.ua.pt";
  public final static String CUSTOMER_HOST        = "l040101-ws10.ua.pt";
  public final static String REGISTRY_HOST        = "l040101-ws01.ua.pt";

  // Definition of variables
  public final static int NUM_CUSTOMERS   = 30;
  public final static int NUM_MECHANICS   = 2;
  public final static int NUM_REPLACEMENT = 3;
  public final static int NUM_CAR_TYPES   = 3;
  public final static int[] CAR_PARTS     = {0, 0, 0};
  public final static int[] MAX_CAR_PARTS = {1, 1, 1};
  public final static String LOG_NAME     = "log.txt";
}
