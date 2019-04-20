package Interfaces;

/**
 *  Interface for Customer for the use of the Park
 * */
public interface CustomerPark {
    /**
     *  Park Car.
     *  @param carId ID of the car.
     * */
    void parkCar(Integer carId);

    /**
     *  Gets Car.
     *  @param carId id of the car.
     *  @return id of the car
     * */
    Integer getCar(Integer carId);
}
