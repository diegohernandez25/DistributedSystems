package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Supplier Site Interface dedicated to the Lounge so it can terminate Supplier Site Service program.
 * */
public interface SupplierSiteLoungeInterface extends Remote
{
    /**
     * Terminates Supplier Site Server
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
