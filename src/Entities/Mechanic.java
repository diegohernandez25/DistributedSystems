package Entities;

import Locations.Lounge;
import Locations.Park;

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
     *  Queue for cars waiting to be repaired
     * */
    private LinkedHashMap<Car, CarPart> carsToBeRepairedQueue;

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
        Car car = carsToBeRepairedQueue.entrySet().iterator().next();                                       // First car waiting for repair parts
        CarPart carPart = carsToBeRepairedQueue.get(car);                                                   // Car part from the first car

        while(lounge.isCarKeysRepairQueueEmpty() && !repairArea.checkCarPartInStock(carPart))               // Mechanic waits until needed parts available
        {                                                                                                   // or any cars are available to be repaired
            wait();
        }

        // Priority on car part now available, since this car is in front of the queue
        if(repairArea.checkCarPartInStock(carPart))                                                         // If Mechanic was awoken because a
        {                                                                                                   // needed car part is already available
            repairArea.getCarPart(carPart, mechanicId);                                                     // Get the available part
            fixCar();                                                                                       // Fix the car
            park.parkCar(car);                                                                              // Park the repaired car in park
        }

        if(!lounge.isCarKeysRepairQueueEmpty())                                                             // If Mechanic was awoken because there is
        {                                                                                                   // another car waiting to be repaired
            Key repairCarKey = lounge.getVehicle(mechanicId);                                               // Get keys of car to be repaired
            Car repairCar = park.getCar(repairCarKey);                                                      // Get respective car
            CarPart neededPart = repairArea.checkCarPartNeeded(mechanicId);                                 // Check which part needs repair

            if(repairArea.checkCarPartInStock(neededPart))                                                  // If needed part is available in stock
            {
                repairArea.getCarPart(neededPart, mechanicId);                                              // Get required part from stock
                fixCar();                                                                                   // Fix the car
                park.parkCar(repairCar);                                                                    // Park repaired car in park
            }
            else                                                                                            // If part is not available
            {
                carsToBeRepairedQueue.put(repairCar, neededPart);                                           // Add to queue of cars to be repaired
                lounge.alertManager(neededPart, mechanicId);                                                // Alerts the manager for missing car part
            }
        }

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
