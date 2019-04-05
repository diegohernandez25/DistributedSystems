package Interfaces;

public interface GriRA {
    void setCustomerCarRepaired(int customer);

    void setNumCarsRepaired();

    void setNumPostJobs();

    void addNumPartAvailable(int part, int num);

    void removeNumPartAvailable(int part);

    void setNumCarWaitingPart(int part, int num);
}
