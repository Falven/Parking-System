package Model;

import Controller.GarageController;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Garage extends Model<GarageController> {

    public static final int DEFAULT_MAX_OCCUPANCY = 20;

    private SimpleStringProperty name;
    private SimpleIntegerProperty occupancy;
    private SimpleIntegerProperty maxOccupancy;
    private SimpleIntegerProperty entryGates;
    private SimpleIntegerProperty exitGates;

    public Garage(String name) {
        this(name, 0, DEFAULT_MAX_OCCUPANCY, 0, 0);
    }

    public Garage(String name, int occupancy, int max_occupancy, int entryGates, int exitGates) {
        this.name = new SimpleStringProperty(name);
        this.occupancy = new SimpleIntegerProperty(occupancy);
        this.maxOccupancy = new SimpleIntegerProperty(max_occupancy);
        this.entryGates = new SimpleIntegerProperty(entryGates);
        this.exitGates = new SimpleIntegerProperty(exitGates);
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

    public int getEntryGates() {
        return this.entryGates.get();
    }

    public void setEntryGates(int entryGates) {
        this.entryGates.set(entryGates);
    }

    public IntegerProperty entryGatesProperty() {
        return this.entryGates;
    }

    public int getExitGates() {
        return this.exitGates.get();
    }

    public void setExitGates(int exitGates) {
        this.exitGates.set(exitGates);
    }

    public IntegerProperty exitGatesProperty() {
        return this.exitGates;
    }

    @Override
    public String toString() {
        return getName();
    }
}
