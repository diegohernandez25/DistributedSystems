package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *  Interface for Lounge for the use of the General Repository
 * */
public interface GriLounge extends Remote {

    /**
     *  Set the state of the Manager
     *  @param state the current state of the Manager
     * */
    void setStateManager(int state) throws RemoteException;

    /**
     *  Set the state of a Customer
     *  @param customer the Customer that is going to change state
     *  @param state the current state of specified Customer
     * */
    void setStateCustomer(int customer, int state) throws RemoteException;

    /**
     *  Set the state of a Mechanic
     *  @param mechanic the Mechanic that is going to change state
     *  @param state the current state of specified Mechanic
     * */
    void setStateMechanic(int mechanic, int state) throws RemoteException;

    /**
     *  Set if a Customer needs a replacement car. Default value is "F", this sets it to "T"
     *  @param customer the Customer who needs a replacement car
     * */
    void setCustomerNeedsReplacement(int customer) throws RemoteException;

    /**
     *  Adds a Customer to the queue
     * */
    void addCustomersQueue() throws RemoteException;

    /**
     *  Removes a Customer from the queue
     * */
    void removeCustomersQueue() throws RemoteException;

    /**
     *  Adds a Customer to the replacement car queue
     * */
    void addCustomersReplacementQueue() throws RemoteException;

    /**
     *  Removes a Customer from the replacement car queue
     * */
    void removeCustomersReplacementQueue() throws RemoteException;

    /**
     *  Set the number of replacement cars parked
     *  @param num the number of replacement cars parked
     * */
    void setNumReplacementParked(int num) throws RemoteException;

    /**
     *  Sets the number of cars waiting for a specific part
     *  @param part the part the cars are waiting for
     *  @param num the number of cars waiting for the part. +1 if new car waiting for part, -1 if car no longer needs part
     * */
    void setNumCarWaitingPart(int part, int num) throws RemoteException;

    /**
     *  Sets a flag signaling the Manager has been adviced that a specific part is missing
     *  @param part the part which needs to be set the flag
     *  @param flag the flag signaling if Manager has been adviced (T/F)
     * */
    void setFlagMissingPart(int part, String flag) throws RemoteException;

    /**
     * Terminates GeneralRepInformation server
     * */
    void finish() throws RemoteException;
}
