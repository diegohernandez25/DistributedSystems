package Main;

import Locals.Lounge;
import Locals.OutsideWorld;
import Locals.Park;
import Interfaces.*;

public class Customer extends Thread{
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
     *  Park
     * */
    private CustomerPark park;

    /**
     *  OutsideWorld
     * */
    private CustomerOW outsideWorld;

    /**
     *  Lounge
     * */
    private CustomerLounge lounge;

    /**
     * Customer constructor
     * @param customerId    - Id of the customer.
     * @param requiresCar   - requires car flag.
     * @param car           - id of the car.
     * */
    public Customer(int customerId, boolean requiresCar, int car, CustomerPark park, CustomerOW outsideWorld, CustomerLounge lounge)
    {   this.customerId     = customerId;
        this.requiresCar    = requiresCar;
        this.hasCar         = true;
        this.car            = car;
        this.repCar         = -1;
        this.park           = park;
        this.outsideWorld   = outsideWorld;
        this.lounge         = lounge;
    }

    /**
     *  Life cycle of the customer thread
     * */
    @Override
    public void run()
    {   int key = car;
        livingNormalLife();
        System.out.println(customerId +". Going to park car.");
        park.parkCar(this.car, customerId, true);
        this.car = null;
        System.out.println(customerId +". Car Parked.");
        System.out.println(customerId +". Entering Customer queue.");
        lounge.enterCustomerQueue(this.customerId,false);
        System.out.println(customerId +". Customer attended.");
        System.out.println(customerId +". Customer giving key to manager.");
        lounge.giveManagerCarKey(key, this.customerId);
        System.out.println(customerId +". Customer gave key to manager.");
        key = -1;
        int repKey = -1;
        if(this.requiresCar)
        {   System.out.println(customerId +". Customer wants rental key.");
            repKey = lounge.getReplacementCarKey(this.customerId);
            System.out.println(customerId +". Customer has rental key. Going to park");
            repCar = park.getCar(repKey, customerId, true);
            System.out.println(customerId +". Customer got car.");
        }
        else
        {   System.out.println(customerId +". Exiting lounge.");
            lounge.exitLounge(this.customerId);
        }
        System.out.println(customerId +". Waiting for repair.");
        outsideWorld.waitForRepair(this.customerId);
        System.out.println(customerId +". Repair done getting back to the office.");
        if(repCar != -1)
        {   System.out.println(customerId +". Parking car");
            park.parkCar(repCar, customerId, true);
            repCar = -1;
            System.out.println(customerId +". Car parked.");
        }
        System.out.println(customerId +". Entering queue to pay.");
        lounge.enterCustomerQueue(this.customerId,true);
        if(repKey != -1)
        {   System.out.println(customerId +". Giving back replacement key.");
            lounge.returnReplacementCarKey(repKey,this.customerId);
            repKey = -1;
        }
        System.out.println(customerId +". Paying for the service.");
        key = lounge.payForTheService(this.customerId);
        System.out.println(customerId +". Getting car back");
        car = park.getCar(key, customerId, true);
        System.out.println(customerId +"Got back car");
        System.out.println(customerId +"Operation finished!");
        //System.exit(1);
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

}
