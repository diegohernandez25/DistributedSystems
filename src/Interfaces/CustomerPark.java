package Interfaces;

/**
 *  Interface for Customer for the use of the Park
 * */
public interface CustomerPark {
    boolean parkCar(Integer carId, int id, boolean customerPark);

    Integer getCar(Integer carId, int id, boolean customerGet);
}
