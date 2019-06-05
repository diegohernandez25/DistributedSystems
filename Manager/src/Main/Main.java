package Main;

import Interfaces.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

public class Main {
    public static void main(String args[])
    {   //String rmiRegHostName   = Parameters.REGISTRY_HOST;
        String rmiRegHostName   = Parameters.LOCALHOST;
        int rmiRegPortNumb      = Parameters.REGISTRY_PORT;

        LoungeInterface managerLounge = null;
        OutsideWorldInterface managerOW         = null;
        RepairAreaInterface managerRA         = null;
        SupplierSiteInterface managerSS         = null;

        Registry registry = null;

        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Registry created.");

        /**
         * Entities
         * */
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
         * ManagerOutside World
         * */
        try{
            managerOW = (OutsideWorldInterface) registry.lookup(Parameters.OW_NAME);
        }catch(RemoteException e){
            System.out.println("Remote Exception error @managerOW");
            e.printStackTrace();
            System.exit(1);
        }catch (NotBoundException e){
            System.out.println("Bound Exception error @managerOW");
            e.printStackTrace();
            System.exit(1);
        }

        /**
         * Manager Repair Area
         * */
        try{
            managerRA = (RepairAreaInterface) registry.lookup(Parameters.REPAIRAREA_NAME);
        }catch(RemoteException e){
            System.out.println("Remote Exception error @managerRA");
            e.printStackTrace();
            System.exit(1);
        }catch (NotBoundException e){
            System.out.println("Bound Exception error @managerRA");
            e.printStackTrace();
            System.exit(1);
        }

        /**
         * Manager SupplierSite
         * */
        try{
            managerSS = (SupplierSiteInterface) registry.lookup(Parameters.SS_NAME);
        }catch(RemoteException e){
            System.out.println("Remote Exception error @managerSS");
            e.printStackTrace();
            System.exit(1);
        }catch (NotBoundException e){
            System.out.println("Bound Exception error @managerSS");
            e.printStackTrace();
            System.exit(1);
        }

        Manager manager = new Manager(0,managerLounge,managerSS,managerOW,managerRA);
        manager.start();
        try {
            manager.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
}
