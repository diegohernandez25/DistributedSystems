package Locations;

public class GeneralRepInformation {

    /**
     *  Number of Customers
     * */
    int numCustomers;

    /**
     *  Number of Mechanics
     * */
    int numMechanics;

    /**
     *  Number of car parts
     * */
    int numParts;

    /**
     *  State of Manager
     * */
    int stateManager;

    /**
     *  State of Customer
     * */
    int stateCustomer;

    /**
     *  State of Mechanic
     * */
    int stateMechanic;

    /**
     *
     *  Instantiation of GeneralRepInformation
     *
     *  Logger IS a monitor
     *
     *  Initialization of values to be updated
     *  Each time a thread does a new action, updates logger with the specific value
     *
     * */
    public GeneralRepInformation(int numCustomers, int numMechanics, int numParts)
    {
        this.numCustomers = numCustomers;
        this.numMechanics = numMechanics;
        this.numParts = numParts;

        this.stateManager = 0;
        this.stateCustomer = 0;
        this.stateMechanic = 0;


    }

    public synchronized void updateLogger()
    {
        /**
        *
        *  Clear console
        *
        */
        System.out.print("\033[H\033[2J");
        System.out.flush();

        /**
         *
         *  Print Static content
         *
         */
        System.out.printf(" MAN  MECHANIC                                                                 CUSTOMER                                                                                       %n" +
                "Stat  St0 St1  S00 C00 P00 R00 S01 C01 P01 R01 S02 C02 P02 R02 S03 C03 P03 R03 S04 C04 P04 R04 S05 C05 P05 R05 S06 C06 P06 R06 S07 C07 P07 R07 S08 C08 P08 R08 S09 C09 P09 R09%n" +
                "               S10 C10 P10 R10 S11 C11 P11 R11 S12 C12 P12 R12 S13 C13 P13 R13 S14 C14 P14 R14 S15 C15 P15 R15 S16 C16 P16 R16 S17 C17 P17 R17 S18 C18 P18 R18 S19 C19 P19 R19%n" +
                "               S20 C20 P20 R20 S21 C21 P21 R21 S22 C22 P22 R22 S23 C23 P23 R23 S24 C24 P24 R24 S25 C25 P25 R25 S26 C26 P26 R26 S27 C27 P27 R27 S28 C28 P28 R28 S29 C29 P29 R29%n" +
                "                  LOUNGE        PARK                             REPAIR AREA                                           SUPPLIERS SITE                                         %n" +
                "               InQ WtK NRV    NCV  NPV       NSRQ   Prt0  NV0  S0 Prt1  NV1  S1 Prt2  NV1  S1                         PP0   PP1   PP2                                         %n");

        //FIXME monitor gives states
        /**
         *
         *  Print Manager states
         *
         */
        System.out.printf("%04d ", 1);

        //FIXME monitor gives the number and states
        /**
         *
         *  For every Mechanic, print its state
         *
         */
        for(int i = 0; i < numMechanics; i++) {
            System.out.printf(" %03d", i);
        }

        //FIXME monitor gives the number and details
        /**
         *
         *  For every Customer, print its
         *      state - current state of Customer
         *              Integer
         *      vehicle - current vehicle driven by Customer
         *                own car: Integer customer ID
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
                System.out.printf("%n             ");
            }

            System.out.printf("  %03d  %02d  %01d   %01d", i%10, i%10, i%10, i%10);
        }

        //FIXME monitor gives details
        /**
         *
         *  Print Locations description
         *
         */

        /**
         *
         *      Lounge:
         *          InQ - number of Customers in queue
         *          WtK - number of Customers waiting for replacement car
         *          NRV - number of cars that have been repaired
         *
         */
        System.out.printf("%n                %02d  %02d  %02d", 1, 1, 1);

        /**
         *
         *     Park:
         *          NCV - number of Customer cars parked
         *          NPV - number of replacement cars parked
         *
         */
        System.out.printf("     %02d   %02d", 2, 2);

        /**
         *
         *     Repair Area:
         *         NSRQ - number of requests made by Manager to Repair Area
         *         Prt# - number of parts # in storage
         *         NV# - number of cars waiting for part #
         *         S# - flag signaling Manager has been alerted for missing part # (T/F)
         *
         * */
        System.out.printf("        %02d   ", 3);
        for(int i = 0; i < numParts; i++) {
            System.out.printf("  %02d    %02d   %01d", i, i, i);
        }

        /**
         *
         *      Suppliers Site:
         *          PP# - number of each parts that have been purchased so far
         *
         * */
        System.out.printf("                      ");
        for(int i = 0; i < numParts; i++) {
            System.out.printf("    %02d", i);
        }
    }
}
