package Interfaces;

public interface MechanicLounge {
    int getCarToRepairKey(int mechanicId);

    boolean requestPart(int idType, int number, int mechanicId);

    void alertManagerRepairDone(int idKey, int mechanicId);
}
