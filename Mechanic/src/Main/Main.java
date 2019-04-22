package Main;

import Locals.Lounge;
import Locals.Park;
import Locals.RepairArea;

public class Main {

    public static void main(String[] args)
    {   Lounge lounge = new Lounge("localhost",22460);
        Park park = new Park("localhost",22461);
        RepairArea repairArea = new RepairArea("localhost",22463);

        int NUM_MECHANICS = 2;

        Mechanic[] mechanic = new Mechanic[NUM_MECHANICS];
        for(int i = 0; i<NUM_MECHANICS; i++)
        {   mechanic[i] = new Mechanic(i,lounge,park,repairArea);
            mechanic[i].start();
        }

        for(int i = 0; i<NUM_MECHANICS;i++)
        {   try { mechanic[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
