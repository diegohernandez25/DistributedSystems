package Locations;

import Loggers.Logger;
import Objects.CarPart;
import Resources.MemException;
import Resources.MemStack;
import java.util.ArrayList;
import java.util.HashMap;

public class RepairArea {

    /**
     *  Mechanic states
     *  */
    private final static int    WAITING_FOR_WORK = 0,
                                FIXING_THE_CAR = 1,
                                CHECKING_STOCK = 2,
                                ALERTING_MANAGER = 3;

    // Hashmap of car part + number of available parts
    private static HashMap<CarPart, Integer> stock;

    Logger logger = new Logger();

    /**
     *
     * Instantiation of the Repair Area
     *      @param stock - stock of car parts and the number of available parts
     *      @param mechanics - Mechanics
     *
     * */
    public RepairArea(HashMap<CarPart, Integer> stock, Integer[] mechanics)
    {
        this.stock = stock;
        this.stateMechanics = new int[mechanics.length];
    }

    /**
     *  Adds a number of car parts to stock
     *      @param carPart car part to add to stock
     *      @param number number of the car part to add to stock
     * */
    public void addToStock(CarPart carPart, int number)
    {
        stock.put(carPart, number);
    }

    /**
     *  Checks if car part is available in stock
     *      @param carPart car part to check
     *      @return boolean (true/false) Car part is in stock/Car part doesn't exist or isn't available
     * */
    public synchronized boolean checkCarPartInStock(CarPart carPart)
    {
        // check stock for part
        if(stock.get(carPart) == 0 || stock.get(carPart) == null)
        {
            // if not available (out of stock or inexistent), return false
            return false;
        }

        // else, it exists and in stock
        return true;
    }

    /**
     *  Gets car part from stock
     *      @param carPart car part to get from stock
     *      @param mechanicId id of the Mechanic
     *      @return CarPart removes specific car part from stock and returns it
     * */
    public synchronized CarPart getCarPart(CarPart carPart, int mechanicId)
    {
        stateMechanics[mechanicId] = CHECKING_STOCK;
        // remove one of the car parts from stock
        int inStock = stock.get(carPart);
        inStock--;
        stock.put(carPart, inStock);

        // return it
        return carPart;
    }

    /**
     *  Checks which part is needed for the car
     *      @param mechanicId id of the Mechanic
     *      @return CarPart car part needed for a car
     * */
    public synchronized CarPart checkCarPartNeeded(int mechanicId)
    {
        stateMechanics[mechanicId] = FIXING_THE_CAR;
        CarPart carPart = new CarPart((int) Math.random() * 2);
        return carPart;
    }

}
