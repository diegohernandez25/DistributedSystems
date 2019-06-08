package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * SupplierSite Interface.
 * Note: Extends Remote, SupplierSiteLoungeInterface, SupplierSiteManagerInterface
 * */
public interface SupplierSiteInterface extends Remote, SupplierSiteLoungeInterface, SupplierSiteManagerInterface{

}
