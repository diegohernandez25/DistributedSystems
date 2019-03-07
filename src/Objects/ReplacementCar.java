package Objects;

import java.util.Objects;

public class ReplacementCar extends Car {
    private int customerId;

    public ReplacementCar(int ownerId) {
        super(ownerId);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "ReplacementCar{" +
                "customerId=" + customerId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ReplacementCar that = (ReplacementCar) o;
        return customerId == that.customerId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), customerId);
    }
}
