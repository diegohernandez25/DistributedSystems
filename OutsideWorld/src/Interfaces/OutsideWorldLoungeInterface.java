package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface OutsideWorldLoungeInterface extends Remote {

    /**
     * Terminate Outside World server.
     * */
    void finish() throws RemoteException;
}
