package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Garage {

    public static final int DEFAULT_MAX_OCCUPANCY = 20;

    private SimpleStringProperty name;
    private SimpleIntegerProperty occupancy;
    private SimpleIntegerProperty maxOccupancy;

    public Garage(String name, int occupancy) {
        this(name, occupancy, DEFAULT_MAX_OCCUPANCY);
    }

    public Garage(String name, int occupancy, int max_occupancy) {
        this.name = new SimpleStringProperty(name);
        this.occupancy = new SimpleIntegerProperty(occupancy);
        this.maxOccupancy = new SimpleIntegerProperty(max_occupancy);
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public int getOccupancy() {
        return this.occupancy.get();
    }

    public void setOccupancy(int occupancy) {
        this.occupancy.set(occupancy);
    }

    public IntegerProperty occupancyProperty() {
        return this.occupancy;
    }

    public int getMaxOccupancy() {
        return this.maxOccupancy.get();
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy.set(maxOccupancy);
    }

    public IntegerProperty maxOccupancyProperty() {
        return this.maxOccupancy;
    }

    @Override
    public String toString() {
        return getName();
    }
}
