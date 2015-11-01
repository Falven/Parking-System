package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity(name = "ENTRYGATE")
public class EntryGate {
    @Id
    @NotNull
    @GeneratedValue()
    @Column(name="ENTRYGATE_ID")
    protected int id;

    @NotNull
    @Column(name="TICKET_ID")
    @OneToMany()
    private List<Ticket> tickets;

    @ManyToOne()
    @JoinColumn(name="ENTRYGATE_ID")
    private Garage garage;

    public EntryGate() {
    }
}