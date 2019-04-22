package Locations;

import java.io.*;
import Interfaces.*;

public class GeneralRepInformation implements GriLounge, GriPark, GriRA, GriSS {

    /**
     *  Manager state abbreviation (each index is the corresponding state in integer). 4 chars max
     *                                     READ_PAPER = 0,
     *                                     ATTEND_CUSTOMER = 1,
     *                                     CALL_CUSTOMER = 2,
     *                                     FILL_STOCK = 3;
     * */
    private static final String[] managerStates = {"READ", "CUST", "CALL", "FILL"};

    /**
     *  Customer state abbreviation (each index is the corresponding state in integer). 3 chars max
     *                                 NORMAL_LIFE = 0,
     *                                 WAITING_REQUEST_REPAIR = 1,
     *                                 WAITING_REPLACEMENT_CAR = 2,
     *                                 GET_REPLACEMENT_CAR = 3,
     *                                 ATTENDED_W_SUBCAR = 4,
     *                                 ATTENDED_WO_SUBCAR = 5,
     *                                 ATTENDED = 6,
     *                                 ATTENDING = 7,
     *                                 CAR_FIXED = 8,
     *                                 GETTING_REPLACEMENT_CAR = 9,
     *                                 WAITING_ATTENDANCE = 10;
     * */
    private static final String[] customerStates = {"NOR", "WRR", "WRC", "GRC", "AWS", "AOS", "ATD", "ATG", "CFX", "GGR", "WGA"};

    /**
     *  Mechanic state abbreviation (each index is the corresponding state in integer). 3 chars max
     *                                 WAITING_FOR_WORK = 0,
     *                                 FIXING_THE_CAR = 1,
     *                                 CHECKING_STOCK = 2,
     *                                 ALERTING_MANAGER = 3;
     * */
    private static final String[] mechanicStates = {"WFW", "FTC", "CST", "ALM"};

    /**
     *  File that the logger is going to write in
     * */
    private File f;

    /**
     *  Print Writer that is going to write on file
     * */
    private PrintWriter pw;

    /**
     *  Number of Customers
     * */
    private int numCustomers;

    /**
     *  Number of Mechanics
     * */
    private int numMechanics;

    /**
     *  Number of car parts
     * */
    private int numParts;

    /**
     *  State of Manager
     * */
    private int stateManager;

    /**
     *  State of Customer
     * */
    private int[] stateCustomer;

    /**
     *  State of Mechanic
     * */
    private int[] stateMechanic;

    /**
     *  Driven car by Customer (index is Customer ID)
     * */
    private String[] customerVehicle;

    /**
     *  Customer needs replacement car (index is Customer ID)
     * */
    private String[] customerNeedsReplacement;

    /**
     *  Customer car is repaired (index is Customer ID)
     * */
    private String[] customerCarRepaired;

    /**
     *  Number of Customers in queue
     * */
    private int numCustomersQueue;

    /**
     *  Number of Customers waiting for replacement car
     * */
    private int numCustomersReplacementQueue;

    /**
     *  Number of Customer cars that have already been repaired
     * */
    private int numCarsRepaired;

    /**
     *  Number of Customer cars parked
     * */
    private int numCarsParked;

    /**
     *  Number of replacement cars parked
     * */
    private int numReplacementParked;

    /**
     *  Number of requests made by Manager to the Repair Area
     * */
    private int numPostJobs;

    /**
     *  Number of each part in storage (index is car part ID)
     * */
    private int[] numPartAvailable;

    /**
     *  Number of cars waiting for each part (index is car part ID)
     * */
    private int[] numCarWaitingPart;

    /**
     *  Flag signaling Manager has been notified of each missing part (index is car part ID)
     * */
    private String[] flagMissingPart;

    /**
     *  Number of each car part that has been bought so far (index is car part ID)
     * */
    private int[] numBoughtPart;

    /**
     *
     *  Instantiation of GeneralRepInformation.
     *
     *  Initialization of values to be updated
     *
     *  @param numCustomers total number of Customers
     *  @param numMechanics total number of Mechanics
     *  @param numParts total number of Car Parts
     *  @param carParts starting stock of Car Parts
     *  @param nameOfFile name of the file where the log is going to be written in
     *
     * */
    public GeneralRepInformation(int numCustomers, int numMechanics, int numParts, int[] carParts, String nameOfFile)
    {
        // initialize file to be written on
        this.f = new File(nameOfFile);

        // initialize Print Writer, to create a new file
        try
        {
            this.pw = new PrintWriter(new FileOutputStream(f));
        }
        catch(Exception e)
        {
            System.out.println("Couldn't initialize Print Writer\nException:\n" + e);
        }

        // initialize number of entities
        this.numCustomers = numCustomers;
        this.numMechanics = numMechanics;
        this.numParts = numParts;

        // initialize state of entities
        this.stateManager = 0;
        this.stateCustomer = new int[numCustomers];
        for(int i = 0; i < stateCustomer.length; i++)
            this.stateCustomer[i] = 0;
        this.stateMechanic = new int[numMechanics];
        for(int i = 0; i < stateMechanic.length; i++)
            this.stateMechanic[i] = 0;

        // initialize Customer vehicle array
        this.customerVehicle = new String[numCustomers];
        for(int i = 0; i < customerVehicle.length; i++)
            this.customerVehicle[i] = String.valueOf(i);

        // initialize Customer needs replacement car array
        this.customerNeedsReplacement = new String[numCustomers];
        for(int i = 0; i < customerNeedsReplacement.length; i++)
            this.customerNeedsReplacement[i] = "F";

        // initialize Customer car repaired array
        this.customerCarRepaired = new String[numCustomers];
        for(int i = 0; i < customerCarRepaired.length; i++)
            this.customerCarRepaired[i] = "F";

        // initialize number of Customers in queue
        this.numCustomersQueue = 0;

        // initialize number of Customers waiting for replacement car
        this.numCustomersReplacementQueue = 0;

        // initialize number of Customer cars that have been already repaired
        this.numCarsRepaired = 0;

        // initialize number of Customer cars parked in the Park
        this.numCarsParked = 0;

        // initialize number of replacement cars parked in the Park
        this.numReplacementParked = 0;

        // initialize number of requests made by Manager to the Repair Area
        this.numPostJobs = 0;

        // initialize number of each car part in stock array
        this.numPartAvailable = carParts;

        // initialize number of cars waiting for each car part array
        this.numCarWaitingPart = new int[numParts];
        for(int i = 0; i < numCarWaitingPart.length; i++)
            this.numCarWaitingPart[i] = 0;

        // initialize flag signaling Manager has been notified of each missing part array
        this.flagMissingPart = new String[numParts];
        for(int i = 0; i < flagMissingPart.length; i++)
            this.flagMissingPart[i] = "F";

        // initialize number of car parts that have already been bought in Suppliers Site array
        this.numBoughtPart = new int[numParts];
        for(int i = 0; i < numBoughtPart.length; i++)
        {
            this.numBoughtPart[i] = 0;
        }

        this.print();
    }

    /**
     *  Set the state of the Manager
     *  @param state the current state of the Manager
     * */
    public synchronized void setStateManager(int state)
    {
        this.stateManager = state;
        this.print();
    }

    /**
     *  Set the state of a Customer
     *  @param customer the Customer that is going to change state
     *  @param state the current state of specified Customer
     * */
    public synchronized void setStateCustomer(int customer, int state)
    {
        this.stateCustomer[customer] = state;
        this.print();
    }

    /**
     *  Set the state of a Mechanic
     *  @param mechanic the Mechanic that is going to change state
     *  @param state the current state of specified Mechanic
     * */
    public synchronized void setStateMechanic(int mechanic, int state)
    {
        this.stateMechanic[mechanic] = state;
        this.print();
    }

    /**
     *  Set the vehicle currently used by a Customer
     *  @param customer the Customer who is changing vehicles
     *  @param vehicle the vehicle that the specified Customer is changing to. Values can be: own car - customer ID; replacement car - R# (where # is the number of the replacement car); none - '-'
     * */
    public synchronized void setCustomerVehicle(int customer, String vehicle)
    {
        this.customerVehicle[customer] = vehicle;
        this.print();
    }

    /**
     *  Set if a Customer needs a replacement car. Default value is "F", this sets it to "T"
     *  @param customer the Customer who needs a replacement car
     * */
    public synchronized void setCustomerNeedsReplacement(int customer)
    {
        this.customerNeedsReplacement[customer] = "T";
        this.print();
    }

    /**
     *  Set if a Customer's car is repaired. Default value is "F", this sets it to "T"
     *  @param customer the Customer whose car has been repaired
     * */
    public synchronized void setCustomerCarRepaired(int customer)
    {
        this.customerCarRepaired[customer] = "T";
        this.print();
    }

//    /**
//     *  Set the number of Customers in queue. It's the length of the queue
//     *  @param num the number of Customers in queue
//     * */
//    public synchronized void setNumCustomersQueue(int num)
//    {
//        this.numCustomersQueue = num;
//    }

    /**
     *  Adds a Customer to the queue
     * */
    public synchronized void addCustomersQueue()
    {
        this.numCustomersQueue++;
        this.print();
    }

    /**
     *  Removes a Customer from the queue
     * */
    public synchronized void removeCustomersQueue()
    {
        this.numCustomersQueue--;
        this.print();
    }

//    /**
//     *  Set the number of Customers waiting in queue for a replacement car. It's the length of the queue
//     *  @param num the number of Customers in queue
//     * */
//    public synchronized void setNumCustomersReplacementQueue(int num)
//    {
//        this.numCustomersReplacementQueue = num;
//    }

    /**
     *  Adds a Customer to the replacement car queue
     * */
    public synchronized void addCustomersReplacementQueue()
    {
        this.numCustomersReplacementQueue++;
        this.print();
    }

    /**
     *  Removes a Customer from the replacement car queue
     * */
    public synchronized void removeCustomersReplacementQueue()
    {
        this.numCustomersReplacementQueue--;
        this.print();
    }

    /**
     *  Increments the number of cars that have been repaired
     * */
    public synchronized void setNumCarsRepaired()
    {
        this.numCarsRepaired++;
        this.print();
    }

    /**
     *  Set the number of Customer cars parked
     *  @param num the number of cars parked
     * */
    public synchronized void setNumCarsParked(int num)
    {
        this.numCarsParked += num;
        this.print();
    }

    /**
     *  Set the number of replacement cars parked
     *  @param num the number of replacement cars parked
     * */
    public synchronized void setNumReplacementParked(int num)
    {
        this.numReplacementParked += num;
        this.print();
    }

    /**
     *  Increments the number of requests the Manager has made to the Repair Area
     * */
    public synchronized void setNumPostJobs()
    {
        this.numPostJobs++;
        this.print();
    }

    /**
     *  Adds the number of parts available in stock (restocked part)
     *  @param part the part which needs to be set
     *  @param num the number of the specified part available
     * */
    public synchronized void addNumPartAvailable(int part, int num)
    {
        this.numPartAvailable[part] = num;
        this.print();
    }

    /**
     *  Removes one of the parts available in stock (used part)
     *  @param part the part which was used
     * */
    public synchronized void removeNumPartAvailable(int part)
    {
        this.numPartAvailable[part]--;
        this.print();
    }

    /**
     *  Sets the number of cars waiting for a specific part
     *  @param part the part the cars are waiting for
     *  @param num the number of cars waiting for the part. +1 if new car waiting for part, -1 if car no longer needs part
     * */
    public synchronized void setNumCarWaitingPart(int part, int num)
    {
        this.numCarWaitingPart[part] += num;
        this.print();
    }

    /**
     *  Sets a flag signaling the Manager has been adviced that a specific part is missing
     *  @param part the part which needs to be set the flag
     *  @param flag the flag signaling if Manager has been adviced (T/F)
     * */
    public synchronized void setFlagMissingPart(int part, String flag)
    {
        this.flagMissingPart[part] = flag;
        this.print();
    }

    /**
     *  Increments the number of bought parts of a specific part
     *  @param part the part that was bought
     *  @param num the number of bought parts
     * */
    public synchronized void setNumBoughtPart(int part, int num)
    {
        this.numBoughtPart[part] += num;
        this.print();
    }

    /**
     *  Prints values of Logger on console, and writes it in a file specified beforehand
     * */
    private synchronized void print()
    {
        /*
         *  Initialize temporary string
         * */
        String s;

        try
        {

            /*
             *  Initialize PrintWriter to append the new information on file
             * */
            pw = new PrintWriter(new FileOutputStream(f,true));

            /*
             *
             *  Clear console
             *
             */
//            System.out.print("\033[H\033[2J");
//            System.out.flush();

            /*
             *
             *  Print Static content
             *
             */
//            s = String.format(" MAN  MECHANIC                                                                 CUSTOMER                                                                             %n" +
//                    "Stat  St0 St1  S00 C00 P00 R00 S01 C01 P01 R01 S02 C02 P02 R02 S03 C03 P03 R03 S04 C04 P04 R04 S05 C05 P05 R05 S06 C06 P06 R06 S07 C07 P07 R07 S08 C08 P08 R08 S09 C09 P09 R09%n" +
//                    "               S10 C10 P10 R10 S11 C11 P11 R11 S12 C12 P12 R12 S13 C13 P13 R13 S14 C14 P14 R14 S15 C15 P15 R15 S16 C16 P16 R16 S17 C17 P17 R17 S18 C18 P18 R18 S19 C19 P19 R19%n" +
//                    "               S20 C20 P20 R20 S21 C21 P21 R21 S22 C22 P22 R22 S23 C23 P23 R23 S24 C24 P24 R24 S25 C25 P25 R25 S26 C26 P26 R26 S27 C27 P27 R27 S28 C28 P28 R28 S29 C29 P29 R29%n" +
//                    "                  LOUNGE        PARK                             REPAIR AREA                                           SUPPLIERS SITE                                         %n" +
//                    "               InQ WtK NRV    NCV  NPV       NSRQ   Prt0  NV0  S0 Prt1  NV1  S1 Prt2  NV2  S2                         PP0   PP1   PP2                                         %n");
            s = String.format(" MAN  MECHANIC                                                                 CUSTOMER                                                                             %n");
//            System.out.print(s);
            pw.append(s);

            /*
             *
             *  Print Static content based on number of entities
             *
             * */

            // this count is only to get log aligned when the number of Customers is only > 10
            int count = 0;

            // always one Manager only
            s = "Stat  ";
            // number of spaces occupied are 6
            count += 6;
//            System.out.print(s);
            pw.append(s);

            // for each Mechanic
            for(int i = 0; i < numMechanics; i++) {
                s = String.format("St%1d ", i);
                // number of spaces occupied are 4 for each Mechanic
                count += 4;
//                System.out.print(s);
                pw.append(s);
            }

            // for each Customer
            for(int i = 0; i < numCustomers; i++) {
                if(i % 10 == 0 && i != 0){
                    // print next line
                    s = String.format("%n");
//                    System.out.print(s);
                    pw.append(s);

                    // print spaces to align log
                    for(int j = 0; j < count; j++) {
                        s = " ";
//                        System.out.print(s);
                        pw.append(s);
                    }
                }
                s = String.format(" S%02d C%02d P%02d R%02d", i, i, i, i);
//                System.out.print(s);
                pw.append(s);
            }

            /*
             *
             *   Print more static content
             *
             * */
            s = String.format("%n                  LOUNGE        PARK                             REPAIR AREA                                           SUPPLIERS SITE                                         %n");
//            System.out.print(s);
            pw.append(s);

            /*
             *
             *   Print static content based on number of parts
             *
             * */
            s = "               InQ WtK NRV    NCV  NPV       NSRQ   ";
//            System.out.print(s);
            pw.append(s);

            // repair area loop
            for(int i = 0; i < numParts; i++) {
                s = String.format("Prt%1d  NV%1d  S%1d ", i, i, i);
//                System.out.print(s);
                pw.append(s);
            }

            // separate the two loops
            s = "                        ";
//            System.out.print(s);
            pw.append(s);

            // supplier site loop
            for(int i = 0; i < numParts; i++) {
                s = String.format("PP%1d   ", i);
//                System.out.print(s);
                pw.append(s);
            }

            /*
             *
             *  Print Manager states
             *
             */
            s = String.format("%n%4s ", managerStates[stateManager]);
//            System.out.print(s);
            pw.append(s);

            /*
             *
             *  For every Mechanic, print its state
             *
             */
            for(int i = 0; i < numMechanics; i++) {
                s = String.format(" %3s", mechanicStates[stateMechanic[i]]);
//                System.out.print(s);
                pw.append(s);
            }

            /*
             *
             *  For every Customer, print its
             *      state - current state of Customer
             *              String
             *      vehicle - current vehicle driven by Customer
             *                own car: customer ID
             *                replacement car: R#
             *                none: '-'
             *      need for replacement vehicle - if it needs a replacemet car
             *                                     T or F
             *      repair status - if its car has already been repaired
             *                      T or F
             *
             */
            for(int i = 0; i < numCustomers; i++) {
                if(i % 10 == 0 && i != 0){
                    // print next line
                    s = String.format("%n");
//                    System.out.print(s);
                    pw.append(s);

                    // print spaces to align log
                    for(int j = 0; j < count-1; j++) {
                        s = " ";
//                        System.out.print(s);
                        pw.append(s);
                    }
                }

                s = String.format("  %3s  %2s  %1s   %1s", customerStates[stateCustomer[i]], customerVehicle[i], customerNeedsReplacement[i], customerCarRepaired[i]);
//                System.out.print(s);
                pw.append(s);
            }

            /*
             *
             *  Print Locations description
             *
             */

            /*
             *
             *      Lounge:
             *          InQ - number of Customers in queue
             *          WtK - number of Customers waiting for replacement car
             *          NRV - number of cars that have been repaired
             *
             */
            s = String.format("%n                %02d  %02d  %02d", numCustomersQueue, numCustomersReplacementQueue, numCarsRepaired);
//            System.out.print(s);
            pw.append(s);

            /*
             *
             *     Park:
             *          NCV - number of Customer cars parked
             *          NPV - number of replacement cars parked
             *
             */
            s = String.format("     %02d   %02d", numCarsParked, numReplacementParked);
//            System.out.print(s);
            pw.append(s);

            /*
             *
             *     Repair Area:
             *         NSRQ - number of requests made by Manager to Repair Area
             *         Prt# - number of parts # in storage
             *         NV# - number of cars waiting for part #
             *         S# - flag signaling Manager has been alerted for missing part # (T/F)
             *
             * */
            s = String.format("        %02d   ", numPostJobs);
//            System.out.print(s);
            pw.append(s);
            for(int i = 0; i < numParts; i++) {
                s = String.format("  %02d    %02d   %1s", numPartAvailable[i], numCarWaitingPart[i], flagMissingPart[i]);
//                System.out.print(s);
                pw.append(s);
            }

            /*
             *
             *      Suppliers Site:
             *          PP# - number of each parts that have been purchased so far
             *
             * */
            s = "                      ";
//            System.out.print(s);
            pw.append(s);
            for(int i = 0; i < numParts; i++) {
                s = String.format("    %02d", numBoughtPart[i]);
//                System.out.print(s);
                pw.append(s);
            }

            /*
             *  Separates 2 logs and closes PrintWriter
             * */
            pw.append("\n******************************************************************************************************************************************************************************\n");
            pw.close();
        }
        catch (Exception e)
        {
            System.out.println("Couldn't write on file\nException:\n" + e);
        }
    }
}