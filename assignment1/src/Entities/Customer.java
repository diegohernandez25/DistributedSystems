package Entities;

import Interfaces.*;
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
     *  Lounge
     *
     *      @serialField lounge
     */
    private CustomerLounge lounge;

    /**
     *  Parking Lot
     *
     *      @serialField park
     * */
    private CustomerPark park;

    /**
     *  Outside World
     *
     *      @serialField park
     * */
    private CustomerOW outsideWorld;

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
     *      @param car car driven by customer
     *      @param lounge used Lounge
     *      @param park used Park
     *      @param outsideWorld used Outside World
     *
     * */
    public Customer(int customerId, boolean requiresCar, int car, CustomerLounge lounge, CustomerPark park, CustomerOW outsideWorld)
    {
        this.customerId = customerId;
        this.requiresCar = requiresCar;
        this.hasCar = true;
        this.lounge = lounge;
        this.park = park;
        this.car = car;
        this.outsideWorld = outsideWorld;
        this.repCar = -1;
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
        String FUNCTION = "run";
        Logger.log(CLASS,CLASS,FUNCTION,"Requires key: "+requiresCar,customerId,Logger.WARNING);
        //Key key = car.getKey();
        int key = car;
        Logger.log(CLASS,CLASS,FUNCTION,"Living Normal Life",customerId,10);
        livingNormalLife();                                         //Customers waits until car needs a fix.
        //park.parkCar(key.getKeyValue(),car);                      //Customer parks his/her car.
        Logger.log(CLASS,CLASS,FUNCTION,"Parking car: "+car,customerId,10);
        park.parkCar(car,customerId, true);
        car = null;                                                 //Customer does not have a car anymore.
        Logger.log(CLASS,CLASS,FUNCTION,"Entering queue",customerId,10);
        lounge.enterCustomerQueue(customerId,false);        //Customer wants to request repair, so it
                                                                    //waits for attendance.
        Logger.log(CLASS,CLASS,FUNCTION,"Giving manager the key",customerId,10);
        lounge.giveManagerCarKey(key, customerId);                  //Customer gives key to manager.
        key = -1;
        int repKey = -1;
        if(wantsRental())                                           //If Customer needs a replacement car...
        {
            Logger.log(CLASS,CLASS,FUNCTION,"Customer wants rental key",customerId,10);
            repKey = lounge.getReplacementCarKey(customerId);       //Customer waits for a key of a replacement car
                                                                    //and grabs it from the lounge.
            Logger.log(CLASS,CLASS,FUNCTION,"Given rental key "+repKey,customerId,10);
            repCar = park.getCar(repKey,customerId, true);                //Gets replacement car in the park.
            //repKey = repCar;
            Logger.log(CLASS,CLASS,FUNCTION,"Got rental car "+repCar,customerId,10);
        }
        else lounge.exitLounge(customerId);                         //...else, the Customer just leaves the Lounge.

        Logger.log(CLASS,CLASS,FUNCTION,"Waiting for repair ",customerId,Logger.WARNING);
        outsideWorld.waitForRepair(customerId);                     //Customer waits in the outside world until the
                                                                    //his/her car is fixed.

        Logger.log(CLASS,CLASS,FUNCTION,"repair done getting to office ",customerId,10);
        if(repCar != -1)                                            //If customer has a replacement car...
        {   Logger.log(CLASS,CLASS,FUNCTION,"Parking replacement car "+repCar,customerId,10);
            park.parkCar(repCar,customerId, true);                        //After the customer is alerted, the customer
            repCar = -1;                                            //parks the replacement car.
        }
        Logger.log(CLASS,CLASS,FUNCTION,"Enter queue to pay ",customerId,10);
        lounge.enterCustomerQueue(customerId,true);         //After the customer is alerted, he/she goes to
                                                                    //the lounge and waits for his/her turn to pay
                                                                    //for the service.
        if(repKey != -1)
        {   Logger.log(CLASS,CLASS,FUNCTION,"Giving back replacement key "+repKey,customerId,10);
            lounge.returnReplacementCarKey(repKey,customerId);
            repKey=-1;
                                                                    //Customer returns the key of the replacement
        }
        Logger.log(CLASS,CLASS,FUNCTION,"Paying for the service",customerId,10);
        key = lounge.payForTheService(customerId);                  //Customer pays the service and gets the keys
                                                                    //of his/her car.
        Logger.log(CLASS,CLASS,FUNCTION,"Got back car Key "+key,customerId,10);
        car = park.getCar(key,customerId, true);                          //Customer gets his/her car from the park.
        Logger.log(CLASS,CLASS,FUNCTION,"Got back car "+car,customerId,10);
        Logger.log(CLASS,NONE,"Operation finished!",0,
                Logger.SUCCESS);
        //System.exit(1); //FIXME: DELETE
        Logger.log(CLASS,CLASS,"run","Customer done.",customerId,Logger.SUCCESS);

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

    /**fixed
     *  Checks if Customer needs rental
     *  @return (true/false) if the Customer needs the rental
     * */
    private boolean wantsRental(){ return requiresCar; }

}
