package Entities;

import Locations.GeneralRepInformation;
import Locations.Lounge;
import Locations.OutsideWorld;
import Locations.Park;
import Loggers.Logger;


public class Customer extends Thread{

    private static final String CLASS       = "Customer";
    private static final String NONE        = "None";

    /**
     *  Initialize GeneralRepInformation
     * */
    private GeneralRepInformation gri;

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
                    OutsideWorld outsideWorld, GeneralRepInformation gri)
    {;
        this.customerId = customerId;
        this.requiresCar = requiresCar;
        this.hasCar = true;
        this.nIter = nIter;
        this.lounge = lounge;
        this.park = park;
        this.car = car;
        this.outsideWorld = outsideWorld;
        this.repCar = null;
        this.gri = gri;
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


        //Key key = car.getKey();
        int key = car;
        livingNormalLife();                                         //Customers waits until car needs a fix.
        //park.parkCar(key.getKeyValue(),car);                      //Customer parks his/her car.
        park.parkCar(car);
        gri.setNumCarsParked(1);                                    //Logs Customer parked his car

        car = null;                                                 //Customer does not have a car anymore.
        gri.setCustomerVehicle(customerId, "-");             //Logs Customer doesn't have their car anymore

        lounge.enterCustomerQueue(customerId,false);        //Customer wants to request repair, so it
                                                                    //waits for attendance.
        gri.addCustomersQueue();                                    //Logs Customer entering queue

        lounge.giveManagerCarKey(key, customerId);                  //Customer gives key to manager.
        key = -1;
        int repKey = -1;
        if(wantsRental())                                           //If Customer needs a replacement car...
        {
            gri.setCustomerNeedsReplacement(customerId);            //Logs Customer needs a replacString.valueOfement vehicle
            gri.addCustomersReplacementQueue();                     //Logs Customer enters replacement car queue

            repKey = lounge.getReplacementCarKey(customerId);       //Customer waits for a key of a replacement car
                                                                    //and grabs it from the lounge.

            gri.removeCustomersReplacementQueue();                  //Logs Customer exits replacement car queue
            repCar = park.getCar(repKey);                              //Gets replacement car in the park.
            gri.setNumReplacementParked(-1);                        //Logs replacement car removed from park
            gri.setCustomerVehicle(customerId,                      //Logs Customer changing cars to a replacement car
                            "R"+String.valueOf(lounge.replacementCarKeysSize()));
        }
        else lounge.exitLounge(customerId);                         //...else, the Customer just leaves the Lounge.
        gri.removeCustomersQueue();                                 //Logs Customer exists queue

        outsideWorld.waitForRepair(customerId);                     //Customer waits in the outside world until the
                                                                    //his/her car is fixed.

        gri.setCustomerCarRepaired(customerId);                     //Log Customer car has been fixed

        if(repKey != -1)                                            //If customer has a replacement car...
        {
            park.parkCar(repKey);                                   //After the customer is alerted, the customer
            gri.setNumReplacementParked(1);                         //Logs replacement car parking in park
            repCar = null;                                          //parks the replacement car.
            gri.setCustomerVehicle(customerId, "-");         //Logs Customer doesn't have replacement car anymore
        }
        lounge.enterCustomerQueue(customerId,true);         //After the customer is alerted, he/she goes to
                                                                    //the lounge and waits for his/her turn to pay
                                                                    //for the service.
        gri.addCustomersQueue();                                    //Logs Customer entering queue

        if(repKey != -1)
        {
            lounge.returnReplacementCarKey(repKey);
                                                                    //Customer returns the key of the replacement
        }
        key = lounge.payForTheService(customerId);                  //Customer pays the service and gets the keys
                                                                    //of his/her car.
        gri.removeCustomersQueue();                                 //Logs Customer exists queue

        car = park.getCar(key);                                     //Customer gets his/her car from the park.
        gri.setNumCarsParked(-1);                                   //Logs Customer removed their car from park
        gri.setCustomerVehicle(customerId, String.valueOf(customerId)); //Logs Customer has their own car again

        Logger.log(CLASS,NONE,"Operation finished!",0,
                Logger.SUCCESS);

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
