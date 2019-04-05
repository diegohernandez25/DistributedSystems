package Interfaces;

public interface CustomerLounge {
    boolean enterCustomerQueue(int customerId, boolean payment);

    int getReplacementCarKey(int customerId);

    boolean returnReplacementCarKey(int key, int customerId);

    void exitLounge(int customerId);

    void giveManagerCarKey(int key, int customerId);

    int payForTheService(int customerId);
}
