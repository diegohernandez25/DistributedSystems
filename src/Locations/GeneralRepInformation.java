package Locations;

public class GeneralRepInformation {

    /**
     * This will be the logger.
     */

    // Clear console
    System.out.print("\033[H\033[2J");
    System.out.flush();

    // Static content
    System.out.printf(" MAN  MECHANIC                                                                 CUSTOMER                                                                                       %n" +
                      "Stat  St0 St1  S00 C00 P00 R00 S01 C01 P01 R01 S02 C02 P02 R02 S03 C03 P03 R03 S04 C04 P04 R04 S05 C05 P05 R05 S06 C06 P06 R06 S07 C07 P07 R07 S08 C08 P08 R08 S09 C09 P09 R09%n" +
                      "               S10 C10 P10 R10 S11 C11 P11 R11 S12 C12 P12 R12 S13 C13 P13 R13 S14 C14 P14 R14 S15 C15 P15 R15 S16 C16 P16 R16 S17 C17 P17 R17 S18 C18 P18 R18 S19 C19 P19 R19%n" +
                      "               S20 C20 P20 R20 S21 C21 P21 R21 S22 C22 P22 R22 S23 C23 P23 R23 S24 C24 P24 R24 S25 C25 P25 R25 S26 C26 P26 R26 S27 C27 P27 R27 S28 C28 P28 R28 S29 C29 P29 R29%n" +
                      "                  LOUNGE        PARK                             REPAIR AREA                                           SUPPLIERS SITE                                         %n" +
                      "               InQ WtK NRV    NCV  NPV       NSRQ   Prt0  NV0  S0 Prt1  NV1  S1 Prt2  NV1  S1                         PP0   PP1   PP2                                         %n");

    // Manager states
    System.out.printf("%04d ", 1);

    //FIXME Should be number of mechanics
    for(int i = 0; i < 2; i++) {
        System.out.printf(" %03d", i);
    }

    //FIXME Should be number of clients
    for(int i = 0; i < 30; i++) {
        if(i % 10 == 0 && i != 0){
            System.out.printf("%n             ");
        }
        System.out.printf("  %03d  %02d  %01d   %01d", i%10, i%10, i%10, i%10);
    }

    // Locations
    System.out.printf("%n               %02d  %02d  %02d      %02d   %02d        %02d     %02d    %02d   %01d  %02d    %02d   %01d  %02d    %02d   %01d                          %02d    %02d    %02d", 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3);
}
