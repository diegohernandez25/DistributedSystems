package Entities;

import Locations.GeneralRepInformation;
import Locations.Lounge;
import Locations.Park;
import Locations.RepairArea;
import Loggers.Logger;

public class Mechanic extends Thread {


    private String  MECHANIC = "Mechanic",
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
    {   String FUNCTION = "run";
        int idCurrentCar;
        int idCurrentKey;
        boolean WORK = true;
        while(WORK)
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
                        Logger.log(MECHANIC,MECHANIC, FUNCTION,"Parking continue and concluded car",mechanicId,10);
                        repairArea.concludeCarRepair(idCurrentCar,mechanicId);                  // Registers repair conclusion
                                                                                                // on Repair Area

                        idCurrentKey = idCurrentCar;
                        Logger.log(MECHANIC,MECHANIC, FUNCTION,"Going to park customer car"+idCurrentCar,mechanicId,10);
                        park.parkCar(idCurrentCar,mechanicId);
                        gri.setNumCarsParked(1);                                                // Log Customer car parked
                        idCurrentCar=-1;// Leaves car at the park
                        Logger.log(MECHANIC,MECHANIC, FUNCTION,"Going to alert manager about fixed car" +
                                "and give back customer key"+idCurrentKey,mechanicId,10);
                        lounge.alertManagerRepairDone(idCurrentKey,mechanicId);                 // Alerts manager that repair
                        idCurrentKey=-1;                                                                        // is done
                    }
                    else{
                        Logger.log(MECHANIC,MECHANIC,RUN,
                                "Error: car wasn't reserved. This should not happen",0,Logger.ERROR);
                        System.exit(1);
                    }
                    break;
                case 2:                                                                         //repair new car
                    if((idCurrentKey = lounge.getCarToRepairKey(mechanicId)) != -1)                        // If there are new cars to
                                                                                                // repair
                    {
                        Logger.log(MECHANIC,MECHANIC,RUN,"Got car key to repair "+idCurrentKey,mechanicId,10);
                        if((idCurrentCar = park.getCar(idCurrentKey,mechanicId)) == -1)                    //Gets car at the park
                        {
                            Logger.log(MECHANIC,MECHANIC,RUN,
                                    "Error: car is not parked. This should not happend!",
                                    0, Logger.ERROR);
                            System.exit(1);
                        }
                        gri.setNumCarsParked(-1);                                               // Log Customer car removed from park
                        Logger.log(MECHANIC,MECHANIC,RUN,"Checking car "+idCurrentCar,mechanicId,10);
                        int carPart = repairArea.checkCar(idCurrentCar,mechanicId);//FIXME             // Checks which part the car


                                                                                                // needs for its repair.
                        Logger.log(MECHANIC,MECHANIC,RUN,"Check done. Car needs part "+(carPart)+". Proceeding to repair"
                                ,mechanicId,10);
                        if(!repairArea.repairCar(idCurrentCar,(carPart),mechanicId))
                        {
                            Logger.log(MECHANIC,MECHANIC,RUN,"Car part "+(carPart)+" not available. Requesting stock refill"
                                    ,mechanicId,Logger.WARNING);
                            lounge.requestPart(carPart, repairArea.getMaxPartStock(carPart)     // Requests parts.
                                    ,mechanicId);
                            gri.setNumCarWaitingPart(carPart, 1);                          // Log new car waiting for part
                            continue;                                                           // Re-starts cycle once again.
                        }
                        Logger.log(MECHANIC,MECHANIC,RUN,"Start fixing procedure"
                                ,mechanicId,10);

                        gri.setNumPartAvailable(carPart, -1);                              // Log car part being needed

                        fixCar();                                                               // Mechanic starts fixing the
                                                                                                // car
                        Logger.log(MECHANIC,MECHANIC,RUN,"Car fixed. Concluding repair"
                                ,mechanicId,Logger.SUCCESS);
                        repairArea.concludeCarRepair(idCurrentCar,mechanicId);                  // Registers conclusion of
                                                                                                // repair at the repair Area
                        Logger.log(MECHANIC,MECHANIC,RUN,"Parking car",mechanicId,10);
                        park.parkCar(idCurrentCar, mechanicId);
                        gri.setNumCarsParked(1);                                                // Log repaired car parked
                        Logger.log(MECHANIC,MECHANIC,RUN,"Alerting manager that car is fixed.",mechanicId,Logger.SUCCESS);
                        lounge.alertManagerRepairDone(idCurrentCar,mechanicId);                 // Alerts manager that the repair
                        // is done
                        continue;                                                               // Goes back to cycle
                    }
                    break;
                case 3:
                    Logger.log(MECHANIC,MECHANIC,RUN, "Mechanic has waken up",0,Logger.SUCCESS);
                    break;
                case 4:
                    WORK = false;
                    Logger.log(MECHANIC,MECHANIC,RUN, "Going home",0,Logger.SUCCESS);
                    break;
                default:
                    Logger.log(MECHANIC,MECHANIC,RUN, "Option not registered",0,Logger.ERROR);
                    break;
            }
        }
        Logger.log(MECHANIC,MECHANIC,RUN,"Mechanic Done",mechanicId,Logger.SUCCESS);
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
