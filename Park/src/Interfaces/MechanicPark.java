package Interfaces;

/**
 *  Interface for Mechanic for the use of the Park
 * */
public interface MechanicPark {

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
