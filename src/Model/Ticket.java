package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @NotNull
    @GeneratedValue()
    protected int id;

    @ManyToOne()
    private EntryGate entryGate;

    @ManyToOne()
    private ExitGate exitGate;

    public Ticket() {
    }
}