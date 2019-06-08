package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Outside World Interface dedicated to the Manager, specify the Outside World functions that can be called by the Manager.
 * */
public interface OutsideWorldManagerInterface extends Remote {


    /**
     *  Managers alerts customer that car is fixed and it can be retrieved;
     *  @param customerId ID of the customer to alert .
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void alertCustomer(Integer customerId) throws RemoteException;

    /**
     *  Alert remaining customer whom hasn't been alerted (because they haven't arrived sooner at the outside world)
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void alertRemainingCustomers() throws RemoteException;

    /**
     *  Checks if there are user's expected to arrive at the outside world.
     *  @return true/false if customer is already in the Outside World or not
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    boolean customersNotYetAtOutsideWorldisEmpty() throws RemoteException;
}
