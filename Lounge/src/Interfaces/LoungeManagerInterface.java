package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Dedicated Lounge Interface for the possible functions called by the Manager.
 * Note: Extends Remote.
 * */
public interface LoungeManagerInterface extends Remote {
    /**
     *  Operation to attend customer. Can be for receive payment or to initiate the repair of a car.
     *  Manager invokes this method.
     *  @return success of the operation so the Mechanic can move on or not to the next operation.
     *  @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     */
    int attendCustomer() throws RemoteException;

    /**
     *  register refill of stock
     *  @param idType the type of Car Part
     *  @param numberParts number of Car Parts being refilled
     *  @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void registerStockRefill(int idType, int numberParts) throws RemoteException;

    /**
     * Manager checks if parts needs to be refilled
     * @return id of the part to refill. Returns -1 if no part is needed to refill
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int checksPartsRequest() throws RemoteException;

    /**
     * Checks if there are cars repaired
     * @return true customer fixed car keys empty
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    boolean isCustomerFixedCarKeysEmpty() throws RemoteException;

    /**
     * Gets key of a fixed car.
     * @return id of the key
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int getFixedCarKey() throws RemoteException;

    /**
     * Gets customer given the id of the key whom the customer belongs-
     * @param idKey id of the key.
     * @return the id of the customer
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int getCustomerFromKey(int idKey) throws RemoteException;

    /**
     * Make key ready to give back to customer.
     * @param idCustomer id of the customer.
     * @param idKey id of the key.
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void readyToDeliverKey(int idCustomer, int idKey) throws RemoteException;

    /**
     *  Get the requested number of a part
     *  @param partId ID of the part Car.
     *  @return number of parts requested.
     *  @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int requestedNumberPart(int partId) throws RemoteException;

    /**
     * Checks if all customer have been attended.
     * @return true - all services done to all customers for the dat. False - otherwise
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    boolean allDone() throws RemoteException;
}
