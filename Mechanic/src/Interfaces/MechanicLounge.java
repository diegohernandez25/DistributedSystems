package Interfaces;

/**
 *  Interface for Mechanic for the use of the Lounge
 * */
public interface MechanicLounge {

    /**
     *  Mechanic gets keys of car to be repaired
     *  @param mechanicId ID of the Mechanic
     *  @return key of the car to repair
     *  */
    int getCarToRepairKey(int mechanicId);

    /**
     *      Mechanic asks for a type of car parts for the repair
     *      @param idType       the id of the part to refill stock
     *      @param number       the number of stock needed
     *      @param mechanicId   the id of the mechanic
     * */
    void requestPart(int idType, int number, int mechanicId);

    /**
     * Mechanic return key of the repaired car
     * @param idKey         the id of the key (= idCar)
     * @param mechanicId    the id of the mechanic.
     * */
    void alertManagerRepairDone(int idKey, int mechanicId);

    /**
     * Trigger servers termination.
     * */
    void finish();
}
