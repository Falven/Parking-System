package Model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

@Entity
public class Ticket {

    @Id
    @GeneratedValue()
    private int id;

    @ManyToOne()
    private EntryGate entryGate;

    @ManyToOne()
    private ExitGate exitGate;

    @Column(nullable = false)
    private java.sql.Date assignedDate;

    @Column(nullable = false)
    private java.sql.Time assignedTime;

    @Column(nullable = false)
    private java.sql.Date dueDate;

    @Column(nullable = false)
    private java.sql.Time dueTime;

    @Column(nullable = false)
    private BigDecimal amountDue;

    public Ticket() {
        Calendar cal = Calendar.getInstance();
        assignedDate = new Date(cal.getTimeInMillis());
        assignedTime = new java.sql.Time(assignedDate.getTime());

        cal.add(Calendar.DATE, 1);
        dueDate = new Date(cal.getTimeInMillis());
        dueTime = new java.sql.Time(dueDate.getTime());

        amountDue = new BigDecimal(19.99);
    }

    public Ticket(EntryGate owner) {
        this();
        this.entryGate = owner;
    }

    public Ticket(int id) {
        this();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public EntryGate getEntryGate() {
        return entryGate;
    }

    public ExitGate getExitGate() {
        return exitGate;
    }

    public void setExitGate(ExitGate exitGate) {
        this.exitGate = exitGate;
    }

    public Date getAssignedDate() {
        return assignedDate;
    }

    public Time getAssignedTime() {
        return assignedTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Time getDueTime() {
        return dueTime;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }
}