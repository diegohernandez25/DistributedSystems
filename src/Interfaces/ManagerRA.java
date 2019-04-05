package Interfaces;

/**
 *  Interface for Manager for the use of the Repair Area
 * */
public interface ManagerRA {
    void  refillCarPartStock(int idPart, int quantity);

    void postJob(int carID);

    void sendHome();
}
