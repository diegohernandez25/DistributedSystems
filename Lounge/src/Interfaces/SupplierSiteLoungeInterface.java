package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Supplier Site Interface dedicated to Lounge in order to terminate Supplier Site Program.
 * Note: Extends Remote.
 * */
public interface SupplierSiteLoungeInterface extends Remote
{
    /**
     * Terminates Supplier Site Server
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
