package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Outside World Interface used by the Lounge in order to have the functionality to terminate Outside World program.
 * */
public interface OutsideWorldLoungeInterface extends Remote {

    /**
     * Terminate Outside World server.
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
