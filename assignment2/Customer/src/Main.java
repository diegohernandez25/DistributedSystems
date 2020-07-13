import Locals.Lounge;
import Locals.OutsideWorld;
import Locals.Park;
import Main.Parameters;
import Interfaces.*;
import Main.Customer;

/**
 * Main class.
 * */
public class Main {

    public static void main(String[] args)
    {   int NUM_CUSTOMERS = Parameters.numCustomers;
        Park park = new Park(Parameters.parkHost, Parameters.parkPort);
        OutsideWorld outsideWorld = new OutsideWorld(Parameters.owHost, Parameters.owPort);
        Lounge lounge = new Lounge(Parameters.loungeHost, Parameters.loungePort);

        Customer[] customer = new Customer[NUM_CUSTOMERS];
        for(int i =0; i <NUM_CUSTOMERS; i++)
        {   customer[i] = new Customer(i, Math.random() < 0.5, i, (CustomerPark) park, (CustomerOW) outsideWorld, (CustomerLounge) lounge);
            customer[i].start();
        }

        for(int i =0; i < NUM_CUSTOMERS; i++)
        {   try { customer[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
