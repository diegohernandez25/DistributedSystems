package Interfaces;

/**
 *  Interface for Manager for the use of the Repair Area
 * */
public interface ManagerRA {
    /**
     *      Refill car Part stock
     *      @param idPart   - ID of car part.
     *      @param quantity - number of car parts to refill
     * */
    void refillCarPartStock(int idPart, int quantity);

    /**
     * Alerts that a new car needs to be checked.
     * @param carID the ID of the car that needs to be repaired
     * */
    void postJob(int carID);

    /**
     *  Sends the Mechanics home
     * */
    void sendHome();
}
