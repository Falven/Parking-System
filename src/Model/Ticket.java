package Model;

import javafx.beans.property.*;

import javax.persistence.*;
import java.sql.Time;
import java.util.Calendar;

@Entity
@Access(AccessType.PROPERTY)
public class Ticket {

    private IntegerProperty id;
    private ObjectProperty<Garage> garage;
    private ObjectProperty<EntryGate> entryGate;
    private ObjectProperty<ExitGate> exitGate;
    private ObjectProperty<java.sql.Date> assignedDate;
    private ObjectProperty<java.sql.Time> assignedTime;
    private ObjectProperty<java.sql.Date> dueDate;
    private ObjectProperty<java.sql.Time> dueTime;
    private DoubleProperty amountDue;

    public Ticket() {
        this(null, null);
    }

    public Ticket(EntryGate gate, Garage garage) {
        this.id = new SimpleIntegerProperty();
        this.garage = (null == garage) ? new SimpleObjectProperty<>() : new SimpleObjectProperty<>(garage);
        this.entryGate = (null == gate) ? new SimpleObjectProperty<>() : new SimpleObjectProperty<>(gate);
        this.exitGate = new SimpleObjectProperty<>();
        Calendar cal = Calendar.getInstance();
        this.assignedDate = new SimpleObjectProperty<>(new java.sql.Date(cal.getTimeInMillis()));
        this.assignedTime = new SimpleObjectProperty<>(new java.sql.Time(getAssignedDate().getTime()));
        cal.add(Calendar.DATE, 1);
        this.dueDate = new SimpleObjectProperty<>(new java.sql.Date(cal.getTimeInMillis()));
        this.dueTime = new SimpleObjectProperty<>(new java.sql.Time(getDueDate().getTime()));
        this.amountDue = new SimpleDoubleProperty(19.99);
    }

    @Id
    @GeneratedValue()
    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    @ManyToOne()
    public Garage getGarage() {
        return this.garage.get();
    }

    public void setGarage(Garage garage) {
        this.garage.set(garage);
    }

    public ObjectProperty<Garage> garageProperty() {
        return this.garage;
    }

    @ManyToOne()
    public EntryGate getEntryGate() {
        return this.entryGate.get();
    }

    public void setEntryGate(EntryGate entryGate) {
        this.entryGate.set(entryGate);
    }

    public ObjectProperty<EntryGate> entryGateProperty() {
        return this.entryGate;
    }

    @ManyToOne()
    public ExitGate getExitGate() {
        return this.exitGate.get();
    }

    public void setExitGate(ExitGate exitGate) {
        this.exitGate.set(exitGate);
    }

    public ObjectProperty<ExitGate> exitGateProperty() {
        return this.exitGate;
    }

    @Column(nullable = false)
    public java.sql.Date getAssignedDate() {
        return this.assignedDate.get();
    }

    public void setAssignedDate(java.sql.Date assignedDate) {
        this.assignedDate.set(assignedDate);
    }

    public ObjectProperty<java.sql.Date> assignedDateProperty() {
        return this.assignedDate;
    }

    @Column(nullable = false)
    public Time getAssignedTime() {
        return this.assignedTime.get();
    }

    public void setAssignedTime(Time assignedTime) {
        this.assignedTime.set(assignedTime);
    }

    public ObjectProperty<java.sql.Time> assignedTimeProperty() {
        return this.assignedTime;
    }

    @Column(nullable = false)
    public java.sql.Date getDueDate() {
        return this.dueDate.get();
    }

    public void setDueDate(java.sql.Date dueDate) {
        this.dueDate.set(dueDate);
    }

    public ObjectProperty<java.sql.Date> dueDateProperty() {
        return this.dueDate;
    }

    @Column(nullable = false)
    public Time getDueTime() {
        return this.dueTime.get();
    }

    public void setDueTime(Time dueTime) {
        this.dueTime.set(dueTime);
    }

    public ObjectProperty<java.sql.Time> dueTimeProperty() {
        return this.dueTime;
    }

    @Column(nullable = false)
    public double getAmountDue() {
        return this.amountDue.get();
    }

    public void setAmountDue(double amountDue) {
        this.amountDue.set(amountDue);
    }

    public DoubleProperty amountDueProperty() {
        return this.amountDue;
    }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}