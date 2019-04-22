package Main;

import Locals.Lounge;
import Locals.OutsideWorld;
import Locals.Park;

public class Main {

    public static void main(String[] args)
    {   int NUM_CUSTOMERS = 30;
        Park park = new Park("localhost",22461);
        OutsideWorld outsideWorld = new OutsideWorld("localhost",22462);
        Lounge lounge = new Lounge("localhost",22460);

        Customer[] customer = new Customer[NUM_CUSTOMERS];
        for(int i =0; i <NUM_CUSTOMERS; i++)
        {   customer[i] = new Customer(i, Math.random() < 0.5, i, park, outsideWorld, lounge);
            customer[i].start();
        }

        for(int i =0; i < NUM_CUSTOMERS; i++)
        {   try { customer[i].join();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
}
