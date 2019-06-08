package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Repair Lounge Interface dedicated to the lounge in order to terminate Repair Lounge Program.
 * */
public interface RepairAreaLoungeInterface extends Remote {
    /**
     * Terminate Outside World server.
     * @throws RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
