package Objects;

import java.util.Objects;

public class Car {
    private int ownerId;
    private Key key;

    public Car(int ownerId)
    {
        this.ownerId = ownerId;
        this.key = new Key();
    }

    @Override
    public String toString() {
        return "Car{" +
                "ownerId=" + ownerId +
                ", key=" + key +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return ownerId == car.ownerId &&
                Objects.equals(key, car.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, key);
    }

    public int getOwnerId() {
        return ownerId;
    }

    public Key getKey() {
        return key;
    }
}
