package Entities;

public class Mechanic extends Thread {


    private String  MECHANIC = "Mechanic",
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
                        repairArea.concludeCarRepair(idCurrentCar,mechanicId);                  // Registers repair conclusion
                        // on Repair Area

                        idCurrentKey = idCurrentCar;
                        park.parkCar(idCurrentCar,mechanicId, false);
                        idCurrentCar=-1;// Leaves car at the park
                                "and give back customer key"+idCurrentKey,mechanicId,10);
                        lounge.alertManagerRepairDone(idCurrentKey,mechanicId);                 // Alerts manager that repair
                        idCurrentKey=-1;                                                                        // is done
                    }
                    else{
                        System.exit(1);
                    }
                    break;
                case 2:                                                                         //repair new car
                    if((idCurrentKey = lounge.getCarToRepairKey(mechanicId)) != -1)                        // If there are new cars to
                    // repair
                    {
                        if((idCurrentCar = park.getCar(idCurrentKey,mechanicId, false)) == -1)                    //Gets car at the park
                        {
                            System.exit(1);
                        }
                        int carPart = repairArea.checkCar(idCurrentCar,mechanicId);//FIXME             // Checks which part the car


                        // needs for its repair.
                                ,mechanicId,10);
                        if(!repairArea.repairCar(idCurrentCar,(carPart),mechanicId))
                        {
                            lounge.requestPart(carPart, repairArea.getMaxPartStock(carPart)     // Requests parts.
                                    ,mechanicId);
                            continue;                                                           // Re-starts cycle once again.
                        }
                                ,mechanicId,10);

                        fixCar();                                                               // Mechanic starts fixing the
                        // car
                        repairArea.concludeCarRepair(idCurrentCar,mechanicId);                  // Registers conclusion of
                        // repair at the repair Area
                        park.parkCar(idCurrentCar, mechanicId, false);
                        lounge.alertManagerRepairDone(idCurrentCar,mechanicId);                 // Alerts manager that the repair
                        // is done
                        continue;                                                               // Goes back to cycle
                    }
                    break;
                case 3:
                    break;
                case 4:
                    WORK = false;
                    break;
                default:
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
