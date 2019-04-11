package Interfaces;

/**
 *  Interface for Customer for the use of the Outside World
 * */
public interface CustomerOW {
    /**
     *  Customer waits until the manager alerts him/her about the end of the service.
     *  @param customerId - ID of the waiting customer.
     * */
    void waitForRepair(Integer customerId);
}
