package Main;

import Interfaces.*;

import java.rmi.RemoteException;

public class Mechanic extends Thread {
    /**
     *  Mechanic identification
     *
     * */
    private int mechanicId;

    /**
     *  Lounge
     * */
    private LoungeMechanicInterface lounge;

    /**
     *  Parking Lot
     * */
    private ParkCustomerMechanicInterface park;

    /**
     *  Repair Area
     * */
    private RepairAreaMechanicInterface repairArea;

    /**
     *  Instantiation of Mechanic Thread.
     *
     *      @param mechanicId identification of Mechanic.
     *      @param lounge used Lounge.
     *      @param park used Park.
     *      @param repairArea used Repair Area.
     * */


    public Mechanic(int mechanicId, LoungeMechanicInterface lounge, ParkCustomerMechanicInterface park, RepairAreaMechanicInterface repairArea)
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
        try{
            while(WORK)
            {   switch (repairArea.findNextTask(mechanicId))
            {   /**
             *   Continue repair car.
             */
                case 1:
                    if((idCurrentCar = repairArea.repairWaitingCarWithPartsAvailable(mechanicId)) != -1)
                    {   System.out.println(this.mechanicId + ". Continuing to repair car");
                        fixCar();
                        repairArea.concludeCarRepair(idCurrentCar,mechanicId);
                        System.out.println(this.mechanicId + ". Repair concluded");
                        idCurrentKey = idCurrentCar;
                        park.parkCar(idCurrentCar, mechanicId, false);
                        System.out.println(this.mechanicId + ". Car parked");
                        lounge.alertManagerRepairDone(idCurrentKey,mechanicId);
                        System.out.println(this.mechanicId + ". Alerting manager that car is repaired.");
                    }
                    else
                    {   System.out.println(this.mechanicId + ". This should not happen!");
                        System.exit(1);
                    }
                    break;
                /**
                 *  Repair new car.
                 * */
                case 2:
                    if((idCurrentKey = lounge.getCarToRepairKey(mechanicId)) != -1)
                    {   System.out.println(this.mechanicId + ". Going to repair a ne car.Going to park.");
                        if((idCurrentCar = park.getCar(idCurrentKey, mechanicId, false)) == -1)
                        {   System.out.println(this.mechanicId + ". This should not happen!");
                            System.exit(1);
                        }
                        System.out.println(this.mechanicId + ". Checking car parts...");
                        int carPart = repairArea.checkCar(idCurrentCar,mechanicId);
                        System.out.println(this.mechanicId + ". Looking if there is parts available");
                        if(!repairArea.repairCar(idCurrentCar,carPart,mechanicId))
                        {   lounge.requestPart(carPart, repairArea.getMaxPartStock(carPart), mechanicId);
                            System.out.println(this.mechanicId + ". Parts not available. Requesting parts.");
                            continue;
                        }
                        System.out.println(this.mechanicId + ". Fixing car.");
                        fixCar();
                        System.out.println(this.mechanicId + ". Car fixed");
                        repairArea.concludeCarRepair(idCurrentCar, mechanicId);
                        System.out.println(this.mechanicId + ". Repair concluded");
                        park.parkCar(idCurrentCar, mechanicId, false);
                        System.out.println(this.mechanicId + ". Car parked");
                        lounge.alertManagerRepairDone(idCurrentCar,mechanicId);
                        System.out.println(this.mechanicId + ". Manager alerted about repair.");
                        continue;
                    }
                    break;
                case 3:
                    System.out.println(this.mechanicId + ". Mechanic has waken up");
                    break;
                case 4:
                    WORK = false;
                    System.out.println(this.mechanicId + ". Going home.");
                    break;
                default:
                    System.out.println(this.mechanicId + ". Option not registered");
                    break;
            }
            }
            this.lounge.finish();
            System.out.println(this.mechanicId + ". FINISHED!");

        }catch (RemoteException e) {
            e.printStackTrace();
        }

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
}
