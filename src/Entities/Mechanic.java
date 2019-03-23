package Entities;

import Locations.Lounge;
import Locations.Park;
import Locations.RepairArea;
import Loggers.Logger;

public class Mechanic extends Thread {


    public String   MECHANIC = "Mechanic",
                    RUN = "run";
    /**
     *  Mechanic identification
     *
     *      @serialField mechanicId
     *
     * */
    private int mechanicId;

    /**
     *  Lounge
     *
     *      @serialField lounge
     * */
    private Lounge lounge;

    /**
     *  Parking Lot
     *
     *      @serialField park
     * */
    private Park park;

    /**
     *  Repair Area
     *
     *      @serialField repairArea
     * */
    private RepairArea repairArea;

    /**
     *  Instantiation of Mechanic Thread.
     *
     *      @param mechanicId identification of Mechanic.
     *      @param lounge used lounge.
     *      @param park used park.
     *      @param repairArea used repair area.
     * */


    public Mechanic(int mechanicId, Lounge lounge, Park park, RepairArea repairArea)
    {
        this.mechanicId = mechanicId;
        this.lounge = lounge;
        this.park = park;
        this.repairArea = repairArea;
    }

    /**
     *
     *  Life cycle of Mechanic
     *
     *  */
    @Override
    public void run()
    {
        int idCurrentCar;
        int idCurrentKey;
        while(true)
        {
            this.readsPaper();
            if((idCurrentCar = repairArea.repairWaitingCarWithPartsAvailable() )!= -1)  // If mechanic is able to repair
                                                                                        // a car that was waiting for
                                                                                        // parts he/she repairs it
            {
                repairArea.concludeCarRepair(idCurrentCar);                             // Registers repair conclusion
                                                                                        // on Repair Area
                idCurrentKey = idCurrentCar;
                park.parkCar(idCurrentCar);                                             // Leaves car at the park
                lounge.alertManagerRepairDone(idCurrentKey);                            // Alerts manager that repair
                                                                                        // is done
            }

            else if((idCurrentKey = lounge.checkCarToRepair()) == -1)                   // If there are new cars to
                                                                                        // repair
            {
                if((idCurrentCar = park.getCar(idCurrentKey)) == -1)                    //Gets car at the park
                {
                    Logger.log(MECHANIC,MECHANIC,RUN,
                            "Error: car is not parked. This should not happend!",
                            0, Logger.ERROR);
                    System.exit(1);
                }
                int carPart = repairArea.checkCar(idCurrentCar);                        // Checks which part the car
                                                                                        // needs for its repair.
                if(!repairArea.repairCar(idCurrentCar,carPart))
                {
                    lounge.requestPart(carPart, repairArea.getMaxPartStock(carPart));   // Requests parts.
                    continue;                                                           // Re-starts cycle once again.
                }
                fixCar();                                                               // Mechanic starts fixing the
                                                                                        // car
                repairArea.concludeCarRepair(idCurrentCar);                             // Registers conclusion of
                                                                                        // repair at the repair Area
                if(!park.parkCar(idCurrentCar))                                         // Parks the car
                {   Logger.log(MECHANIC,MECHANIC,RUN,
                        "Error: The car is already in the park. " +
                                "This should not have happened!",
                        0, Logger.ERROR);
                    System.exit(1);
                }
                lounge.alertManagerRepairDone(idCurrentCar);                            // Alerts manager that the repair
                                                                                        // is done
                continue;                                                               // Goes back to cycle
            }
            else
            {
                readsPaper();                                                           // If nothing to do, reads paper;
                //TODO: Maybe alert the mechanic with a notify all?
            }
        }
    }

    @Override
    public String toString()
    {
        return "Mechanic{" +
                "id=" + mechanicId +
                '}';
    }

    /**
     *  Fixing the car (internal operation)
     * */
    private void fixCar()
    {
        try
        {
            sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){ }
    }

    /**
     *      Reads paper
     *
     * */
    private void readsPaper()
    {
        try{
            sleep((long) (1 + 40 * Math.random()));
        }
        catch(InterruptedException e){}
    }
}
