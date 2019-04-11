package Interfaces;

/**
 *  Interface for Mechanic for the use of the Park
 * */
public interface MechanicPark {

    /**
     *  Park Car.
     *  @param carId ID of the car.
     *  @param id ID of customer or mechanic. This is just for the Logger
     *  @param customerPark (true/false) if it was the Customer parking the car. GeneralRep only
     *  @return Operation status.
     * */
    boolean parkCar(Integer carId, int id, boolean customerPark);

    /**
     *  Gets Car.
     *  @param carId id of the car.
     *  @param id id of the Mechanic or Customer
     *  @param customerGet if it was the Customer getting the Car
     *  @return id of the car
     * */
    Integer getCar(Integer carId, int id, boolean customerGet);
}
