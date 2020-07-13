package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  Interface for Repair Area for the use of the General Repository
 *  Note: Extends Remote.
 * */
public interface GriRA extends Remote {

    /**
     *  Set if a Customer's car is repaired. Default value is "F", this sets it to "T"
     *  @param customer the Customer whose car has been repaired
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void setCustomerCarRepaired(int customer) throws RemoteException;

    /**
     *  Increments the number of cars that have been repaired
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void setNumCarsRepaired() throws RemoteException;

    /**
     *  Increments the number of requests the Manager has made to the Repair Area
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void setNumPostJobs() throws RemoteException;

    /**
     *  Adds the number of parts available in stock (restocked part)
     *  @param part the part which needs to be set
     *  @param num the number of the specified part available
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void addNumPartAvailable(int part, int num) throws RemoteException;

    /**
     *  Removes one of the parts available in stock (used part)
     *  @param part the part which was used
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void removeNumPartAvailable(int part) throws RemoteException;

    /**
     *  Sets the number of cars waiting for a specific part
     *  @param part the part the cars are waiting for
     *  @param num the number of cars waiting for the part. +1 if new car waiting for part, -1 if car no longer needs part
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void setNumCarWaitingPart(int part, int num) throws RemoteException;
}
