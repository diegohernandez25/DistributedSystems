package Main;


import Interfaces.*;

import java.rmi.RemoteException;

public class Customer extends Thread{
    /**
     *  Customer identification.
     *
     * */
    private int customerId;

    /**
     *  Needs a car.
     * */
    private boolean requiresCar;

    /**
     *  Posses a Car.
     * */
    private boolean hasCar;

    /**
     *  Current state of the Customer.
     * */
    private int clientState;

    /**
     *  Current state of the Customer's Car.
     * */
    private int carState;


    /**
     *  Car that the user posses.
     * */
    private Integer car;

    /**
     *  Replacement car that the user posses.
     * */
    private Integer repCar;

    /**
     *  Park
     * */
    private ParkInterface park;

    /**
     *  OutsideWorld
     * */
    private OutsideWorldInterface outsideWorld;

    /**
     *  Lounge
     * */
    private LoungeInterface lounge;

    /**
     * Customer constructor
     * @param customerId Id of the customer.
     * @param requiresCar requires car flag.
     * @param car id of the car.
     * @param park park object.
     * @param outsideWorld outside world object.
     * @param lounge lounge object.
     * */
    public Customer(int customerId, boolean requiresCar, int car, ParkInterface park, OutsideWorldInterface outsideWorld
            , LoungeInterface lounge)
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
        try {
            park.parkCar(this.car, customerId, true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        this.car = null;
        System.out.println(customerId +". Car Parked.");
        System.out.println(customerId +". Entering Customer queue.");
        try {
            lounge.enterCustomerQueue(this.customerId,false);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(customerId +". Customer attended.");
        System.out.println(customerId +". Customer giving key to manager.");
        try {
            lounge.giveManagerCarKey(this.customerId,key);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(customerId +". Customer gave key to manager.");
        key = -1;
        int repKey = -1;
        if(this.requiresCar)
        {   System.out.println(customerId +". Customer wants rental key.");
            try {
                repKey = lounge.getReplacementCarKey(this.customerId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println(customerId +". Customer has rental key. Going to park");
            try {
                repCar = park.getCar(repKey, customerId, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.out.println(customerId +". Customer got car.");
        }
        else
        {   System.out.println(customerId +". Exiting lounge.");
            try {
                lounge.exitLounge(this.customerId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        System.out.println(customerId +". Waiting for repair.");
        try {
            outsideWorld.waitForRepair(this.customerId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(customerId +". Repair done getting back to the office.");
        if(repCar != -1)
        {   System.out.println(customerId +". Parking car");
            try {
                park.parkCar(repCar, customerId, true);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            repCar = -1;
            System.out.println(customerId +". Car parked.");
        }
        System.out.println(customerId +". Entering queue to pay.");
        try {
            lounge.enterCustomerQueue(this.customerId,true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if(repKey != -1)
        {   System.out.println(customerId +". Giving back replacement key.");
            try {
                lounge.returnReplacementCarKey(repKey,this.customerId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            repKey = -1;
        }
        System.out.println(customerId +". Paying for the service.");
        try {
            key = lounge.payForTheService(this.customerId);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(customerId +". Getting car back");
        try {
            car = park.getCar(key, customerId, true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println(customerId +". Got back car");
        System.out.println(customerId +". Operation finished!");
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
