package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Dedicated Lounge Interface for the possible functions called by the Customers.
 * Note: Extends Remote.
 * */
public interface LoungeCustomerInterface extends Remote {

    /**
     *  Customer enters queue to be attended by the Manager.
     *      @param customerId Id of the customer to be attended
     *      @param payment type of attendance. (true/false) Pay for repair/Request repair.
     *      @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void enterCustomerQueue(int customerId, boolean payment) throws RemoteException;

    /**
     * Get replacement car key.
     * @param customerId ID of the client who needs the replacement car.
     * @return the key of the replacement car.
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int getReplacementCarKey(int customerId) throws RemoteException;

    /**
     *  Return Replacement car key.
     *  Customer with the need of a replacement car invokes this method.
     *
     *      @param key Key of the replacement car key.
     *      @param customerId ID of the current Customer returning the replacement car key
     *      @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     *
     * */
    void returnReplacementCarKey(int key, int customerId) throws RemoteException;

    /**
     * Exit Lounge
     * @param customerId Id of the customer who will exit the lounge
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void exitLounge(int customerId) throws RemoteException;

    /**
     * Customer gives Manager his/hers car key.
     * @param key - Customer's car key.
     * @param customerId current Customer giving their car key
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     */
    void giveManagerCarKey(int customerId, int key) throws RemoteException;

    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *  @param customerId - ID of the customer.
     *  @return the Customer's car key.
     *  @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int payForTheService(int customerId) throws RemoteException;
}
