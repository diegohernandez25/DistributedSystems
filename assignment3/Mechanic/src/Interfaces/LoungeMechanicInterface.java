package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Dedicated Lounge Interface for the possible functions called by the Mechanics.
 * Note: Extends Remote.
 * */
public interface LoungeMechanicInterface extends Remote {
    /**
     *  Mechanic gets keys of car to be repaired
     *  @param mechanicId ID of the Mechanic
     *  @return key of the car to repair
     *  @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     *  */
    int getCarToRepairKey(int mechanicId) throws RemoteException;

    /**
     *      Mechanic asks for a type of car parts for the repair
     *      @param idType       the id of the part to refill stock
     *      @param number       the number of stock needed
     *      @param mechanicId   the id of the mechanic
     *      @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void requestPart(int idType, int number, int mechanicId) throws RemoteException;

    /**
     * Mechanic return key of the repaired car
     * @param idKey         the id of the key (= idCar)
     * @param mechanicId    the id of the mechanic.
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void alertManagerRepairDone(int idKey, int mechanicId) throws RemoteException;

    /**
     * Trigger servers termination.
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
