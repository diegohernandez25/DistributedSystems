package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Outside World Interface.
 * Note: Extends Remote, OutsideWorldCustomerInterface, OutsideWorldManagerInterface, OutsideWorldLoungeInterface
 * */
public interface OutsideWorldInterface extends Remote, OutsideWorldCustomerInterface, OutsideWorldManagerInterface, OutsideWorldLoungeInterface {



}
