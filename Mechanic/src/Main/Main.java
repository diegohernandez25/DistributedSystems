package Main;

import Interfaces.MechanicLounge;
import Interfaces.MechanicPark;
import Interfaces.MechanicRA;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main
{
    public static void main(String args[])
    {   String rmiRegHostName   = Parameters.REGISTRY_NAME;
        int rmiRegPortNumb      = Parameters.REGISTRY_PORT;

        MechanicLounge mechanicLounge   = null;
        MechanicPark mechanicPark       = null;
        MechanicRA mechanicRA           = null;

        Registry registry = null;


        try {
            registry = LocateRegistry.getRegistry(rmiRegHostName, rmiRegPortNumb);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.out.println("Registry created.");

        /**
         * Entities
         * */
        /**
         * Lounge
         * */
        try {
            mechanicLounge = (MechanicLounge) registry.lookup(Parameters.LOUNGE_NAME);
        } catch (RemoteException e) {
            System.out.println("Remote Exception error @mechanicLounge");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Bound Exception error @mechanicLounge");
            e.printStackTrace();
            System.exit(1);
        }

        /**
         * Park
         * */

        try {
            mechanicPark = (MechanicPark) registry.lookup(Parameters.PARK_NAME);
        } catch (RemoteException e) {
            System.out.println("Remote Exception error @mechanicPark");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Bound Exception error @mechanicPark");
            e.printStackTrace();
            System.exit(1);
        }

        try {
            mechanicRA = (MechanicRA) registry.lookup(Parameters.REPAIRAREA_NAME);
        } catch (RemoteException e) {
            System.out.println("Remote Exception error @mechanicRA");
            e.printStackTrace();
            System.exit(1);
        } catch (NotBoundException e) {
            System.out.println("Bound Exception error @mechanicRA");
            e.printStackTrace();
            System.exit(1);
        }

        Mechanic[] mechanic = new Mechanic[2];
        for(int i = 0; i< 2; i++)
        {   mechanic[i] = new Mechanic(i,mechanicLounge,mechanicPark,mechanicRA);
            mechanic[i].start();
        }
        for(int i = 0; i< 2; i++){
            try {
                mechanic[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

    }
}

