package Interfaces;

import Resources.MemException;

import java.rmi.Remote;

public interface LoungeInterface extends Remote {

     void enterCustomerQueue(int customerId, boolean payment);

    /**
     *  Operation to attend customer. Can be for receive payment or to initiate the repair of a car.
     *  Manager invokes this method.
     *  @return success of the operation so the Mechanic can move on or not to the next operation.
     * */
    int attendCustomer();

    /**
     * Get replacement car key.
     * @param customerId ID of the client who needs the replacement car.
     * @return the key of the replacement car.
     * */
    int getReplacementCarKey(int customerId);

    /**
     *  Return Replacement car key.
     *  Customer with the need of a replacement car invokes this method.
     *  @param key Key of the replacement car key.
     *  @param customerId ID of the current Customer returning the replacement car key.
     * */
    void returnReplacementCarKey(int key, int customerId);

    /**
     * Exit Lounge
     * @param customerId Id of the customer who will exit the lounge
     * */
    void exitLounge(int customerId);

    /**
     *  Checks if Customer car keys are empty
     *  @return boolean (true/false) Available customers cars/No customers cars.
     * */
     boolean isCustomerCarKeysEmpty();

    /**
     * Customer gives Manager his/hers car key.
     * @param key Customer's car key.
     * @param customerId current Customer giving their car key
     */
    void giveManagerCarKey(int customerId, int key);

    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *  @param customerId ID of the customer.
     *  @return the Customer's car key.
     * */
    int payForTheService(int customerId);


    /**
     *      Mechanic asks for a type of car parts for the repair
     *      @param idType       the id of the part to refill stock
     *      @param number       the number of stock needed
     *      @param mechanicId   the id of the mechanic
     * */
    void requestPart(int idType, int number, int mechanicId);


    void registerStockRefill(int idType, int numberParts);

    /**
     * Manager checks if parts needs to be refilled
     * @return id of the part to refill. Returns -1 if no part is needed to refill
     * */
    int checksPartsRequest();

    /**
     * Mechanic return key of the repaired car
     * @param idKey         the id of the key (= idCar)
     * @param mechanicId    the id of the mechanic.
     * */
    void alertManagerRepairDone(int idKey, int mechanicId);

    /**
     * Checks if there are cars repaired
     * @return true - customer fixed car keys empty
     * */
    boolean isCustomerFixedCarKeysEmpty();
    /**
     * Gets key of a fixed car.
     * @return id of the key
     * */
    int getFixedCarKey();

    /**
     * Gets customer given the id of the key whom the customer belongs-
     * @param idKey id of the key.
     * @return the id of the customer
     * */
    int getCustomerFromKey(int idKey);
    /**
     * Make key ready to give back to customer.
     * @param idCustomer id of the customer.
     * @param idKey id of the key.
     * */
    void readyToDeliverKey(int idCustomer, int idKey);
    /**
     *  Get the requested number of a part
     *  @param partId ID of the part Car.
     *  @return number of parts requested.
     * */
    int requestedNumberPart(int partId);

    /**
     * Checks if all customer have been attended.
     * @return true - all services done to all customers for the dat. False - otherwise
     * */
    public boolean allDone();

    /**
     *  Mechanic declares that he/she is going home. Function is used for the locals/server termination.
     * */
    void finish();
}
