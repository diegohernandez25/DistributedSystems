package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Park Interface dedicated to the Mechanic and Customer, specifying the Park functions that can be called by the Customer and Mechanic.
 * Note: extends Remote
 * */
public interface ParkCustomerMechanicInterface extends Remote {
    /**
     *  Park Car.
     *  @param carId ID of the car.
     *  @param id customer id.
     *  @param customerPark flag if it was a customer who parked the car
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void parkCar(Integer carId, int id, boolean customerPark) throws RemoteException;

    /**
     *  Gets Car.
     *  @param carId id of the car.
     *  @param id customer id.
     *  @param customerGet flag if it was a customer who got the car
     *  @return id of the car
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    Integer getCar(Integer carId, int id, boolean customerGet) throws RemoteException;

}
