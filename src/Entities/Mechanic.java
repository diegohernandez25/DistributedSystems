package Entities;

import Locations.Lounge;
import Locations.Park;
import Loggers.Logger;
import Objects.CarPart;
import Objects.Key;

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

        readThePaper();                                                 // Mechanic waits for work
        Key repairCarKey = lounge.getVehicle(mechanicId);               // Get keys of car to be repaired
        Car repairCar = park.getCar(repairCarKey);                      // Get needer car

        //FIXME: how to check and handle needed car parts?
        CarPart neededPart = repairArea.getCarParts(repairCarKey);      // Get car part needed to repair the car
        if(neededPart != null)                                          // If car part is available
        {
            fixCar();                                                   // Fix the car
            park.parkCar(repairCar);
        }
        else
        {
            //FIXME: arg should be car or some car part?
            lounge.alertManager(repairCar)                              // Alert manager for missing car part
        }

    }

    /**
     *  Reading the paper while waiting for work (internal operation)
     * */
    private void readThePaper()
    {
        try
        {
            sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){ }
    }

    /**
     *  Fixing the car (internal operation)
     * */
    private CarPart fixCar()
    {
        try
        {
            sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){ }
    }

    @Override
    public String toString()
    {
        return "Mechanic{" +
                "id=" + mechanicId +
                '}';
    }
}
