package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Park Interface.
 * Note: Extends Remote, ParkLoungeInterface and ParkCustomerMechanicInterface.
 * */
public interface ParkInterface extends Remote, ParkLoungeInterface, ParkCustomerMechanicInterface{

}
