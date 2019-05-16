package serverSide;

import interfaces.Compute;
import interfaces.Register;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 *  This data type instantiates and registers a remote object that will run mobile code.
 *  Communication is based in Java RMI.
 */
public class ServerComputeEngine {
    public static int PORT= 3870;
    public static String SERVER_NAME = "localhost";
    /**
     *  Main task.
     */
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        String rmiRegHostName = SERVER_NAME;
        int rmiRegPortNumb = PORT;

        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        System.out.println("Security manager was installed!");

        ComputeEngine engine = new ComputeEngine();
        Compute engineStub = null;
        int listeningPort = 22001;

        try
        {   engineStub = (Compute) UnicastRemoteObject.exportObject((Remote) engine, listeningPort);
        }
        catch (RemoteException e)
        {   System.out.println("ComputeEngine stub generation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println("Stub was generated!");

        String nameEntryBase = "RegisterHandler";
        String nameEntryObject = "Compute";
        Registry registry = null;
        Register reg = null;

        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        {   System.out.println("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println("RMI registry was created!");
        try
        { reg = (Register) registry.lookup (nameEntryBase);
        }
        catch (RemoteException e)
        {   System.out.println("RegisterRemoteObject lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        } catch (NotBoundException e) {
            System.out.println("RegisterRemoteObject not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        try
        { reg.bind (nameEntryObject, (Remote) engineStub);
        }
        catch (RemoteException e)
        { System.out.println("ComputeEngine registration exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (AlreadyBoundException e)
        {   System.out.println("ComputeEngine already bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        System.out.println("ComputeEngine object was registered!");


    }

}
