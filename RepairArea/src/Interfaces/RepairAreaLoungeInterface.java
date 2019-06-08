package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Repair Area Interface dedicated to the Lounge so the Lounge can terminate Repair Area Program service.
 * */
public interface RepairAreaLoungeInterface extends Remote {
    /**
     * Terminate Outside World server.
     * @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void finish() throws RemoteException;
}
