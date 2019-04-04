package Interfaces;

public interface ManagerLounge {
    int attendCustomer();

    boolean registerStockRefill(int idType, int numberParts);

    int checksPartsRequest(int index);

    boolean isCustomerFixedCarKeysEmpty();

    int getFixedCarKey();

    int getCustomerFromKey(int idKey);

    void readyToDeliverKey(int idCustomer, int idKey);

    int requestedNumberPart(int partId);

    boolean allDone();
}
