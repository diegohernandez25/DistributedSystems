package Main;

import Interfaces.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {
    /**
     * Number of car parts.
     * */
    //TODO: public static final int numPartTypes = Parameters.NUM_CAR_TYPES;
    public static final int numPartTypes = Parameters.NUM_CAR_TYPES;

    /**
     * Main.Main
     * @param args  arguments.
     * */
    public static void main(String[] args)
    {
        String rmiRegHostName = Parameters.REGISTRY_HOST;
        //String rmiRegHostName = Parameters.LOCALHOST;
        int rmiRegPortNumb = Parameters.REGISTRY_PORT;

        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        System.out.println("Security manager was installed!");
        Registry registry = null;
        Register register = null;
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Got Registry!");
        GeneralRepInterface griSS = null;

        // General Repository Information
        try {
            griSS = (GeneralRepInterface) registry.lookup(Parameters.GENERALREP_NAME);
        }
        catch (RemoteException e) {
            System.out.println("Remote Exception error @griSS");
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            System.out.println("Bound Exception error @griSS");
            e.printStackTrace();
        }
        System.out.println("Got GRI!");

        SupplierSite supplierSite = new SupplierSite(numPartTypes, griSS);
        SupplierSiteInterface supplierSiteInterface = null;

        try {
            supplierSiteInterface = (SupplierSiteInterface) UnicastRemoteObject.exportObject(supplierSite,Parameters.SS_PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("GOT SS!");

        /**
         * Register it with the general registry service
         * */
        try {
            register = (Register) registry.lookup(Parameters.REGISTRY_NAME_ENTRY);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException @register");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("GOT Register!");
        try {
            register.bind(Parameters.SS_NAME, supplierSiteInterface);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @ssInterface");
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @ssInterface");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("BIND!");
        System.out.println("Supplier Site registered.");

        while(!supplierSite.finish)
        {
            try {
                synchronized (supplierSite)
                {
                    supplierSite.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Supplier Site finished.");

        try {
            register.unbind(Parameters.SS_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @supplierSite");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @supplierSite");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Supplier Site unregistered.");

        try {
            UnicastRemoteObject.unexportObject(supplierSite,false);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Supplier Site unexported.");
    }
}
