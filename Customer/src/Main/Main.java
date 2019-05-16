package Main;

import Interfaces.CustomerLounge;
import Interfaces.CustomerOW;
import Interfaces.CustomerPark;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {
    static int PORT= 8080;
    static String NAME_SERVER = "localhost";


    public static void main(String args[])
    {   String rmiRegHostName = NAME_SERVER;
        int rmiRegPortNumb = PORT;
        Registry registry = null;

        CustomerLounge customerLounge = null;
        CustomerOW customerOW = null;
        CustomerPark customerPark = null;

        try{
            registry = LocateRegistry.getRegistry(rmiRegHostName,rmiRegPortNumb);
        }
        catch (RemoteException e) {
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Registry created.");

        /*Entities*/
        /**
         * Lounge
         * */
        try {
            customerLounge = (CustomerLounge) registry.lookup("Lounge");
        } catch (RemoteException e) {
            System.out.println("Remote Exception error @customerLounge");
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.out.println("Bound Exception error @customerLounge");
            e.printStackTrace();
        }

        /**
         * Outside World
         * */
        try {
            customerOW = (CustomerOW) registry.lookup("Outside World");
        }
        catch (RemoteException e) {
            System.out.println("Remote Exception error @customerOW");
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            System.out.println("Bound Exception error @customerOW");
            e.printStackTrace();
        }

        /**
         * Park
         * */
        try {
            customerPark = (CustomerPark) registry.lookup("Park");
        }
        catch (RemoteException e) {
            System.out.println("Remote Exception error @customerPark");
            e.printStackTrace();
        }
        catch (NotBoundException e) {
            System.out.println("Bound Exception error @customerPark");
            e.printStackTrace();
        }

        Customer[] customers = new Customer[30];
        for(int i = 0; i< customers.length; i++)
        {   customers[i] = new Customer(i,Math.random() < 0.5,i,customerPark,customerOW,customerLounge);
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
