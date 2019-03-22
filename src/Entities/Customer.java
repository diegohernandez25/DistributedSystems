package Entities;

import Locations.Lounge;
import Locations.OutsideWorld;
import Locations.Park;
import Loggers.Logger;


public class Customer extends Thread{

    private static final String CLASS       = "Customer";
    private static final String NONE        = "None";

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
     *      @serialField park
     * */
    private Park park;

    /**
     *  Outside World
     *
     *      @serialField park
     * */
    private OutsideWorld outsideWorld;

    /**
     *  Car that the user posses.
     *
     *      @serialField car
     * */
    private Integer car;

    /**
     *  Replacement car that the user posses.
     *
     *      @serialField repCar
     * */
    private Integer repCar;

    /**
     *  Instantiation of Customer Thread.
     *
     *      @param customerId identification of customer.
     *      @param requiresCar customer needs car.
     *      @param nIter Number of iteraction through the customer
     *                   thread life cycle
     * */
    public Customer(int customerId, boolean requiresCar, int nIter, Integer car, Lounge lounge, Park park,
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

    /**
     *      To String
     * */
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", requiresCar=" + requiresCar +
                ", hasCar=" + hasCar +
                ", clientState=" + clientState +
                ", carState=" + carState +
                ", nIter=" + nIter +
                ", lounge=" + lounge +
                ", park=" + park +
                ", outsideWorld=" + outsideWorld +
                ", car=" + car +
                ", repCar=" + repCar +
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
            //Key key = car.getKey();
            int key = car;
            livingNormalLife();                                         //Customers waits until car needs a fix.
            //park.parkCar(key.getKeyValue(),car);                        //Customer parks his/her car.
            park.parkCar(car);
            car = null;                                                 //Customer does not have a car anymore.
            lounge.enterCustomerQueue(customerId,false);                //Customer wants to request repair, so it
                                                                        //waits for attendance.
            lounge.giveManagerCarKey(key, customerId);                  //Customer gives key to manager.
            key = -1;
            int repKey = -1;
            if(wantsRental())                                           //If Customer needs a replacement car...
            {
                repKey = lounge.getReplacementCarKey(customerId);       //Customer waits for a key of a replacement car
                                                                        //and grabs it from the lounge.

                repCar = park.getCar(key);                //Gets replacement car in the park.
            }
            else lounge.exitLounge(customerId);                         //...else, the Customer just leaves the Lounge.
            outsideWorld.waitForRepair(customerId);                     //Customer waits in the outside world until the
                                                                        //his/her car is fixed.

            if(repKey != -1)                                          //If customer has a replacement car...
            {
                park.parkCar(repKey);                                   //After the customer is alerted, the customer
                repCar = null;                                          //parks the replacement car.
            }
            lounge.enterCustomerQueue(customerId,true);                 //After the customer is alerted, he/she goes to
                                                                        //the lounge and waits for his/her turn to pay
                                                                        //for the service.
            if(repKey != -1)
            {
                lounge.giveManagerCarKey(repKey,customerId);            //Customer returns the key of the replacement
                repKey = -1;                                            //car
            }
            key = lounge.payForTheService(customerId);                  //Customer pays the service and gets the keys
                                                                        //of his/her car.
            car = park.getCar(key);                                     //Customer gets his/her car from the park.
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
