package Locations;

import Entities.Customer;
import Resources.MemException;
import Resources.MemFIFO;

public class Lounge {

    MemFIFO<Customer> queueCustomers;

    public Lounge(Customer[] storage)
    {
        try {
            queueCustomers = new MemFIFO<>(storage);
        } catch (MemException e) {
            e.printStackTrace();
        }
    }


}
