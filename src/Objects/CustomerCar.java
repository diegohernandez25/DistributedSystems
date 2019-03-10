package Objects;

import Entities.Customer;

public class CustomerCar extends Car{

    private int customerId;

    public CustomerCar() {
        super();
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

}
