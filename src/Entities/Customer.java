package Entities;

import Locations.Lounge;
import Locations.Park;
import Objects.CustomerCar;


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

    private CustomerCar car;

    /**
     *  Instantiation of Customer Thread.
     *
     *      @param customerId identification of customer.
     *      @param requiresCar customer needs car.
     *      @param nIter Number of iteraction through the customer
     *                   thread life cycle
     * */
    public Customer(int customerId, boolean requiresCar, int nIter, CustomerCar car, Lounge lounge, Park park)
    {
        this.customerId = customerId;
        this.requiresCar = requiresCar;
        this.hasCar = true;
        this.nIter = nIter;
        this.lounge = lounge;
        this.park = park;
        this.car = car;
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
        //TODO: Customer's life cycle
        /*
        int i = 0;
        for(; i < nIter; i++)
        {
            livingNormalLife();
            park.parkCar(car.getKey().getKeyValue(),car);
            car = null;
            lounge.enterCustomerQueue(customerId, false);
            if(wantsRental())
            {
                Key rentalCarKey = lounge.getReplacementCarKey(customerId);
                ReplacementCar rep_car = (ReplacementCar) park.getCar(rentalCarKey.getKeyValue());
                hasCar = true;
                //outside.backToWorldWithRepCar()
            }
            else
            {
                lounge.exitLounge(customerId);
            }
        }
        */
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
