package Interfaces;

/**
 *  Interface for Manager for the use of the Supplier Site
 * */
public interface ManagerSS {

    /**
     *      Restocks car part. Always gets 3 of the part.
     *
     *      @param idType type of the part to replenish
     *      @param number requested number
     *
     *      @return number of parts that were restocked of the specific type of part
     * */
    int restockPart(int idType, int number);
}
