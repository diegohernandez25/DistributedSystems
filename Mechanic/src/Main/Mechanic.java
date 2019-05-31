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
    private LoungeInterface lounge;

    /**
     *  Parking Lot
     * */
    private ParkInterface park;

    /**
     *  Repair Area
     * */
    private RepairAreaInterface repairArea;

    /**
     *  Instantiation of Mechanic Thread.
     *
     *      @param mechanicId identification of Mechanic.
     *      @param lounge used Lounge.
     *      @param park used Park.
     *      @param repairArea used Repair Area.
     * */


    public Mechanic(int mechanicId, LoungeInterface lounge, ParkInterface park, RepairAreaInterface repairArea)
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
        {   int task = 0;
            try {
                task = repairArea.findNextTask(mechanicId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //switch (repairArea.findNextTask(mechanicId)) TODO: Works??
            switch (task)
            {
            /**
            *   Continue repair car.
            */
            case 1:
                try {
                    if((idCurrentCar = repairArea.repairWaitingCarWithPartsAvailable(mechanicId)) != -1)
                    {   System.out.println(this.mechanicId + ". Continuing to repair car");
                        fixCar();
                        try {
                            repairArea.concludeCarRepair(idCurrentCar,mechanicId);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        System.out.println(this.mechanicId + ". Repair concluded");
                        idCurrentKey = idCurrentCar;
                        try {
                            park.parkCar(idCurrentCar, mechanicId, false);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        System.out.println(this.mechanicId + ". Car parked");
                        try {
                            lounge.alertManagerRepairDone(idCurrentKey,mechanicId);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        System.out.println(this.mechanicId + ". Alerting manager that car is repaired.");
                    }
                    else
                    {   System.out.println(this.mechanicId + ". This should not happen!");
                        System.exit(1);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            /**
             *  Repair new car.
             * */
            case 2:
                try {
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
                } catch (RemoteException e) {
                    e.printStackTrace();
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
        try {
            this.lounge.finish();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(this.mechanicId + ". FINISHED!");

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
