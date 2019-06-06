package Interfaces;

import java.rmi.RemoteException;

/**
 *  Interface for Supplier Site for the use of the General Repository
 * */
public interface GriSS {

    /**
     *  Sets a flag signaling the Manager has been adviced that a specific part is missing
     *  @param part the part which needs to be set the flag
     *  @param flag the flag signaling if Manager has been adviced (T/F)
     * */
    void setFlagMissingPart(int part, String flag) throws RemoteException;

    /**
     *  Increments the number of bought parts of a specific part
     *  @param part the part that was bought
     *  @param num the number of bought parts
     * */
    void setNumBoughtPart(int part, int num) throws  RemoteException;
}
