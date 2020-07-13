package Interfaces;

/**
 *  Interface for Manager for the use of the Outside World
 * */
public interface ManagerOW {

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
}
