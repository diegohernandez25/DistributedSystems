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
     * Number of mecahnics
     * */
    public static final int numMechanics = Parameters.NUM_MECHANICS;

    /**
     * Number of car parts types
     * */
    public static final int numPartTypes = Parameters.NUM_CAR_TYPES;

    /**
     * Stock of car parts for each type
     * */
    public static final int[] carParts = Parameters.CAR_PARTS;

    /**
     * Name of the logger file
     */
    public static final String fileName = Parameters.LOG_NAME;

    /**
     * Main
     * @param args  - arguments.
     * */
    public static void main(String[] args)
    {
        //String rmiRegHostName = Parameters.REGISTRY_HOST;
        String rmiRegHostName = Parameters.LOCALHOST;
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
        System.out.println("registry done!");
        GeneralRepInformation generalRepInformation = new GeneralRepInformation(numCustomers, numMechanics, numPartTypes, carParts, fileName);
        GeneralRepInterface generalRepInterface = null;

        try {
            generalRepInterface = (GeneralRepInterface) UnicastRemoteObject.exportObject(generalRepInformation,Parameters.GENERALREP_PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("object exported!");


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
        System.out.println("got register!");

        try {
            register.bind(Parameters.GENERALREP_NAME, generalRepInterface);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @generalRep");
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @generalRep");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("General Repository Information registered.");

        while(!generalRepInformation.finish)
        {   try {
                synchronized (generalRepInformation)
                {   generalRepInformation.wait();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("General Repository Information finished.");

        try {
            register.unbind(Parameters.GENERALREP_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @generalRep");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @generalRep");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("General Repository Information unregistered.");

        try {
            UnicastRemoteObject.unexportObject(generalRepInformation,false);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("General Repository Information unexported.");
    }
}
