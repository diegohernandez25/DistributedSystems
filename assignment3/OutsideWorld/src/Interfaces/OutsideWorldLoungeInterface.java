package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Outside World interface dedicated to Lounge in order to specify the function to terminate the Outside World Service.
 * */
public interface OutsideWorldLoungeInterface extends Remote {

    /**
     * Terminate Outside World server.
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
