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
        /**
         * Gets and Sets Security Manager.
         * */
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        Registry registry = null;
        Register register = null;
        /**
         Gets registry.
         * */
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        GriSS griSS = null;

        /**
         * Gets General Repository Info for Supplier Site.
         * */
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

        /**
         * Greates Supplier Site object
         * */
        SupplierSite supplierSite = new SupplierSite(numPartTypes, griSS);
        SupplierSiteInterface supplierSiteInterface = null;
        /***
         * Exports Supplier Site.
         */
        try {
            supplierSiteInterface = (SupplierSiteInterface) UnicastRemoteObject.exportObject(supplierSite,Parameters.SS_PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }

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
        /**
         * Binds supplier site to register.
         * */
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
        /**
         * Waits until Supplier Site is set to finish.
         * */
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

        /**
         * Unbinds supplier site from register.
         * */
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

        /**
         * Unexport supplier site.
         * */
        try {
            UnicastRemoteObject.unexportObject(supplierSite,false);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
