package Model;

import javax.persistence.*;
import java.beans.PropertyChangeSupport;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class ExitGate {

    private int id;
    private List<Ticket> tickets;
    private List<Payment> payments;
    private Garage garage;

    @Transient
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public ExitGate() {
    }

    public ExitGate(Garage owner) {
        this();
        setGarage(owner);
    }

    @Id
    @GeneratedValue()
    @Access(AccessType.PROPERTY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldId = this.id;
        if(oldId != id) {
            this.id = id;
            pcs.firePropertyChange("id", oldId, this.id);
        }
    }

    @OneToMany(mappedBy="exitGate", cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        List<Ticket> oldTickets = this.tickets;
        if(oldTickets != tickets) {
            this.tickets = tickets;
            pcs.firePropertyChange("tickets", oldTickets, this.tickets);
        }
    }

    @OneToMany(mappedBy="exitGate", cascade = CascadeType.ALL)
    @Access(AccessType.PROPERTY)
    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        List<Payment> oldPayments = this.payments;
        if(oldPayments != payments) {
            this.payments = payments;
            pcs.firePropertyChange("payments", oldPayments, this.payments);
        }
    }

    @ManyToOne()
    @Access(AccessType.PROPERTY)
    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        Garage oldGarage = this.garage;
        if(oldGarage != garage) {
            this.garage = garage;
            pcs.firePropertyChange("garage", oldGarage, this.garage);
        }
    }

    @Override
    public String toString() {
        return "Exit Gate #" + id;
    }
}