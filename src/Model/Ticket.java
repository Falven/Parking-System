package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;

@Entity(name = "TICKET")
public class Ticket {
    @Id
    @NotNull
    @GeneratedValue()
    @Column(name="TICKET_ID")
    protected int id;

    @ManyToOne()
    @JoinColumn(name="ENTRYGATE_ID")
    private EntryGate entryGate;

    @ManyToOne()
    @JoinColumn(name="EXITGATE_ID")
    private ExitGate exitGate;

    public Ticket() {
    }
}