package Interfaces;

import java.rmi.Remote;

public interface OutsideWorldInterface extends Remote {

    /**
     *  Customer waits until the manager alerts him/her about the end of the service.
     *  @param customerId ID of the waiting customer.
     * */
    void waitForRepair(Integer customerId);

    /**
     *  Managers alerts customer that car is fixed and it can be retrieved;
     *  @param customerId ID of the customer to alert .
     * */
    void alertCustomer(Integer customerId);

    /**
     *  Alert remaining customer whom hasn't been alerted (because they haven't arrived sooner at the outside world)
     * */
    void alertRemainingCustomers();

    /**
     *  Checks if there are user's expected to arrive at the outside world.
     *  @return true/false if customer is already in the Outside World or not
     * */
    boolean customersNotYetAtOutsideWorldisEmpty();

    /**
     * Terminates Outside World Information Server
     * */
    void finish();

}
