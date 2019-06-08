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
     * Number of customers
     * */
    public static final int numCustomers = Parameters.NUM_CUSTOMERS;

    /**
     * Number of mechanics
     * */
    public static final int numMechanics = Parameters.NUM_MECHANICS;

    /**
     * Number of replacement cars
     * */
    public static final int numReplacementCars = Parameters.NUM_REPLACEMENT;

    /**
     * Number of car part types.
     * */
    public static final int numCarTypes = Parameters.NUM_CAR_TYPES;



    public static void main(String[] args)
    {   String rmiRegHostName = Parameters.REGISTRY_HOST;
        //String rmiRegHostName = Parameters.LOCALHOST;
        int rmiRegPortNumb = Parameters.REGISTRY_PORT;

        GriLounge griLounge = null;
        OutsideWorldLoungeInterface owLounge = null;
        ParkLoungeInterface parkLounge = null;
        RepairAreaLoungeInterface raLounge = null;
        SupplierSiteLoungeInterface ssLounge = null;
        /**
         * Get security manager.
         * */
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        Registry registry = null;
        Register register = null;
        /**
         * Get registry.
         * */
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Get general repository for Lounge.
         * */
        try {
            griLounge = (GeneralRepInterface) registry.lookup(Parameters.GENERALREP_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: RemoteException");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException");
            e.printStackTrace();
            System.exit(1);
        }

        /**
         * Create Lounge object
         * */
        int[] replacementCarKeys = new int[numReplacementCars];
        for(int i = numCustomers; i< numCustomers + numReplacementCars; i++)
        {   replacementCarKeys[i - numCustomers] = i;
        }
        Lounge lounge = null;
        try {
            lounge = new Lounge(numCustomers, numMechanics, replacementCarKeys, numCarTypes, griLounge);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        LoungeInterface loungeInterface = null;
        /**
         * Export Lounge.
         * */
        try {
            loungeInterface = (LoungeInterface) UnicastRemoteObject.exportObject(lounge,Parameters.LOUNGE_PORT);
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
            System.out.println("ERROR: Remote Exception @register, @lounge");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @lounge");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Bind Lounge to the register.
         * */
        try {
            register.bind(Parameters.LOUNGE_NAME,loungeInterface);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @lounge");
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @lounge");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Wait until Lounge is called to terminate.
         * */
        while(!lounge.finish)
        {   try {
            synchronized (lounge) {
                lounge.wait();
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * Get outside world for Lounge service.
         * */
        try {
            owLounge = (OutsideWorldInterface) registry.lookup(Parameters.OW_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: RemoteException");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Get Park for Lounge service.
         * */
        try {
            parkLounge = (ParkInterface) registry.lookup(Parameters.PARK_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: RemoteException");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Get repair area for lounge service.
         * */
        try {
            raLounge = (RepairAreaInterface) registry.lookup(Parameters.REPAIRAREA_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: RemoteException");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Get supplier site for lounge service.
         * */
        try {
            ssLounge = (SupplierSiteInterface) registry.lookup(Parameters.SS_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: RemoteException");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Terminates the following services: Supplier Site, Park, Outside World, Repair Area and General Repository
         * */
        try {
            ssLounge.finish();
            parkLounge.finish();
            owLounge.finish();
            raLounge.finish();
            griLounge.finish();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        /**
         * Unbind Lounge to the Register
         * */
        try {
            register.unbind(Parameters.LOUNGE_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @lounge");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException @register, @lounge");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Unexport Lounge.
         * */
        try {
            UnicastRemoteObject.unexportObject(lounge,false);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }
}
