package Objects;

import java.util.Objects;

public class CarPart {

    private int id;

    public CarPart(int id) { this.id = id;}

    public String getId() { return id; }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        CarPart carPart = (CarPart) o;
//        return Objects.equals(name, carPart.name);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name);
//    }

    @Override
    public String toString() {
        return "CarPart{" +
                "id='" + id + '\'' +
                '}';
    }
}
