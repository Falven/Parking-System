package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExitGate {
    @Id
    @NotNull
    @GeneratedValue()
    private int id;

    @NotNull
    @OneToMany(cascade=CascadeType.REMOVE)
    private List<Ticket> tickets;

    @NotNull
    @OneToMany(cascade=CascadeType.REMOVE)
    private List<Payment> payments;

    @ManyToOne()
    private Garage garage;

    public ExitGate() {
    }

    public ExitGate(Garage owner) {
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
        return "Exit Gate #" + id;
    }
}