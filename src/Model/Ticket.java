package Model;

import Controller.TicketController;
import javafx.beans.property.*;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class Ticket extends Model<TicketController> {

    public static final double DEFAULT_AMOUNT_DUE = 19.99;

    private IntegerProperty id;
    private ObjectProperty<Date> assignedDate;
    private ObjectProperty<Time> assignedTime;
    private ObjectProperty<Date> dueDate;
    private ObjectProperty<Time> dueTime;
    private DoubleProperty amountDue;
    private IntegerProperty entryGateId;
    private IntegerProperty exitGateId;

    public Ticket() {
        this(0);
    }

    public Ticket(int entryGateId) {
        this.id = new SimpleIntegerProperty();
        Calendar cal = Calendar.getInstance();
        this.assignedDate = new SimpleObjectProperty<>(new Date(cal.getTimeInMillis()));
        this.assignedTime = new SimpleObjectProperty<>(new Time(getAssignedDate().getTime()));
        cal.add(Calendar.DATE, 1);
        this.dueDate = new SimpleObjectProperty<>(new Date(cal.getTimeInMillis()));
        this.dueTime = new SimpleObjectProperty<>(new Time(getDueDate().getTime()));
        this.amountDue = new SimpleDoubleProperty(DEFAULT_AMOUNT_DUE);
        this.entryGateId = new SimpleIntegerProperty(entryGateId);
        this.exitGateId = new SimpleIntegerProperty();
    }

    public Ticket(int id, Date assignedDate, Time assignedTime, Date dueDate, Time dueTime, double amountDue, int entryGateId, int exitGateId) {
        this.id = new SimpleIntegerProperty(id);
        this.assignedDate = new SimpleObjectProperty<>(assignedDate);
        this.assignedTime = new SimpleObjectProperty<>(assignedTime);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.dueTime = new SimpleObjectProperty<>(dueTime);
        this.amountDue = new SimpleDoubleProperty(amountDue);
        this.entryGateId = new SimpleIntegerProperty(entryGateId);
        this.exitGateId = new SimpleIntegerProperty(exitGateId);
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public Date getAssignedDate() {
        return this.assignedDate.get();
    }

    public void setAssignedDate(Date assignedDate) {
        this.assignedDate.set(assignedDate);
    }

    public ObjectProperty<Date> assignedDateProperty() {
        return this.assignedDate;
    }

    public Time getAssignedTime() {
        return this.assignedTime.get();
    }

    public void setAssignedTime(Time assignedTime) {
        this.assignedTime.set(assignedTime);
    }

    public ObjectProperty<Time> assignedTimeProperty() {
        return this.assignedTime;
    }

    public Date getDueDate() {
        return this.dueDate.get();
    }

    public void setDueDate(Date dueDate) {
        this.dueDate.set(dueDate);
    }

    public ObjectProperty<Date> dueDateProperty() {
        return this.dueDate;
    }

    public Time getDueTime() {
        return this.dueTime.get();
    }

    public void setDueTime(Time dueTime) {
        this.dueTime.set(dueTime);
    }

    public ObjectProperty<Time> dueTimeProperty() {
        return this.dueTime;
    }

    public double getAmountDue() {
        return this.amountDue.get();
    }

    public void setAmountDue(double amountDue) {
        this.amountDue.set(amountDue);
    }

    public DoubleProperty amountDueProperty() {
        return this.amountDue;
    }

    public int getEntryGateId() {
        return this.entryGateId.get();
    }

    public void setEntryGateId(int entryGateId) {
        this.entryGateId.set(entryGateId);
    }

    public IntegerProperty entryGateIdProperty() {
        return this.entryGateId;
    }

    public int getExitGateId() {
        return this.exitGateId.get();
    }

    public void setExitGateId(int exitGateId) {
        this.exitGateId.set(exitGateId);
    }

    public IntegerProperty exitGateIdProperty() {
        return this.exitGateId;
    }

    @Override
    public String toString() {
        return Integer.toString(getId());
    }
}