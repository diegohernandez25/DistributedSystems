package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Repair Area Interface dedicated to Manager Entity, specifying the Repair Area functions that can be called by the Manager.
 * Note: Extends Remote
 * */
public interface RepairAreaManagerInterface extends Remote {

    /**
     *      Refill car Part stock
     *      @param idPart   ID of car part.
     *      @param quantity number of car parts to refill
     *      @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void refillCarPartStock(int idPart, int quantity) throws RemoteException;

    /**
     * Alerts that a new car needs to be checked.
     * @param carID the ID of the car that needs to be repaired
     * @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void postJob(int carID) throws RemoteException;

    /**
     *  Sends the Mechanics home
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void sendHome() throws RemoteException;
}
