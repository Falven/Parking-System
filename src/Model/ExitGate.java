package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity(name = "EXITGATE")
public class ExitGate {
    @Id
    @NotNull
    @GeneratedValue()
    @Column(name="EXITGATE_ID")
    protected int id;

    @NotNull
    @Column(name="TICKET_ID")
    @OneToMany()
    private List<Ticket> tickets;

    @NotNull
    @Column(name="PAYMENT_ID")
    @OneToMany()
    private List<Payment> payments;

    @ManyToOne()
    @JoinColumn(name="EXITGATE_ID")
    private Garage garage;

    public void ExitGate() {
    }
}