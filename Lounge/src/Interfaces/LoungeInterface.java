package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Lounge Interface. Extends all Lounge Interfaces dedicated for the different entities (This is: Manager, Mechanic and Customers)
 * Note: Extends Remote, LoungeCustomerInterface, LoungeManagerInterface and LoungeMechanicInterface.
 * */
public interface LoungeInterface extends Remote, LoungeCustomerInterface, LoungeManagerInterface, LoungeMechanicInterface {
}
