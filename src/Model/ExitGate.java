package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExitGate {

    @Id
    @GeneratedValue()
    private int id;

    @OneToMany(mappedBy="exitGate")
    private List<Ticket> tickets;

    @OneToMany(mappedBy="exitGate")
    private List<Payment> payments;

    @ManyToOne()
    private Garage garage;

    public ExitGate() {
    }

    public ExitGate(Garage owner) {
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

    public List<Payment> getPayments() {
        return payments;
    }

    public String toString() {
        return "Exit Gate #" + id;
    }
}