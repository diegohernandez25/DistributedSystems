package Interfaces;

/**
 *  Interface for Customer for the use of the Lounge
 * */
public interface CustomerLounge {

    /**
     *  Customer enters queue to be attended by the Manager.
     *      @param customerId - Id of the customer to be attended
     *      @param payment - type of attendance. (true/false) Pay for repair/Request repair.
     *      @return operation success so the thread can move on to the next operation.
     * */
    boolean enterCustomerQueue(int customerId, boolean payment);

    /**
     * Get replacement car key.
     * @param customerId - ID of the client who needs the replacement car.
     * @return the key of the replacement car.
     * */
    int getReplacementCarKey(int customerId);

    /**
     *  Return Replacement car key.
     *  Customer with the need of a replacement car invokes this method.
     *
     *      @param key - Key of the replacement car key.
     *      @param customerId ID of the current Customer returning the replacement car key
     *
     *      @return status of the operation.
     * */
    boolean returnReplacementCarKey(int key, int customerId);

    /**
     * Exit Lounge
     * @param customerId - Id of the customer who will exit the lounge
     * */
    void exitLounge(int customerId);

    /**
     * Customer gives Manager his/hers car key.
     * @param key - Customer's car key.
     * @param customerId current Customer giving their car key
     */
    void giveManagerCarKey(int key, int customerId);

    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *  @param customerId - ID of the customer.
     *  @return the Customer's car key.
     * */
    int payForTheService(int customerId);
}
