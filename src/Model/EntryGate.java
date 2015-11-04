package Model;

import javax.persistence.*;
import java.beans.PropertyChangeSupport;
import java.util.List;

@Entity
@Access(AccessType.PROPERTY)
public class EntryGate {

    private int id;
    private List<Ticket> tickets;
    private Garage garage;

    @Transient
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public EntryGate() {
    }

    public EntryGate(Garage owner) {
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

    @OneToMany(mappedBy="entryGate", cascade = CascadeType.ALL)
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
        return "Entry Gate #" + id;
    }
}