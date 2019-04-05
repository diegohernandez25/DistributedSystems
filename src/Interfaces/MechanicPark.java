package Interfaces;

/**
 *  Interface for Mechanic for the use of the Park
 * */
public interface MechanicPark {
    boolean parkCar(Integer carId, int id, boolean customerPark);

    Integer getCar(Integer carId, int id, boolean customerGet);
}
