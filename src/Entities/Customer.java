package Entities;

import java.util.Objects;

public class Customer {
    private int id;
    private boolean requiresCar;
    private boolean hasCar;
    private EnumCustomer state;

    public Customer(int id, boolean requiresCar)
    {
        this.id = id;
        this.requiresCar = requiresCar;
        this.hasCar = true;
        this.state = EnumCustomer.NORMAL_LIFE;
    }

    public int getId() { return id; }

    public boolean requiresCar() { return requiresCar; }

    public boolean hasCar() { return hasCar; }

    public void setHasCar(boolean hasCar) { this.hasCar = hasCar; }

    @Override
    public String toString()
    {
        return "Customer{" +
                "id=" + id +
                ", requiresCar=" + requiresCar +
                ", hasCar=" + hasCar +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id &&
                requiresCar == customer.requiresCar &&
                hasCar == customer.hasCar;
    }

    @Override
    public int hashCode() { return Objects.hash(id, requiresCar, hasCar); }
}
