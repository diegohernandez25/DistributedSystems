package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Repair Area Interface.
 * Note: Extends Remote, RepairAreaManagerInterface, RepairAreaMechanicInterface, RepairAreaLoungeInterface.
 * */
public interface RepairAreaInterface extends Remote, RepairAreaManagerInterface, RepairAreaMechanicInterface, RepairAreaLoungeInterface {
}
