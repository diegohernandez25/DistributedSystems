package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SupplierSiteLoungeInterface extends Remote
{
    /**
     * Terminates Supplier Site Server
     * */
    void finish() throws RemoteException;
}
