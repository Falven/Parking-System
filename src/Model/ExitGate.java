package Model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

import javax.persistence.*;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class ExitGate {

    private IntegerProperty id;
    private ListProperty<Ticket> tickets;
    private ListProperty<Payment> payments;
    private ObjectProperty<Garage> garage;

    public ExitGate() {
        this(null);
    }

    public ExitGate(Garage owner) {
        this.id = new SimpleIntegerProperty();
        this.tickets = new SimpleListProperty<>();
        this.payments = new SimpleListProperty<>();
        this.garage = new SimpleObjectProperty<>(owner);
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

    @OneToMany(mappedBy="exitGate", cascade = CascadeType.ALL)
    public List<Ticket> getTickets() {
        return this.tickets.get();
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets.set(FXCollections.observableArrayList(tickets));
    }

    public ListProperty<Ticket> ticketsProperty() {
        return this.tickets;
    }

    @OneToMany(mappedBy="exitGate", cascade = CascadeType.ALL)
    public List<Payment> getPayments() {
        return this.payments.get();
    }

    public void setPayments(List<Payment> payments) {
        this.payments.set(FXCollections.observableArrayList(payments));
    }

    public ListProperty<Payment> paymentsProperty() {
        return this.payments;
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

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}