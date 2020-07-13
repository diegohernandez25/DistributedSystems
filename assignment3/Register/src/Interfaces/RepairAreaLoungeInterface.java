package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RepairAreaLoungeInterface extends Remote {
    /**
     * Terminate Outside World server.
     * */
    void finish() throws RemoteException;
}
