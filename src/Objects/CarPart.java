package Objects;

import java.util.Objects;

public class CarPart {

    private String name;

    public CarPart(String name) { this.name = name;}

    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarPart carPart = (CarPart) o;
        return Objects.equals(name, carPart.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "CarPart{" +
                "name='" + name + '\'' +
                '}';
    }
}
