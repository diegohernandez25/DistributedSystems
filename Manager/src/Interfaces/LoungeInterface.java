package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoungeInterface extends Remote {
    /**
     *  Customer enters queue to be attended by the Manager.
     *      @param customerId Id of the customer to be attended
     *      @param payment type of attendance. (true/false) Pay for repair/Request repair.
     * */
    void enterCustomerQueue(int customerId, boolean payment) throws RemoteException;

    /**
     * Get replacement car key.
     * @param customerId ID of the client who needs the replacement car.
     * @return the key of the replacement car.
     * */
    int getReplacementCarKey(int customerId) throws RemoteException;

    /**
     *  Return Replacement car key.
     *  Customer with the need of a replacement car invokes this method.
     *
     *      @param key Key of the replacement car key.
     *      @param customerId ID of the current Customer returning the replacement car key
     *
     * */
    void returnReplacementCarKey(int key, int customerId) throws RemoteException;

    /**
     * Exit Lounge
     * @param customerId Id of the customer who will exit the lounge
     * */
    void exitLounge(int customerId) throws RemoteException;

    /**
     * Customer gives Manager his/hers car key.
     * @param key - Customer's car key.
     * @param customerId current Customer giving their car key
     */
    void giveManagerCarKey(int customerId, int key) throws RemoteException;

    /**
     *  Customer pays for the service and retrieves the keys of his/her car.
     *  @param customerId - ID of the customer.
     *  @return the Customer's car key.
     * */
    int payForTheService(int customerId) throws RemoteException;

    /**
     *  Operation to attend customer. Can be for receive payment or to initiate the repair of a car.
     *  Manager invokes this method.
     *  @return success of the operation so the Mechanic can move on or not to the next operation.
     */
    int attendCustomer() throws RemoteException;

    /**
     *  register refill of stock
     *  @param idType the type of Car Part
     *  @param numberParts number of Car Parts being refilled
     * */
    void registerStockRefill(int idType, int numberParts) throws RemoteException;

    /**
     * Manager checks if parts needs to be refilled
     * @return id of the part to refill. Returns -1 if no part is needed to refill
     * */
    int checksPartsRequest() throws RemoteException;

    /**
     * Checks if there are cars repaired
     * @return true customer fixed car keys empty
     * */
    boolean isCustomerFixedCarKeysEmpty() throws RemoteException;

    /**
     * Gets key of a fixed car.
     * @return id of the key
     * */
    int getFixedCarKey() throws RemoteException;

    /**
     * Gets customer given the id of the key whom the customer belongs-
     * @param idKey id of the key.
     * @return the id of the customer
     * */
    int getCustomerFromKey(int idKey) throws RemoteException;

    /**
     * Make key ready to give back to customer.
     * @param idCustomer id of the customer.
     * @param idKey id of the key.
     * */
    void readyToDeliverKey(int idCustomer, int idKey) throws RemoteException;

    /**
     *  Get the requested number of a part
     *  @param partId ID of the part Car.
     *  @return number of parts requested.
     * */
    int requestedNumberPart(int partId) throws RemoteException;

    /**
     * Checks if all customer have been attended.
     * @return true - all services done to all customers for the dat. False - otherwise
     * */
    boolean allDone() throws RemoteException;

    /**
     *  Mechanic gets keys of car to be repaired
     *  @param mechanicId ID of the Mechanic
     *  @return key of the car to repair
     *  */
    int getCarToRepairKey(int mechanicId) throws RemoteException;

    /**
     *      Mechanic asks for a type of car parts for the repair
     *      @param idType       the id of the part to refill stock
     *      @param number       the number of stock needed
     *      @param mechanicId   the id of the mechanic
     * */
    void requestPart(int idType, int number, int mechanicId) throws RemoteException;

    /**
     * Mechanic return key of the repaired car
     * @param idKey         the id of the key (= idCar)
     * @param mechanicId    the id of the mechanic.
     * */
    void alertManagerRepairDone(int idKey, int mechanicId) throws RemoteException;

    /**
     * Trigger servers termination.
     * */
    void finish() throws RemoteException;
}
