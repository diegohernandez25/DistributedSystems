package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Park Interface dedicated to the Lounge, so the Lounge can terminate Park program service.
 * */
public interface ParkLoungeInterface extends Remote {
    /**
     * Terminates Park Information Server
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
