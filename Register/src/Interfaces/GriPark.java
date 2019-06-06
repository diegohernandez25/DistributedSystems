package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  Interface for Park for the use of the General Repository
 * */
public interface GriPark extends Remote {

    /**
     *  Set the vehicle currently used by a Customer
     *  @param customer the Customer who is changing vehicles
     *  @param vehicle the vehicle that the specified Customer is changing to. Values can be: own car - customer ID; replacement car - R# (where # is the number of the replacement car); none - '-'
     * */
    void setCustomerVehicle(int customer, String vehicle) throws RemoteException;

    /**
     *  Set the number of Customer cars parked
     *  @param num the number of cars parked
     * */
    void setNumCarsParked(int num) throws RemoteException;

    /**
     *  Set the number of replacement cars parked
     *  @param num the number of replacement cars parked
     * */
    void setNumReplacementParked(int num) throws RemoteException;
}
