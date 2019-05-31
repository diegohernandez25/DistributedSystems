package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ParkInterface extends Remote{

    /**
     *  Park Car.
     *  @param carId ID of the car.
     *  @param id customer id.
     *  @param customerPark flag if it was a customer who parked the car
     * */
    void parkCar(Integer carId, int id, boolean customerPark) throws RemoteException;

    /**
     *  Gets Car.
     *  @param carId id of the car.
     *  @param id customer id.
     *  @param customerGet flag if it was a customer who got the car
     *  @return id of the car
     * */
    Integer getCar(Integer carId, int id, boolean customerGet) throws RemoteException;

    /**
     * Terminates Park Information Server
     * */
    void finish() throws RemoteException;
}
