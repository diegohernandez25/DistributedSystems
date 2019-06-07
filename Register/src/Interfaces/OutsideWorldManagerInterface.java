package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OutsideWorldManagerInterface extends Remote {


    /**
     *  Managers alerts customer that car is fixed and it can be retrieved;
     *  @param customerId ID of the customer to alert .
     * */
    void alertCustomer(Integer customerId) throws RemoteException;

    /**
     *  Alert remaining customer whom hasn't been alerted (because they haven't arrived sooner at the outside world)
     * */
    void alertRemainingCustomers() throws RemoteException;

    /**
     *  Checks if there are user's expected to arrive at the outside world.
     *  @return true/false if customer is already in the Outside World or not
     * */
    boolean customersNotYetAtOutsideWorldisEmpty() throws RemoteException;
}
