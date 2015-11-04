package Model;

import javax.persistence.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

@Entity
@Access(AccessType.PROPERTY)
public class Ticket {

    private int id;
    private EntryGate entryGate;
    private ExitGate exitGate;
    private java.sql.Date assignedDate;
    private java.sql.Time assignedTime;
    private java.sql.Date dueDate;
    private java.sql.Time dueTime;
    private BigDecimal amountDue;

    @Transient
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Ticket() {
        Calendar cal = Calendar.getInstance();
        setAssignedDate(new Date(cal.getTimeInMillis()));
        setAssignedTime(new java.sql.Time(assignedDate.getTime()));

        cal.add(Calendar.DATE, 1);
        setDueDate(new Date(cal.getTimeInMillis()));
        setDueTime(new java.sql.Time(dueDate.getTime()));

        setAmountDue(new BigDecimal(19.99));
    }

    public Ticket(EntryGate entryGate) {
        this();
        setEntryGate(entryGate);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    @Id
    @GeneratedValue()
    @Access(AccessType.PROPERTY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        int oldId = this.id;
        if(oldId != id) {
            this.id = id;
            this.pcs.firePropertyChange("id", oldId, this.id);
        }
    }

    @ManyToOne()
    @Access(AccessType.PROPERTY)
    public EntryGate getEntryGate() {
        return entryGate;
    }

    public void setEntryGate(EntryGate entryGate) {
        EntryGate oldEntryGate = this.entryGate;
        if(oldEntryGate != entryGate) {
            this.entryGate = entryGate;
            this.pcs.firePropertyChange("entryGate", oldEntryGate, this.entryGate);
        }
    }

    @ManyToOne()
    @Access(AccessType.PROPERTY)
    public ExitGate getExitGate() {
        return exitGate;
    }

    public void setExitGate(ExitGate exitGate) {
        ExitGate oldExitGate = this.exitGate;
        if(oldExitGate != exitGate) {
            this.exitGate = exitGate;
            this.pcs.firePropertyChange("exitGate", oldExitGate, this.exitGate);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public Date getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Date assignedDate) {
        Date oldAssignedDate = this.assignedDate;
        if(oldAssignedDate != assignedDate) {
            this.assignedDate = assignedDate;
            this.pcs.firePropertyChange("assignedDate", oldAssignedDate, this.assignedDate);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public Time getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(Time assignedTime) {
        Time oldAssignedTime = this.assignedTime;
        if(oldAssignedTime != assignedTime) {
            this.assignedTime = assignedTime;
            this.pcs.firePropertyChange("assignedTime", oldAssignedTime, this.assignedTime);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        Date oldDueDate = this.dueDate;
        if(oldDueDate != dueDate) {
            this.dueDate = dueDate;
            this.pcs.firePropertyChange("dueDate", oldDueDate, this.dueDate);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public Time getDueTime() {
        return dueTime;
    }

    public void setDueTime(Time dueTime) {
        Time oldDueTime = this.dueTime;
        if(oldDueTime != dueTime) {
            this.dueTime = dueTime;
            this.pcs.firePropertyChange("dueTime", oldDueTime, this.dueTime);
        }
    }

    @Column(nullable = false)
    @Access(AccessType.PROPERTY)
    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(BigDecimal amountDue) {
        BigDecimal oldAmountDue = this.amountDue;
        if(oldAmountDue != amountDue) {
            this.amountDue = amountDue;
            this.pcs.firePropertyChange("amountDue", oldAmountDue, this.amountDue);
        }
    }

    @Override
    public String toString() {
        return "Ticket #" + getId();
    }
}