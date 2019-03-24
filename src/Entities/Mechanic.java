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

            switch (repairArea.findNextTask(mechanicId))
            {
                case 1: //Continue repairing car
                    if((idCurrentCar =
                            repairArea.repairWaitingCarWithPartsAvailable(mechanicId) )!= -1)   // If mechanic is able to repair
                                                                                                // a car that was waiting for
                                                                                                // parts he/she repairs it
                    {
                        fixCar();
                        repairArea.concludeCarRepair(idCurrentCar,mechanicId);                  // Registers repair conclusion
                                                                                                // on Repair Area
                        idCurrentKey = idCurrentCar;
                        park.parkCar(idCurrentCar,mechanicId);                                  // Leaves car at the park
                        lounge.alertManagerRepairDone(idCurrentKey,mechanicId);                 // Alerts manager that repair
                                                                                                // is done
                    }
                    else{
                        Logger.log(MECHANIC,MECHANIC,RUN,
                                "Error: car wasn't reserved. This should not happen",0,Logger.ERROR);
                        System.exit(1);
                    }
                    break;
                case 2:                                                                         //repair new car
                    if((idCurrentKey = lounge.getCarToRepairKey(mechanicId)) == -1)                        // If there are new cars to
                                                                                                // repair
                    {
                        if((idCurrentCar = park.getCar(idCurrentKey,mechanicId)) == -1)                    //Gets car at the park
                        {
                            Logger.log(MECHANIC,MECHANIC,RUN,
                                    "Error: car is not parked. This should not happend!",
                                    0, Logger.ERROR);
                            System.exit(1);
                        }
                        int carPart = repairArea.checkCar(idCurrentCar,mechanicId);             // Checks which part the car
                                                                                                // needs for its repair.
                        if(!repairArea.repairCar(idCurrentCar,carPart,mechanicId))
                        {
                            lounge.requestPart(carPart, repairArea.getMaxPartStock(carPart)     // Requests parts.
                                    ,mechanicId);
                            continue;                                                           // Re-starts cycle once again.
                        }
                        fixCar();                                                               // Mechanic starts fixing the
                        // car
                        repairArea.concludeCarRepair(idCurrentCar,mechanicId);                  // Registers conclusion of
                        // repair at the repair Area
                        if(!park.parkCar(idCurrentCar, mechanicId));                            // Parks the car
                        {   Logger.log(MECHANIC,MECHANIC,RUN,
                                "Error: The car is already in the park. " +
                                        "This should not have happened!",
                                0, Logger.ERROR);
                            System.exit(1);
                        }
                        lounge.alertManagerRepairDone(idCurrentCar,mechanicId);                 // Alerts manager that the repair
                        // is done
                        continue;                                                               // Goes back to cycle
                    }
                    break;
                case 3:
                    Logger.log(MECHANIC,MECHANIC,RUN, "Mechanic has waken up",0,Logger.SUCCESS);
                    break;
                default:
                    Logger.log(MECHANIC,MECHANIC,RUN, "Option not registered",0,Logger.ERROR);
                    break;
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
