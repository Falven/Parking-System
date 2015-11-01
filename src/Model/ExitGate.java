package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class ExitGate {
    @Id
    @NotNull
    @GeneratedValue()
    protected int id;

    @NotNull
    @OneToMany()
    private List<Ticket> tickets;

    @NotNull
    @OneToMany()
    private List<Payment> payments;

    @ManyToOne()
    private Garage garage;

    public void ExitGate() {
    }
}