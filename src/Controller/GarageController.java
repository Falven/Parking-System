package Controller;

import Model.EntryGate;
import Model.ExitGate;
import Model.Garage;
import Model.Ticket;
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
import java.util.List;

public class GarageController extends Controller<Garage> {

    @FXML
    private Label entryGatesCountLabel;

    @FXML
    private Label exitGatesCountLabel;

    @FXML
    private Label ticketCountLabel;

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
    private TableColumn<Ticket, String> ticketsTableIdColumn;

    @FXML
    private TableColumn<Ticket, String> ticketsTableEntryGateColumn;

    @FXML
    private TableColumn<Ticket, String> ticketsTableExitGateColumn;

    @FXML
    private TableColumn<Ticket, String> ticketsTableAssignedDateColumn;

    @FXML
    private TableColumn<Ticket, String> ticketsTableAssignedTimeColumn;

    @FXML
    private TableColumn<Ticket, String> ticketsTableDueDateColumn;

    @FXML
    private TableColumn<Ticket, String> ticketsTableDueTimeColumn;

    @FXML
    private TableColumn<Ticket, String> ticketsTableAmountDueColumn;

    @FXML
    private DatePicker dayStatDatePicker;

    @FXML
    private ComboBox<String> monthStatComboBox;

    @FXML
    private ComboBox<String> yearStatComboBox;

    private ListProperty<EntryGate> entryGates;

    private ListProperty<ExitGate> exitGates;

    private ListProperty<Ticket> tickets;

    private ListProperty<String> monthStatList;

    private ListProperty<String> yearStatList;

    public GarageController(Garage garage, Window owner) throws IOException, NoSuchMethodException {
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
        initDailyStatsTab();
        initMonthlyStatsTab();
        initYearlyStatsTab();
    }

    private void initEntryGatesTab() throws IOException, NoSuchMethodException {
        entryGates = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.entryGatesCountLabel.textProperty().bind(entryGatesProperty().sizeProperty().asString());
        this.entryGatesTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.entryGatesTable.setItems(getEntryGates());
        this.entryGatesTable.getSelectionModel().selectFirst();
    }

    private void initExitGatesTab() throws IOException, NoSuchMethodException {
        exitGates = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.exitGatesCountLabel.textProperty().bind(exitGatesProperty().sizeProperty().asString());
        this.exitGatesTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.exitGatesTable.setItems(getExitGates());
        this.exitGatesTable.getSelectionModel().selectFirst();
    }

    private void initTicketsTab() throws IOException, NoSuchMethodException {
        tickets = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.ticketCountLabel.textProperty().bind(ticketsProperty().sizeProperty().asString());
        this.ticketsTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.ticketsTableAssignedDateColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));
        this.ticketsTableAssignedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTime"));
        this.ticketsTableDueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        this.ticketsTableDueTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dueTime"));
        this.ticketsTableAmountDueColumn.setCellValueFactory(new PropertyValueFactory<>("amountDue"));
        this.ticketsTableEntryGateColumn.setCellValueFactory(new PropertyValueFactory<>("entryGateId"));
        this.ticketsTableExitGateColumn.setCellValueFactory(new PropertyValueFactory<>("exitGateId"));
        this.ticketsTable.setItems(getTickets());
        this.ticketsTable.getSelectionModel().selectFirst();
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

    private ObservableList<EntryGate> getEntryGates() {
        return this.entryGates.get();
    }

    private void setEntryGates(ObservableList<EntryGate> entryGates) {
        this.entryGates.set(entryGates);
    }

    private ListProperty<EntryGate> entryGatesProperty() {
        return this.entryGates;
    }

    private ObservableList<ExitGate> getExitGates() {
        return this.exitGates.get();
    }

    private void setExitGates(ObservableList<ExitGate> exitGates) {
        this.exitGates.set(exitGates);
    }

    private ListProperty<ExitGate> exitGatesProperty() {
        return this.exitGates;
    }

    private ObservableList<Ticket> getTickets() {
        return this.tickets.get();
    }

    private void setTickets(ObservableList<Ticket> tickets) {
        this.tickets.set(tickets);
    }

    private ListProperty<Ticket> ticketsProperty() {
        return this.tickets;
    }

    @FXML
    protected void handleAddEntryGate(ActionEvent event) throws IOException, NoSuchMethodException, SQLException {
        EntryGate gate = new EntryGate(getModel().getName());
        ParkingDatabase.getInstance().add(gate);
        getEntryGates().add(gate);
    }

    @FXML
    protected void handleAddExitGate(ActionEvent event) throws IOException, NoSuchMethodException, SQLException {
        ExitGate gate = new ExitGate(getModel().getName());
        ParkingDatabase.getInstance().add(gate);
        getExitGates().add(gate);
    }

    @FXML
    protected void handleRemoveEntryGate(ActionEvent event) throws SQLException {
        EntryGate selected = entryGatesTable.getSelectionModel().getSelectedItem();
        ParkingDatabase.getInstance().remove(selected);
        List<Ticket> tickets = ParkingDatabase.getInstance().getTickets(selected);
        getModel().setOccupancy(getModel().getOccupancy() - tickets.size());
        ParkingDatabase.getInstance().merge(getModel());
        getEntryGates().remove(selected);
        selected.getController().closeStage();
    }

    @FXML
    protected void handleRemoveExitGate(ActionEvent event) throws SQLException {
        ExitGate selected = exitGatesTable.getSelectionModel().getSelectedItem();
        ParkingDatabase.getInstance().remove(selected);
        getExitGates().remove(selected);
        selected.getController().closeStage();
    }

    @FXML
    protected void handleRemoveTicket(ActionEvent event) throws SQLException {
        Ticket selected = ticketsTable.getSelectionModel().getSelectedItem();
        ParkingDatabase.getInstance().remove(selected);
        getTickets().remove(selected);
        selected.getController().closeStage();
        getModel().setOccupancy(getModel().getOccupancy() - 1);
        ParkingDatabase.getInstance().merge(getModel());
    }

    @FXML
    protected void handleShowEntryGate(ActionEvent event) throws IOException, NoSuchMethodException {
        EntryGate selected = entryGatesTable.getSelectionModel().getSelectedItem();
        if (null != selected) {
            EntryGateController controller = selected.getController();
            if (null == controller) {
                controller = new EntryGateController(selected, getScene().getWindow());
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
