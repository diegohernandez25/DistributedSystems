package Main;

import Interfaces.OutsideWorldInterface;
import Interfaces.Register;

import java.rmi.AlreadyBoundException;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Main {


    public static void main(String[] args)
    {   String rmiRegHostName = Parameters.REGISTRY_HOST;
        //String rmiRegHostName = Parameters.LOCALHOST;
        int rmiRegPortNumb = Parameters.REGISTRY_PORT;
        /**
         * Gets and Sets Security Manager
         * */
        if (System.getSecurityManager () == null)
            System.setSecurityManager (new SecurityManager ());
        System.out.println("Security manager was installed!");
        Registry registry = null;
        Register register = null;
        /**
         * Gets registry.
         * */
        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Creates Outside World Object.
         * */
        OutsideWorld outsideWorld = new OutsideWorld(Parameters.NUM_CUSTOMERS);
        OutsideWorldInterface outsideWorldInterface = null;

        try {
            outsideWorldInterface = (OutsideWorldInterface) UnicastRemoteObject.exportObject(outsideWorld,Parameters.OW_PORT);
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
            System.out.println("ERROR: Remote Exception @register, @outsideWorld");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException @register, @outsideWorld");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Binds Outside World to the Register.
         * */
        try {
            register.bind(Parameters.OW_NAME, outsideWorldInterface);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @outsideWorld");
            e.printStackTrace();
            System.exit(1);
        } catch (AlreadyBoundException e) {
            System.out.println("ERROR: AlreadyBoundException @register, @outsideWorld");
            e.printStackTrace();
            System.exit(1);
        }

        /**
         * Waits until Outside World is set to finish.
         * */
        while(!outsideWorld.finish)
        {   try {
                synchronized (outsideWorld) {
                    outsideWorld.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * Unbinds Outside World to the Register.
         * */
        try {
            register.unbind(Parameters.OW_NAME);
        } catch (RemoteException e) {
            System.out.println("ERROR: Remote Exception @register, @outsideWorld");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("ERROR: NotBoundException @register, @outsideWorld");
            e.printStackTrace();
            System.exit(1);
        }
        /**
         * Unexport Outside World
         * */
        try {
            UnicastRemoteObject.unexportObject(outsideWorld,false);
        } catch (NoSuchObjectException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
