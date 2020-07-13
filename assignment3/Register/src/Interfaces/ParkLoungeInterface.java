package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ParkLoungeInterface extends Remote {
    /**
     * Terminates Park Information Server
     * */
    void finish() throws RemoteException;
}
