package Controller;

import Model.EntryGate;
import Model.ExitGate;
import Model.Ticket;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class TicketController {

    private Stage stage;
    private Scene scene;
    private Window window;
    private final Ticket bean;
    private JavaBeanIntegerProperty id;
    private JavaBeanObjectProperty<EntryGate> entryGate;
    private JavaBeanObjectProperty<ExitGate> exitGate;
    private JavaBeanObjectProperty<java.sql.Date> assignedDate;
    private JavaBeanObjectProperty<java.sql.Time> assignedTime;
    private JavaBeanObjectProperty<java.sql.Date> dueDate;
    private JavaBeanObjectProperty<java.sql.Time> dueTime;
    private JavaBeanObjectProperty<BigDecimal> amountDue;

    @FXML
    private Label idLabel;

    @FXML
    private Label assignedDateLabel;

    @FXML
    private Label assignedTimeLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label dueTimeLabel;

    @FXML
    private Label totalLabel;

    public TicketController(Ticket ticket, Window owner) throws IOException, NoSuchMethodException {
        this.bean = ticket;
        this.id = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("id").build();
        this.entryGate = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("amountPaid").build();
        this.exitGate = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("amountPaid").build();
        this.assignedDate = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("amountPaid").build();
        this.assignedTime = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("amountPaid").build();
        this.dueDate = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("amountPaid").build();
        this.dueTime = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("amountPaid").build();
        this.amountDue = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("amountPaid").build();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TicketView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 200.0, 300.0);
        this.stage = new Stage();
        this.stage.setMaxWidth(200.0);
        this.stage.setMaxHeight(300.0);
        this.stage.setTitle("Parking Ticket #" + ticket.getId());
        this.stage.setScene(this.scene);
        this.stage.setX(owner.getX());
        this.stage.setY(owner.getY());
        this.stage.resizableProperty().setValue(Boolean.FALSE);
        this.stage.initModality(Modality.NONE);
        this.window = this.scene.getWindow();

        idLabel.setText(Integer.toString(ticket.getId()));
        assignedDateLabel.setText(ticket.getAssignedDate().toString());
        assignedTimeLabel.setText(new SimpleDateFormat("hh:mm").format(ticket.getAssignedTime()));
        dueDateLabel.setText(ticket.getDueDate().toString());
        dueTimeLabel.setText(new SimpleDateFormat("hh:mm").format(ticket.getDueTime()));
        totalLabel.setText(NumberFormat.getCurrencyInstance().format(ticket.getAmountDue()));
    }

    public Ticket getBean() {
        return this.bean;
    }

    public IntegerProperty idProperty() {
        return this.id;
    }

    public ObjectProperty<EntryGate> entryGateProperty() {
        return this.entryGate;
    }

    public ObjectProperty<ExitGate> exitGateProperty() {
        return this.exitGate;
    }

    public ObjectProperty<java.sql.Date> assignedDateProperty() {
        return this.assignedDate;
    }

    public ObjectProperty<java.sql.Time> assignedTimeProperty() {
        return this.assignedTime;
    }

    public ObjectProperty<java.sql.Date> dueDateProperty() {
        return this.dueDate;
    }

    public ObjectProperty<java.sql.Time> dueTimeProperty() {
        return this.dueTime;
    }

    public ObjectProperty<BigDecimal> amountDueProperty() {
        return this.amountDue;
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int value) {
        this.id.set(value);
    }

    public EntryGate getEntryGate() {
        return this.entryGate.get();
    }

    public void setEntryGate(EntryGate value) {
        this.entryGate.set(value);
    }

    public ExitGate getExitGate() {
        return this.exitGate.get();
    }

    public void setExitGate(ExitGate value) {
        this.exitGate.set(value);
    }

    public java.sql.Date getAssignedDate() {
        return this.assignedDate.get();
    }

    public void setAssignedDate(java.sql.Date value) {
        this.assignedDate.set(value);
    }

    public java.sql.Time getAssignedTime() {
        return this.assignedTime.get();
    }

    public void setAssignedTime(java.sql.Time value) {
        this.assignedTime.set(value);
    }

    public java.sql.Date getDueDate() {
        return this.dueDate.get();
    }

    public void setDueDate(java.sql.Date value) {
        this.dueDate.set(value);
    }

    public java.sql.Time getDueTime() {
        return this.dueTime.get();
    }

    public void setDueTime(java.sql.Time value) {
        this.dueTime.set(value);
    }

    public BigDecimal getAmountDue() {
        return this.amountDue.get();
    }

    public void setAmountDue(BigDecimal value) {
        this.amountDue.set(value);
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    public Ticket getTicket() {
        return bean;
    }

    @Override
    public String toString() {
        return bean.toString();
    }
}
