package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class EntryGate {
    @Id
    @NotNull
    @GeneratedValue()
    protected int id;

    @NotNull
    @OneToMany()
    private List<Ticket> tickets;

    @ManyToOne()
    private Garage garage;

    public EntryGate() {
    }
}