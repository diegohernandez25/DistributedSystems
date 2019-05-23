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
     * Port number
     * */
    private static final int PORT_NUM = Parameters.REPAIRAREA_PORT;

    /**
     * Number of customers
     * */
    public static final int numCustomers = Parameters.NUM_CUSTOMERS;

    /**
     * Number of car parts.
     * */
    public static final int numPartTypes = Parameters.NUM_CAR_TYPES;

    /**
     * Car parts
     */
    public static final int[] carParts = Parameters.CAR_PARTS;

    /**
     * Maximum number of storage for each car part
     */
    public static final int[] maxCarParts = Parameters.MAX_CAR_PARTS;

    /**
     * Main
     * @param args  - arguments.
     * */
    public static void main(String[] args)
    {
        String rmiRegHostName = Parameters.REGISTRY_HOST;
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

        GriRA griRA = null;

        // General Repository Information
        try {
            griRA = (GriRA) registry.lookup(Parameters.GENERALREP_NAME);
        }
        catch (RemoteException e) {
            System.out.println("Remote Exception error @griRA");
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            System.out.println("Bound Exception error @griRA");
            e.printStackTrace();
        }

        RepairArea repairArea = new RepairArea(numCustomers, numPartTypes, carParts, maxCarParts, griRA);
        RepairAreaInterface repairAreaInterface = null;

        try {
            repairAreaInterface = (RepairAreaInterface) UnicastRemoteObject.exportObject(repairAreaInterface,Parameters.REPAIRAREA_PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }

        /**
         * Register it with the general registry service
         * */
        try {
            register = (Register) registry.lookup(Parameters.REGISTRY_NAME);
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
            register.bind(Parameters.REPAIRAREA_NAME, repairAreaInterface);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @raInterface");
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @raInterface");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Repair Area registered.");


        while(!repairArea.finish)
        {
            try {
                repairArea.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Repair Area finished.");

        try {
            register.unbind(Parameters.REPAIRAREA_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @repairArea");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @repairArea");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Repair Area unregistered.");

        try {
            UnicastRemoteObject.unexportObject(repairArea,false);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Repair Area unexported.");
    }
}
