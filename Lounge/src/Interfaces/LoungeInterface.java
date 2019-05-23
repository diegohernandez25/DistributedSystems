package Interfaces;

import Resources.MemException;

import java.rmi.Remote;

public interface LoungeInterface extends Remote, CustomerLounge, ManagerLounge, MechanicLounge {
}
