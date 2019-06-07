package Main;

import Interfaces.*;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String args[])
    {   String rmiRegHostName   = Parameters.REGISTRY_HOST;
        //String rmiRegHostName   = Parameters.LOCALHOST;
        int rmiRegPortNumb      = Parameters.REGISTRY_PORT;
        Registry registry = null;

        LoungeCustomerInterface managerLounge = null;
        OutsideWorldCustomerInterface customerOW = null;
        ParkCustomerMechanicInterface customerPark = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Registry created.");

        /*Entities*/
        /**
         * Lounge
         * */
        try{
            managerLounge = (LoungeInterface) registry.lookup(Parameters.LOUNGE_NAME);
        }catch (RemoteException e) {
            System.out.println("Remote Exception error @managerLounge");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Bound Exception error @managerLounge");
            e.printStackTrace();
            System.exit(1);
        }


        /**
         * Outside World
         * */
        try {
            customerOW = (OutsideWorldInterface) registry.lookup(Parameters.OW_NAME);
        }
        catch (RemoteException e) {
            System.out.println("Remote Exception error @customerOW");
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            System.out.println("Bound Exception error @customerOW");
            e.printStackTrace();
        }
        System.out.println("OW created.");


        /**
         * Park
         * */
        try {
            customerPark = (ParkInterface) registry.lookup(Parameters.PARK_NAME);
        }
        catch (RemoteException e) {
            System.out.println("Remote Exception error @customerPark");
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            System.out.println("Bound Exception error @customerPark");
            e.printStackTrace();
        }
        System.out.println("Park created.");

        Customer[] customers = new Customer[30];
        for(int i = 0; i< customers.length; i++)
        {   customers[i] = new Customer(i,Math.random() < 0.5,i,customerPark,customerOW,managerLounge);
            customers[i].start();
        }
        for (int i=0; i<30; i++)
        {
            try {
                customers[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
