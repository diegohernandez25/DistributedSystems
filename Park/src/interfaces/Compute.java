package interfaces;

import java.rmi.RemoteException;


public interface Compute {
    Object executeTask (Task t) throws RemoteException;
}
