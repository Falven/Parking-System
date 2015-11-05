package Model;

import Controller.EntryController;
import Controller.GarageController;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class EntryGate {

    private IntegerProperty id;
    private ListProperty<Ticket> tickets;
    private ObjectProperty<Garage> garage;
    private EntryController controller;

    public EntryGate() {
        this(null, null);
    }

    public EntryGate(Garage garage, EntryController controller) {
        this.id = new SimpleIntegerProperty();
        this.tickets = new SimpleListProperty<>();
        this.garage = new SimpleObjectProperty<>(garage);
        setController(controller);
    }

    @Id
    @GeneratedValue()
    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    @OneToMany(mappedBy="entryGate", cascade = CascadeType.ALL)
    public List<Ticket> getTickets() {
        return this.tickets.get();
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets.set(FXCollections.observableArrayList(tickets));
    }

    public ListProperty<Ticket> ticketsProperty() {
        return this.tickets;
    }

    @ManyToOne()
    public Garage getGarage() {
        return this.garage.get();
    }

    public void setGarage(Garage garage) {
        this.garage.set(garage);
    }

    public ObjectProperty<Garage> garageProperty() {
        return this.garage;
    }

    @Transient
    public EntryController getController() {
        return this.controller;
    }

    public void setController(EntryController controller) {
        this.controller = controller;
    }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}
