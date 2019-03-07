package Entities;

public class Mechanic {

    private int id;
    private EnumMechanic state;

    public Mechanic(int id)
    {
        this.id = id;
        this.state = EnumMechanic.READING_PAPER;
    }

    public int getId() {
        return id;
    }

    public EnumMechanic getState() {
        return state;
    }

    public void setState(EnumMechanic state) {
        this.state = state;
    }
}
