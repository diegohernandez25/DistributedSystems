package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface LoungeInterface extends Remote, LoungeCustomerInterface, LoungeManagerInterface, LoungeMechanicInterface {
}
