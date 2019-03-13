package Entities;

import Locations.Lounge;
import Locations.OutsideWorld;
import Locations.Park;
import Loggers.Logger;
import Objects.Car;
import Objects.CustomerCar;
import Objects.Key;
import Objects.ReplacementCar;


public class Customer extends Thread{

    private static final String CLASS       = "Customer",
                                CUSTOMER    = "Customer",
                                NONE        = "None";

    /**
     *  Customer identification.
     *
     *      @serialField customerId
     *
     * */
    private int customerId;

    /**
     *  Needs a car.
     *
     *      @serialField  requiresCar
     *
     * */
    private boolean requiresCar;

    /**
     *  Posses a Car.
     *
     *      @serialField hasCar
     *
     * */
    private boolean hasCar;

    /**
     *  Current state of the Customer.
     *
     *      @serialField clientState;
     * */
    private int clientState;

    /**
     *  Current state of the Customer's Car.
     *
     *      @serialField clientState;
     * */
    private int carState;

    /**
     *  Number of iteractions of the customer life cycle.
     *
     *      @serialField nIter
     * */
    private int nIter = 0;

    /**
     *  Lounge
     *
     *      @serialField lounge
     */
    private Lounge lounge;

    /**
     *  Parking Lot
     *
     *      @serialized park
     * */
    private Park park;

    /**
     *  Outside World
     *
     *      @serialized park
     * */
    private OutsideWorld outsideWorld;

    /**
     *  Car that the user posses.
     *
     *      @serialized car
     * */
    private Car car;

    /**
     *  Replacement car that the user posses.
     *
     *      @serialized repCar
     * */
    private Car repCar;

    /**
     *  Instantiation of Customer Thread.
     *
     *      @param customerId identification of customer.
     *      @param requiresCar customer needs car.
     *      @param nIter Number of iteraction through the customer
     *                   thread life cycle
     * */
    public Customer(int customerId, boolean requiresCar, int nIter, CustomerCar car, Lounge lounge, Park park,
                    OutsideWorld outsideWorld)
    {
        this.customerId = customerId;
        this.requiresCar = requiresCar;
        this.hasCar = true;
        this.nIter = nIter;
        this.lounge = lounge;
        this.park = park;
        this.car = car;
        this.outsideWorld = outsideWorld;
        this.repCar = null;
    }

    @Override
    public String toString()
    {
        return "Customer{" +
                "id=" + customerId +
                ", requiresCar=" + requiresCar +
                ", hasCar=" + hasCar +
                '}';
    }

    /**
     *  Life cycle of the customer thread
     * */
    @Override
    public void run()
    {

        int i = 0;
        for(; i < nIter; i++)
        {
            Key key = car.getKey();
            livingNormalLife();                                         //Customers waits until car needs a fix.
            park.parkCar(key.getKeyValue(),car);                        //Customer parks his/her car.
            car = null;                                                 //Customer does not have a car anymore.
            lounge.enterCustomerQueue(customerId,false);        //Customer wants to request repair, so it
                                                                        //waits for attendance.
            lounge.giveManagerCarKey(key, customerId);                  //Customer gives key to manager.
            key = null;
            Key repKey = null;
            if(wantsRental())                                           //If Customer needs a replacement car...
            {
                repKey = lounge.getReplacementCarKey(customerId);       //Customer waits for a key of a replacement car
                                                                        //and grabs it from the lounge.

                //TODO This case needs more analysis!
                if(repKey == null)                                      //If car is already fixed...
                {
                    lounge.enterCustomerQueue(customerId,true); //After the customer is alerted, he/she goes to
                                                                        //the lounge and waits for his/her turn to pay
                                                                        //for the service.
                    car = park.getCar(key.getKeyValue());               //Customer returns the key of the replacement
                                                                        // car
                    Logger.log(CLASS,NONE,"Operation finished!",
                            0,Logger.SUCCESS);
                    continue;                                           //Finish interaction. FIXME: Does the professor likes this approach.
                }
                repCar = park.getCar(key.getKeyValue());                //Gets replacement car in the park.
            }
            else lounge.exitLounge(customerId);                         //...else, the Customer just leaves the Lounge.
            outsideWorld.waitForRepair(customerId);                     //Customer waits in the outside world until the
                                                                        //his/her car is fixed.

            if(repKey != null)                                          //If customer has a replacement car...
            {
                park.parkCar(repKey.getKeyValue(), repCar);             //After the customer is alerted, the customer
                repCar = null;                                          //parks the replacement car.
            }
            lounge.enterCustomerQueue(customerId,true);         //After the customer is alerted, he/she goes to
                                                                        //the lounge and waits for his/her turn to pay
                                                                        //for the service.
            if(repKey != null)
            {
                lounge.giveManagerCarKey(repKey,customerId);            //Customer returns the key of the replacement
                repKey = null;                                          //car
            }
            key = lounge.payForTheService(customerId);
            car = park.getCar(key.getKeyValue());
            Logger.log(CLASS,NONE,"Operation finished!",0,
                    Logger.SUCCESS);
        }
    }

    /**
     *  Living a normal life (internal operation)
     * */

    private void livingNormalLife()
    {
        try
        { sleep((long) (1 + 40 * Math.random()));
        }
        catch (InterruptedException e){}
    }

    private boolean wantsRental(){ return requiresCar; }

}
