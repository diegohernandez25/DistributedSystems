package Main;
import java.rmi.NotBoundException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.AccessException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.*;

import Interfaces.Register;

/**
 *  This data type instantiates and registers a remote object that enables the registration of other remote objects
 *  located in the same or other processing nodes in the local registry service.
 *  Communication is based in Java RMI.
 */
public class ServerRegisterRemoteObject
{
    /**
     *  Main task.
     */

    public static void main(String[] args)
    {
        /* get location of the registry service */

        String rmiRegHostName;
        int rmiRegPortNumb;

        //rmiRegHostName = Parameters.REGISTRY_HOST;
        rmiRegHostName = Parameters.LOCALHOST;
        rmiRegPortNumb = Parameters.REGISTRY_PORT;

        /* create and install the security manager */

        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        System.out.println("Security manager was installed!");

        /* instantiate a registration remote object and generate a stub for it */

        RegisterRemoteObject regEngine = new RegisterRemoteObject (rmiRegHostName, rmiRegPortNumb);
        Register regEngineStub = null;
        int listeningPort = Parameters.SERVER_REGISTRY_PORT; /* it should be set accordingly in each case */

        try
        { regEngineStub = (Register) UnicastRemoteObject.exportObject (regEngine, listeningPort);
        }
        catch (RemoteException e)
        {   System.out.println("RegisterRemoteObject stub generation exception: " + e.getMessage ());
            System.exit (1);
        }
        System.out.println("Stub was generated!");

        /* register it with the local registry service */

        String nameEntry = Parameters.REGISTRY_NAME_ENTRY;
        Registry registry = null;

        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        {   System.out.println("RMI registry creation exception: " + e.getMessage ());
            System.exit (1);
        }
        System.out.println("RMI registry was created!");

        try
        { registry.rebind (nameEntry, regEngineStub);
        }
        catch (RemoteException e)
        {   System.out.println("RegisterRemoteObject remote exception on registration: " + e.getMessage ());
            System.exit (1);
        }
        System.out.println("RegisterRemoteObject object was registered!");

        while (!regEngine.finish)
        {
            try {
                synchronized (regEngine){
                    regEngine.wait();
                    System.out.println("Waked up!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        System.out.println("Registry finished execution.");

        /* Unregister shared region */
        try
        { registry.unbind (nameEntry);
        }
        catch (RemoteException e)
        {   System.out.println("ERROR: RemoteException");
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException ex) {
            System.out.println("ERROR: NotBoundException ");
            ex.printStackTrace ();
            System.exit (1);
        }
        System.out.println("RegisterRemoteObject object was unregistered!");

        try
        { UnicastRemoteObject.unexportObject (regEngine, false);
        }
        catch (RemoteException e)
        {   System.out.println("ERROR: RemoteException ");
            e.printStackTrace ();
            System.exit (1);
        }

        System.out.println("RegisterRemoteObject object was unexported successfully!");

    }
}
