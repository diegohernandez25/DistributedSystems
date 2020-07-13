package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Outside World interface dedicated to the customer entities, in order to specify the functions that can be called by Customers
 * */
public interface OutsideWorldCustomerInterface extends Remote {
    /**
     *  Customer waits until the manager alerts him/her about the end of the service.
     *  @param customerId ID of the waiting customer.
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void waitForRepair(Integer customerId) throws RemoteException;

}
