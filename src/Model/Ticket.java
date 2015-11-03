package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity
public class Ticket {
    @Id
    @GeneratedValue()
    protected int id;

    @ManyToOne()
    private EntryGate entryGate;

    @ManyToOne()
    private ExitGate exitGate;

    public Ticket() {
    }

    public Ticket(EntryGate owner) {
        setEntryGate(owner);
    }

    public EntryGate getEntryGate() {
        return entryGate;
    }

    public void setEntryGate(EntryGate entryGate) {
        this.entryGate = entryGate;
    }

    public ExitGate getExitGate() {
        return exitGate;
    }

    public void setExitGate(ExitGate exitGate) {
        this.exitGate = exitGate;
    }
}