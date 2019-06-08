package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Park interface dedicated to the lounge in order to terminate Park program.
 * */
public interface ParkLoungeInterface extends Remote {
    /**
     * Terminates Park Information Server
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
