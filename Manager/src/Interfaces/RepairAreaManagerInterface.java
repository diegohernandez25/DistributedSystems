package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RepairAreaManagerInterface extends Remote {

    /**
     *      Refill car Part stock
     *      @param idPart   ID of car part.
     *      @param quantity number of car parts to refill
     * */
    void refillCarPartStock(int idPart, int quantity) throws RemoteException;

    /**
     * Alerts that a new car needs to be checked.
     * @param carID the ID of the car that needs to be repaired
     * */
    void postJob(int carID) throws RemoteException;

    /**
     *  Sends the Mechanics home
     * */
    void sendHome() throws RemoteException;
}
