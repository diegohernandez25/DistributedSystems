import Locals.Lounge;
import Locals.Park;
import Locals.RepairArea;
import Main.Parameters;
import Interfaces.*;
import Main.Mechanic;

public class Main {

    public static void main(String[] args)
    {   Lounge lounge = new Lounge(Parameters.loungeHost, Parameters.loungePort);
        Park park = new Park(Parameters.parkHost, Parameters.parkPort);
        RepairArea repairArea = new RepairArea(Parameters.raHost, Parameters.raPort);

        int NUM_MECHANICS = Parameters.numMechanics;

        Mechanic[] mechanic = new Mechanic[NUM_MECHANICS];
        for(int i = 0; i<NUM_MECHANICS; i++)
        {   mechanic[i] = new Mechanic(i, (MechanicLounge) lounge, (MechanicPark) park, (MechanicRA) repairArea);
            mechanic[i].start();
        }

        for(int i = 0; i<NUM_MECHANICS;i++)
        {   try { mechanic[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
