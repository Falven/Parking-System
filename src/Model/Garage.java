package Model;

import Controller.GarageController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class Garage {

    private SimpleStringProperty name;
    private SimpleIntegerProperty occupancy;
    private SimpleIntegerProperty maxOccupancy;
    private SimpleListProperty<Ticket> tickets;
    private SimpleListProperty<EntryGate> entryGates;
    private SimpleListProperty<ExitGate> exitGates;
    private GarageController controller;

    @Transient
    private static final int MAX_OCCUPANCY = 20;

    public Garage() {
        this(null, null);
    }

    public Garage(String name, GarageController controller) {
        this.name = new SimpleStringProperty(name);
        this.occupancy = new SimpleIntegerProperty();
        this.maxOccupancy = new SimpleIntegerProperty(MAX_OCCUPANCY);
        this.tickets = new SimpleListProperty<>();
        this.entryGates = new SimpleListProperty<>();
        this.exitGates = new SimpleListProperty<>();
        setController(controller);
    }

    @Id
    @Column(nullable = false)
    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    @Column(nullable = false)
    public int getOccupancy() {
        return this.occupancy.get();
    }

    public void setOccupancy(int occupancy) {
        this.occupancy.set(occupancy);
    }

    public IntegerProperty occupancyProperty() {
        return this.occupancy;
    }

    @Column(nullable = false)
    public int getMaxOccupancy() {
        return this.maxOccupancy.get();
    }

    public void setMaxOccupancy(int maxOccupancy) {
        this.maxOccupancy.set(maxOccupancy);
    }

    public IntegerProperty maxOccupancyProperty() {
        return this.maxOccupancy;
    }

    @OneToMany(mappedBy="garage", cascade = CascadeType.ALL)
    public List<Ticket> getTickets() {
        return this.tickets.get();
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets.set(FXCollections.observableArrayList(tickets));
    }

    public ListProperty<Ticket> ticketsProperty() {
        return this.tickets;
    }

    @OneToMany(mappedBy="garage", cascade = CascadeType.ALL)
    public List<EntryGate> getEntryGates() {
        return this.entryGates.get();
    }

    public void setEntryGates(List<EntryGate> entryGates) {
        this.entryGates.set(FXCollections.observableArrayList(entryGates));
    }

    public ListProperty<EntryGate> entryGatesProperty() {
        return this.entryGates;
    }

    @OneToMany(mappedBy="garage", cascade = CascadeType.ALL)
    public List<ExitGate> getExitGates() {
        return this.exitGates.get();
    }

    public void setExitGates(List<ExitGate> exitGates) {
        this.exitGates.set(FXCollections.observableArrayList(exitGates));
    }

    public ListProperty<ExitGate> exitGatesProperty() {
        return this.exitGates;
    }

    @Transient
    public GarageController getController() {
        return this.controller;
    }

    public void setController(GarageController controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return getName();
    }
}
