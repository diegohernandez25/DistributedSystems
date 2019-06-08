package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Supplier Site Interface dedicated to the manager. Specifies the Supplier Site functions that can be used by the manager.
 * Note: Extends Remote.
 * */
public interface SupplierSiteManagerInterface extends Remote {

    /**
     *      Restocks car part. Always gets 3 of the part.
     *
     *      @param idType type of the part to replenish
     *      @param number requested number
     *
     *      @return number of parts that were restocked of the specific type of part
     *      @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int restockPart(int idType, int number) throws RemoteException;
}
