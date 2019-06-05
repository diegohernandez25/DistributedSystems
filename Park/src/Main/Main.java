package Main;

import Interfaces.GeneralRepInterface;
import Interfaces.ParkInterface;
import Interfaces.Register;

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
     * Number of replacement cars.
     * */
    public static final int numReplacementCars = Parameters.NUM_REPLACEMENT;


    public static void main(String[] args)
    {   //String rmiRegHostName = Parameters.REGISTRY_HOST;
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

        GeneralRepInterface griPark = null;
        try {
            griPark = (GeneralRepInterface) registry.lookup(Parameters.GENERALREP_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: RemoteException");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException");
            e.printStackTrace();
        }


        int[] replacementCarKeys = new int[numReplacementCars];
        for(int i = numCustomers; i< numCustomers + numReplacementCars; i++)
        {   replacementCarKeys[i - numCustomers] = i;
        }
        Park park = new Park((numCustomers + numReplacementCars),replacementCarKeys,griPark);
        ParkInterface parkInterface = null;


        try {
            parkInterface = (ParkInterface) UnicastRemoteObject.exportObject(park,Parameters.PARK_PORT);
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

        try {
            register.bind(Parameters.PARK_NAME,parkInterface);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @park");
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @park");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Park registered.");

        while(!park.finish) {
            try {
                synchronized (park)
                {
                    park.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Park finished.");
        try {
            register.unbind(Parameters.PARK_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @park");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @park");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Park unregistered.");

        try {
            UnicastRemoteObject.unexportObject(park,false);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Park unexported.");
    }
}
