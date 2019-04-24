package Main;

import Locals.Lounge;
import Locals.Park;
import Locals.RepairArea;
import Interfaces.*;

public class Mechanic extends Thread {
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
    private MechanicLounge lounge;

    /**
     *  Parking Lot
     *
     *      @serialField park
     * */
    private MechanicPark park;

    /**
     *  Repair Area
     *
     *      @serialField repairArea
     * */
    private MechanicRA repairArea;

    /**
     *  Instantiation of Mechanic Thread.
     *
     *      @param mechanicId identification of Mechanic.
     *      @param lounge used Lounge.
     *      @param park used Park.
     *      @param repairArea used Repair Area.
     * */


    public Mechanic(int mechanicId, MechanicLounge lounge, MechanicPark park, MechanicRA repairArea)
    {   this.mechanicId = mechanicId;
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
    {   int idCurrentCar;
        int idCurrentKey;
        boolean WORK = true;
        while(WORK)
        {   switch (repairArea.findNextTask(mechanicId))
            {   /**
                *   Continue repair car.
                */
                case 1:
                    if((idCurrentCar = repairArea.repairWaitingCarWithPartsAvailable(mechanicId)) != -1)
                    {   System.out.println("Continuing to repair car");
                        fixCar();
                        repairArea.concludeCarRepair(idCurrentCar,mechanicId);
                        System.out.println("Repair concluded");
                        idCurrentKey = idCurrentCar;
                        park.parkCar(idCurrentCar, mechanicId, false);
                        System.out.println("Car parked");
                        lounge.alertManagerRepairDone(idCurrentKey,mechanicId);
                        System.out.println("Alerting manager that car is repaired.");
                    }
                    else
                    {   System.out.println("This should not happen!");
                        System.exit(1);
                    }
                    break;
                /**
                 *  Repair new car.
                 * */
                case 2:
                    if((idCurrentKey = lounge.getCarToRepairKey(mechanicId)) != -1)
                    {   System.out.println("Going to repair a ne car.Going to park.");
                        if((idCurrentCar = park.getCar(idCurrentKey, mechanicId, false)) == -1)
                        {   System.out.println("This should not happen!");
                            System.exit(1);
                        }
                        System.out.println("Checking car parts...");
                        int carPart = repairArea.checkCar(idCurrentCar,mechanicId);
                        System.out.println("Looking if there is parts available");
                        if(!repairArea.repairCar(idCurrentCar,carPart,mechanicId))
                        {   lounge.requestPart(carPart, repairArea.getMaxPartStock(carPart), mechanicId);
                            System.out.println("Parts not available. Requesting parts.");
                            continue;
                        }
                        System.out.println("Fixing car.");
                        fixCar();
                        System.out.println("Car fixed");
                        repairArea.concludeCarRepair(idCurrentCar, mechanicId);
                        System.out.println("Repair concluded");
                        park.parkCar(idCurrentCar, mechanicId, false);
                        System.out.println("Car parked");
                        lounge.alertManagerRepairDone(idCurrentCar,mechanicId);
                        System.out.println("Manager alerted about repair.");
                        continue;
                    }
                    break;
                case 3:
                    System.out.println("Mechanic has waken up");
                    break;
                case 4:
                    WORK = false;
                    System.out.println("Going home.");
                    break;
                default:
                    System.out.println("Option not registered");
                    break;
            }
        }
        System.out.println("FINISHED!");
        //System.exit(1);
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
