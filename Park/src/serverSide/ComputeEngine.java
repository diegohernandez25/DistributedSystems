package serverSide;

import interfaces.Compute;
import interfaces.Task;

import java.rmi.RemoteException;

public class ComputeEngine implements Compute {
    /**
     *  Execution of remote code.
     *
     *    @param t code to be executed remotely
     *
     *    @return return value of the invocation of method execute of t
     *
     *    @throws RemoteException if the invocation of the remote method fails
     */
    @Override
    public Object executeTask (Task t)
    {
        return t.execute ();
    }

}
