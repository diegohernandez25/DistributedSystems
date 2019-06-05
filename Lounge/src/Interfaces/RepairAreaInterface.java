package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RepairAreaInterface extends Remote {
    /**
     *      Checks the needed parts for the car to repair
     *
     *      @param idCar id of the car to check
     *      @param mechanicId id of the mechanic doing the task
     *
     *      @return the id of the part needed for repair
     * */
    int checkCar(int idCar, int mechanicId);



    /**
     *      Checks availability of car parts and if available repairs the car
     *
     *      @param carId The Id of car to repair.
     *      @param partId The Id of the car part needed for the repair.
     *      @param mechanicId Id of the mechanic doing the task
     *
     *      @return true ready for repair. False otherwise
     * */
    boolean repairCar(int carId, int partId, int mechanicId) throws RemoteException;

    /**
     *      Checks which of the waiting cars are ready for repair
     *
     *      @param mechanicId id of the mechanic doing the task
     *
     *      @return id of the car ready for repair.
     * */
    int repairWaitingCarWithPartsAvailable(int mechanicId) throws RemoteException;

    /**
     *      Conclude repair of the car.
     *
     *      @param idCar    Id of the car.
     *      @param mechanicId id of the mechanic doing the task
     * */
    void concludeCarRepair(int idCar, int mechanicId) throws RemoteException;

    /**
     *      Refill car Part stock
     *      @param idPart   ID of car part.
     *      @param quantity number of car parts to refill
     * */
    void  refillCarPartStock(int idPart, int quantity) throws RemoteException;

    /**
     * Get maximum number of a specific part that can be stored on the repairArea
     * @param partId    Id of the car part.
     * @return the maximum number of storage for the part
     * */
    int getMaxPartStock(int partId);

    /**
     *  Mechanic checks what has to do next
     *  @param mechanicId id of the mechanic doing the task
     *  @return the task that has to be done
     * */
    int findNextTask(int mechanicId);


    /**
     * Alerts that a new car needs to be checked.
     * @param carID the ID of the car that needs to be repaired
     * */
    void postJob(int carID) throws RemoteException;

    /**
     *  Sends the Mechanics home
     * */
    void sendHome();


    /**
     * Terminates Repair Area Server
     * */
    void finish();

}
