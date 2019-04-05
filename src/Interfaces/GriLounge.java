package Interfaces;

public interface GriLounge {
    void setStateManager(int state);

    void setStateCustomer(int customer, int state);

    void setStateMechanic(int mechanic, int state);

    void setCustomerNeedsReplacement(int customer);

    void addCustomersQueue();

    void removeCustomersQueue();

    void addCustomersReplacementQueue();

    void removeCustomersReplacementQueue();

    void setNumReplacementParked(int num);

    void setNumCarWaitingPart(int part, int num);

    void setFlagMissingPart(int part, String flag);
}
