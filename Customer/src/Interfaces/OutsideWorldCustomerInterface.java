package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OutsideWorldCustomerInterface extends Remote {
    /**
     *  Customer waits until the manager alerts him/her about the end of the service.
     *  @param customerId ID of the waiting customer.
     * */
    void waitForRepair(Integer customerId) throws RemoteException;

}
