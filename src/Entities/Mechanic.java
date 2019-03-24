package Entities;

import Locations.GeneralRepInformation;
import Locations.Lounge;
import Locations.Park;
import Locations.RepairArea;
import Loggers.Logger;

public class Mechanic extends Thread {
    public String   MECHANIC = "Mechanic",
                    RUN = "run";

    /**
     *  Initialize GeneralRepInformation
     * */
    private GeneralRepInformation gri;

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


    public Mechanic(int mechanicId, Lounge lounge, Park park, RepairArea repairArea, GeneralRepInformation gri)
    {
        this.mechanicId = mechanicId;
        this.lounge = lounge;
        this.park = park;
        this.repairArea = repairArea;
        this.gri = gri;
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

            switch (repairArea.findNextTask())
            {
                case 1: //Continue repairing car
                    if((idCurrentCar = repairArea.repairWaitingCarWithPartsAvailable() )!= -1)  // If mechanic is able to repair
                                                                                                // a car that was waiting for
                                                                                                // parts he/she repairs it
                    {
                        fixCar();
                        repairArea.concludeCarRepair(idCurrentCar);                             // Registers repair conclusion
                                                                                                // on Repair Area
                        gri.setNumCarsRepaired();                                               // Log additional car repaired

                        idCurrentKey = idCurrentCar;
                        park.parkCar(idCurrentCar);                                             // Leaves car at the park
                        gri.setNumCarsParked(1);                                                // Log Customer car parked

                        lounge.alertManagerRepairDone(idCurrentKey);                            // Alerts manager that repair
                                                                                                // is done
                    }
                    else{
                        Logger.log(MECHANIC,MECHANIC,RUN,
                                "Error: car wasn't reserved. This should not happen",0,Logger.ERROR);
                        System.exit(1);
                    }
                    break;
                case 2:                                                                         //repair new car
                    if((idCurrentKey = lounge.checkCarToRepair()) == -1)                        // If there are new cars to
                                                                                                // repair
                    {
                        if((idCurrentCar = park.getCar(idCurrentKey)) == -1)                    //Gets car at the park
                        {
                            Logger.log(MECHANIC,MECHANIC,RUN,
                                    "Error: car is not parked. This should not happend!",
                                    0, Logger.ERROR);
                            System.exit(1);
                        }
                        gri.setNumCarsParked(-1);                                               // Log Customer car removed from park

                        int carPart = repairArea.checkCar(idCurrentCar);                        // Checks which part the car
                                                                                                // needs for its repair.
                        if(!repairArea.repairCar(idCurrentCar,carPart))
                        {
                            lounge.requestPart(carPart, repairArea.getMaxPartStock(carPart));   // Requests parts.
                            gri.setFlagMissingPart(carPart, "T");                          // Log Manager has been advised for missing part
                            gri.setNumCarWaitingPart(carPart, 1);                          // Log new car waiting for part

                            continue;                                                           // Re-starts cycle once again.
                        }

                        gri.setNumPartAvailable(carPart, -1);                              // Log car part being needed

                        fixCar();                                                               // Mechanic starts fixing the
                                                                                                // car
                        repairArea.concludeCarRepair(idCurrentCar);                             // Registers conclusion of
                                                                                                // repair at the repair Area
                        gri.setNumCarsRepaired();                                               // Log additional car repaired

                        if(!park.parkCar(idCurrentCar))                                         // Parks the car
                        {   Logger.log(MECHANIC,MECHANIC,RUN,
                                "Error: The car is already in the park. " +
                                        "This should not have happened!",
                                0, Logger.ERROR);
                            System.exit(1);
                        }
                        gri.setNumCarsParked(1);                                                // Log repaired car parked
                        lounge.alertManagerRepairDone(idCurrentCar);                            // Alerts manager that the repair
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
