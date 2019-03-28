package Loggers;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {


    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int WARNING = 2;

    /**
     * @param msg - Message to color in red.
     * @return message colored in red.
     * */
    private static String errorFormat(String msg) { return ANSI_RED + msg + ANSI_RESET; }

    /**
     * @param msg - Message to color in green.
     * @return message colored in green.
     * */
    private static String successFormat(String msg) { return ANSI_GREEN + msg + ANSI_RESET; }

    /**
     * @param msg - Message to color in yellow.
     * @return message colored in yellow.
     * */
    private static String warningFormat(String msg) { return ANSI_YELLOW + msg + ANSI_RESET ; }

    /**
     * @param entity - Name of the entity. On this context, it should be Customer/Manager/Mechanic.
     * @param local - locality of the action. On this context, it should be
     *              Outside World/Louge/Supplier Site/Repair Area/...
     * @param msg - Message to log.
     * @param pid - Id of the process.
     * @param type - type of message. 0 - Success, 1 - Error, 2 - Warning, default - Normal.
     * */
    public static void log(String entity, String local, String msg, int pid, int type)
    {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String log = "[ " + timeStamp + " ]\t"+"[ "+pid+" ]\t"+"( "+local +" )\t"+entity+": "+msg+".";
        printCorrepondentFormat(log,type);
    }

    public static void log(String entity, String local, String function, String msg, int pid, int type)
    {
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String log = "[ " + timeStamp + " ]\t"+"[ "+pid+" ]\t"+"( "+local +" )\t"+"( "+function+" )"+entity+": "+msg+".";
        printCorrepondentFormat(log,type);
    }

    private static void printCorrepondentFormat(String log, int type)
    {
        switch (type)
        {
            case SUCCESS:
                System.out.println(successFormat(log));
                break;
            case ERROR:
                System.out.println(errorFormat(log));
                break;
            case WARNING:
                System.out.println(warningFormat(log));
                break;
            default:
                //System.out.println(log);
                break;
        }
    }

    /**
     * @param e - The Exception.
     * */
    public static void logException(Exception e)
    {
        System.out.println("ERROR: "+e.toString());
        System.out.println("ERROR: "+e.getMessage());
        e.printStackTrace();
    }

}
