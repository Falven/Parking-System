package Controller;

import Model.*;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

public class GarageController extends Controller<Garage> {

    @FXML
    private Label entryGatesCountLabel;

    @FXML
    private Label exitGatesCountLabel;

    @FXML
    private Label ticketCountLabel;

    @FXML
    private Label paymentCountLabel;

    @FXML
    private TableView<EntryGate> entryGatesTable;

    @FXML
    private TableColumn<EntryGate, Number> entryGatesTableIdColumn;

    @FXML
    private TableView<ExitGate> exitGatesTable;

    @FXML
    private TableColumn<ExitGate, Number> exitGatesTableIdColumn;

    @FXML
    private TableView<Ticket> ticketsTable;

    @FXML
    private TableColumn<Ticket, String> ticketsIdCol;

    @FXML
    private TableColumn<Ticket, String> ticketsEntryGateCol;

    @FXML
    private TableColumn<Ticket, String> ticketsExitGateCol;

    @FXML
    private TableColumn<Ticket, String> ticketsAssignedDateCol;

    @FXML
    private TableColumn<Ticket, String> ticketsAssignedTimeCol;

    @FXML
    private TableColumn<Ticket, String> ticketsDueDateCol;

    @FXML
    private TableColumn<Ticket, String> ticketsDueTimeCol;

    @FXML
    private TableColumn<Ticket, String> ticketsAmountDueCol;

    @FXML
    private TableView<Payment> paymentsTable;

    @FXML
    private TableColumn<Payment, String> paymentsPaymentIdCol;

    @FXML
    private TableColumn<Payment, String> paymentsTicketIdCol;

    @FXML
    private TableColumn<Payment, String> paymentsCCNumCol;

    @FXML
    private TableColumn<Payment, String> paymentsCSVCol;

    @FXML
    private TableColumn<Payment, String> paymentsAmountCol;

    @FXML
    private TableColumn<Payment, String> paymentsExpMonthCol;

    @FXML
    private TableColumn<Payment, String> paymentsExpYearCol;

    @FXML
    private TableColumn<Payment, String> paymentsExitGateIdCol;

    @FXML
    private DatePicker dayStatDatePicker;

    @FXML
    private ComboBox<String> monthStatComboBox;

    @FXML
    private ComboBox<String> yearStatComboBox;

    private ListProperty<EntryGate> entryGates;

    private ListProperty<ExitGate> exitGates;

    private ListProperty<Ticket> tickets;

    private ListProperty<Payment> payments;

    private ListProperty<String> monthStatList;

    private ListProperty<String> yearStatList;

    public GarageController(Garage garage, Window owner) throws IOException, NoSuchMethodException, SQLException {
        super(garage);
        double stageWidth = 650.0;
        double stageHeight = 500.0;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double stageX = (screenBounds.getWidth() - stageWidth) / 2;
        double stageY = (screenBounds.getHeight() - stageHeight) / 2;
        initUI(getModel().getName() + " garage.", "/view/GarageView.fxml", stageWidth, stageHeight, Double.MAX_VALUE, Double.MAX_VALUE, stageX, stageY, true, Modality.APPLICATION_MODAL, null, owner);
        initEntryGatesTab();
        initExitGatesTab();
        initTicketsTab();
        initPaymentsTab();
        initDailyStatsTab();
        initMonthlyStatsTab();
        initYearlyStatsTab();
    }

    private void initEntryGatesTab() throws IOException, NoSuchMethodException, SQLException {
        entryGates = new SimpleListProperty<>(ParkingDatabase.getInstance().getEntryGates());
        this.entryGatesCountLabel.textProperty().bind(entryGatesProperty().sizeProperty().asString());
        this.entryGatesTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.entryGatesTable.setItems(getEntryGates());
        this.entryGatesTable.getSelectionModel().selectFirst();
    }

    private void initExitGatesTab() throws IOException, NoSuchMethodException, SQLException {
        exitGates = new SimpleListProperty<>(ParkingDatabase.getInstance().getExitGates());
        this.exitGatesCountLabel.textProperty().bind(exitGatesProperty().sizeProperty().asString());
        this.exitGatesTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.exitGatesTable.setItems(getExitGates());
        this.exitGatesTable.getSelectionModel().selectFirst();
    }

    private void initTicketsTab() throws IOException, NoSuchMethodException, SQLException {
        tickets = new SimpleListProperty<>(ParkingDatabase.getInstance().getTickets());
        this.ticketCountLabel.textProperty().bind(ticketsProperty().sizeProperty().asString());
        this.ticketsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.ticketsAssignedDateCol.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));
        this.ticketsAssignedTimeCol.setCellValueFactory(new PropertyValueFactory<>("assignedTime"));
        this.ticketsDueDateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        this.ticketsDueTimeCol.setCellValueFactory(new PropertyValueFactory<>("dueTime"));
        this.ticketsAmountDueCol.setCellValueFactory(new PropertyValueFactory<>("amountDue"));
        this.ticketsEntryGateCol.setCellValueFactory(new PropertyValueFactory<>("entryGateId"));
        this.ticketsExitGateCol.setCellValueFactory(new PropertyValueFactory<>("exitGateId"));
        this.ticketsTable.setItems(getTickets());
        this.ticketsTable.getSelectionModel().selectFirst();
    }

    private void initPaymentsTab() throws IOException, NoSuchMethodException, SQLException {
        payments = new SimpleListProperty<>(ParkingDatabase.getInstance().getPayments());
        this.paymentCountLabel.textProperty().bind(paymentsProperty().sizeProperty().asString());
        this.paymentsPaymentIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.paymentsTicketIdCol.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        this.paymentsCCNumCol.setCellValueFactory(new PropertyValueFactory<>("ccNum"));
        this.paymentsCSVCol.setCellValueFactory(new PropertyValueFactory<>("csv"));
        this.paymentsAmountCol.setCellValueFactory(new PropertyValueFactory<>("amountPaid"));
        this.paymentsExpMonthCol.setCellValueFactory(new PropertyValueFactory<>("expMonth"));
        this.paymentsExpYearCol.setCellValueFactory(new PropertyValueFactory<>("expYear"));
        this.paymentsExitGateIdCol.setCellValueFactory(new PropertyValueFactory<>("exitGateId"));
        this.paymentsTable.setItems(getPayments());
        this.paymentsTable.getSelectionModel().selectFirst();
    }

    private void initDailyStatsTab() {

    }

    private void initMonthlyStatsTab() {
        this.monthStatList = new SimpleListProperty<String>(FXCollections.observableArrayList());
        this.monthStatComboBox.setItems(this.monthStatList.get());
    }

    private void initYearlyStatsTab() {
        this.yearStatList = new SimpleListProperty<String>(FXCollections.observableArrayList());
        this.yearStatComboBox.setItems(this.yearStatList.get());
    }

    public ObservableList<EntryGate> getEntryGates() {
        return this.entryGates.get();
    }

    public void setEntryGates(ObservableList<EntryGate> entryGates) {
        this.entryGates.set(entryGates);
    }

    public ListProperty<EntryGate> entryGatesProperty() {
        return this.entryGates;
    }

    public ObservableList<ExitGate> getExitGates() {
        return this.exitGates.get();
    }

    public void setExitGates(ObservableList<ExitGate> exitGates) {
        this.exitGates.set(exitGates);
    }

    public ListProperty<ExitGate> exitGatesProperty() {
        return this.exitGates;
    }

    public ObservableList<Ticket> getTickets() {
        return this.tickets.get();
    }

    public void setTickets(ObservableList<Ticket> tickets) {
        this.tickets.set(tickets);
    }

    public ListProperty<Ticket> ticketsProperty() {
        return this.tickets;
    }

    public ObservableList<Payment> getPayments() {
        return this.payments.get();
    }

    public void setPayments(ObservableList<Payment> tickets) {
        this.payments.set(tickets);
    }

    public ListProperty<Payment> paymentsProperty() {
        return this.payments;
    }

    @FXML
    protected void handleAddEntryGate(ActionEvent event) throws IOException, NoSuchMethodException, SQLException {
        Garage garage = getModel();
        EntryGate gate = new EntryGate(garage.getName());
        ParkingDatabase.getInstance().add(gate);
        getEntryGates().add(gate);
        garage.setEntryGates(garage.getEntryGates() + 1);
        ParkingDatabase.getInstance().merge(garage);
        entryGatesTable.getSelectionModel().selectLast();
    }

    @FXML
    protected void handleAddExitGate(ActionEvent event) throws IOException, NoSuchMethodException, SQLException {
        Garage garage = getModel();
        ExitGate gate = new ExitGate(garage.getName());
        ParkingDatabase.getInstance().add(gate);
        getExitGates().add(gate);
        garage.setExitGates(garage.getExitGates() + 1);
        ParkingDatabase.getInstance().merge(garage);
        entryGatesTable.getSelectionModel().selectLast();
    }

    @FXML
    protected void handleRemoveEntryGate(ActionEvent event) throws SQLException {
        EntryGate selected = entryGatesTable.getSelectionModel().getSelectedItem();
        if(null != selected) {
            ObservableList<Ticket> tickets = getTickets();
            Iterator<Ticket> itr = tickets.iterator();
            while (itr.hasNext()) {
                Ticket ticket = itr.next();
                if (ticket.getEntryGateId() == selected.getId()) {
                    itr.remove();
                    ParkingDatabase.getInstance().remove(ticket);
                    getModel().setOccupancy(getModel().getOccupancy() - 1);
                }
            }
            ParkingDatabase.getInstance().remove(selected);
            getModel().setEntryGates(getModel().getEntryGates() - 1);
            ParkingDatabase.getInstance().merge(getModel());
            getEntryGates().remove(selected);
            EntryGateController controller = selected.getController();
            if (null != controller)
                controller.closeStage();
        }
    }

    @FXML
    protected void handleRemoveExitGate(ActionEvent event) throws SQLException {
        ExitGate selected = exitGatesTable.getSelectionModel().getSelectedItem();
        if(null != selected) {
            ParkingDatabase.getInstance().remove(selected);
            getExitGates().remove(selected);
            getModel().setExitGates(getModel().getExitGates() - 1);
            ParkingDatabase.getInstance().merge(getModel());
            ExitGateController controller = selected.getController();
            if (null != controller)
                controller.closeStage();
        }
    }

    @FXML
    protected void handleRemoveTicket(ActionEvent event) throws SQLException {
        Ticket selected = ticketsTable.getSelectionModel().getSelectedItem();
        if(null != selected) {
            ParkingDatabase.getInstance().remove(selected);
            getTickets().remove(selected);
            selected.getController().closeStage();
            getModel().setOccupancy(getModel().getOccupancy() - 1);
            ParkingDatabase.getInstance().merge(getModel());
        }
    }

    @FXML
    protected void handleRemovePayment(ActionEvent event) throws SQLException {
        Payment selected = paymentsTable.getSelectionModel().getSelectedItem();
        if(null != selected) {
            ParkingDatabase.getInstance().remove(selected);
            getPayments().remove(selected);
        }
    }

    @FXML
    protected void handleShowEntryGate(ActionEvent event) throws IOException, NoSuchMethodException {
        EntryGate selected = entryGatesTable.getSelectionModel().getSelectedItem();
        if (null != selected) {
            EntryGateController controller = selected.getController();
            if (null == controller) {
                controller = new EntryGateController(selected, this);
                selected.setController(controller);
            }
            controller.showStage();
        }
    }

    @FXML
    protected void handleShowExitGate(ActionEvent event) throws IOException, NoSuchMethodException {
        ExitGate selected = exitGatesTable.getSelectionModel().getSelectedItem();
        if (null != selected) {
            ExitGateController controller = selected.getController();
            if (null == controller) {
                controller = new ExitGateController(selected, this);
                selected.setController(controller);
            }
            controller.showStage();
        }
    }

    @FXML
    protected void handleShowTicket(ActionEvent event) throws IOException, NoSuchMethodException {
        Ticket selected = ticketsTable.getSelectionModel().getSelectedItem();
        if (null != selected) {
            TicketController controller = selected.getController();
            if (null == controller) {
                controller = new TicketController(selected, getScene().getWindow());
                selected.setController(controller);
            }
            controller.showStage();
        }
    }

    @FXML
    protected void handleDaySelected(ActionEvent event) {
        LocalDate localDate = dayStatDatePicker.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
    }

    @FXML
    protected void handleMonthSelected(ActionEvent event) {
    }

    @FXML
    protected void handleYearSelected(ActionEvent event) {
    }
}
