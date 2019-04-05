package Interfaces;

public interface ManagerOW {
    void alertCustomer(Integer customerId);

    void alertRemainingCustomers();

    boolean customersNotYetAtOutsideWorldisEmpty();
}
