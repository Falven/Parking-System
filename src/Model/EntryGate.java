package Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class EntryGate {

    @Id
    @GeneratedValue()
    private int id;

    @OneToMany(mappedBy="entryGate", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @ManyToOne()
    private Garage garage;

    public EntryGate() {
    }

    public EntryGate(Garage owner) {
        this();
        setGarage(owner);
    }

    public int getId() {
        return id;
    }

    public Garage getGarage() {
        return garage;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public String toString() {
        return "Entry Gate #" + id;
    }
}