package Interfaces;

/**
 *  Interface for Mechanic for the use of the Repair Area
 * */
public interface MechanicRA {
    int checkCar(int idCar, int mechanicId);

    boolean repairCar(int carId, int partId, int mechanicId);

    int repairWaitingCarWithPartsAvailable(int mechanicId);

    void concludeCarRepair(int idCar, int mechanicId);

    int getMaxPartStock(int partId);

    int findNextTask(int mechanicId);
}
