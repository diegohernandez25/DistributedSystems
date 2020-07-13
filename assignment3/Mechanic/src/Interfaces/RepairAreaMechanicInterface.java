package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Repair Area Interface dedicated to the Mechanic, specifying the Repair Area functions that can be called by the Mechanic.
 * Note: Extends Remote.
 * */
public interface RepairAreaMechanicInterface extends Remote {
    /**
     *      Checks the needed parts for the car to repair
     *
     *      @param idCar    - id of the car to check
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return the id of the part needed for repair
     *      @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int checkCar(int idCar, int mechanicId) throws RemoteException;

    /**
     *      Checks availability of car parts and if available repairs the car
     *
     *      @param carId    - The Id of car to repair.
     *      @param partId   - The id of the car part needed for the repair.
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return true ready for repair. False otherwise
     *      @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    boolean repairCar(int carId, int partId, int mechanicId) throws RemoteException;

    /**
     *      Checks which of the waiting cars are ready for repair
     *
     *      @param mechanicId - id of the mechanic doing the task
     *
     *      @return id of the car ready for repair.
     *      @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int repairWaitingCarWithPartsAvailable(int mechanicId) throws RemoteException;

    /**
     *      Conclude repair of the car.
     *
     *      @param idCar    - Id of the car.
     *      @param mechanicId - id of the mechanic doing the task
     *      @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    void concludeCarRepair(int idCar, int mechanicId) throws RemoteException;

    /**
     * Get maximum number of a specific part that can be stored on the repairArea
     * @param partId    - Id of the car part.
     * @return the maximum number of storage for the part
     * @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int getMaxPartStock(int partId) throws RemoteException;

    /**
     *  Mechanic checks what has to do next
     *  @param mechanicId - id of the mechanic doing the task
     *  @return the task that has to be done
     *  @throws  RemoteException communication-related exceptions that may occur during the execution of a remote method call.
     * */
    int findNextTask(int mechanicId) throws RemoteException;
}
