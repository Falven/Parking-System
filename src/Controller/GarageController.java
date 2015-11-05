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

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GarageController {

    @Resource
    EntityManager em;
    private Stage stage;
    private Scene scene;
    private final Garage bean;
    private Window window;

    private static final MapProperty<EntryGate, EntryController> entryControllerLookup = new SimpleMapProperty<>();

    private static final MapProperty<ExitGate, ExitController> exitControllerLookup = new SimpleMapProperty<>();

    private static final MapProperty<Ticket, TicketController> ticketControllerLookup = new SimpleMapProperty<>();

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

    private SimpleListProperty<String> monthStatList;

    private SimpleListProperty<String> yearStatList;

    public GarageController(Garage garage, Window owner) throws IOException, NoSuchMethodException {
        this.bean = garage;
        this.em = Main.getEmf().createEntityManager();

        initUI(owner);
        initEntryGatesTab();
        initExitGatesTab();
        initTicketsTab();
        initDailyStatsTab();
        initMonthlyStatsTab();
        initYearlyStatsTab();
    }

    private void initUI(Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GarageView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 650.0, 500.0);
        this.stage = new Stage();
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle(bean.getName() + " garage.");
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();
    }

    private void initEntryGatesTab() throws IOException, NoSuchMethodException {
        GarageController.setEntryControllerLookup(FXCollections.observableHashMap());

        this.entryGatesCountLabel.textProperty().bind(this.bean.entryGatesProperty().sizeProperty().asString());
        this.entryGatesTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        ObservableList<EntryGate> entryGates = (ObservableList<EntryGate>)bean.getEntryGates();
        for(EntryGate gate : entryGates) {
            getEntryControllerLookup().put(gate, new EntryController(gate, window));
        }
        this.entryGatesTable.setItems(entryGates);
        this.entryGatesTable.getSelectionModel().selectFirst();
    }

    private void initExitGatesTab() throws IOException, NoSuchMethodException {
        GarageController.setExitControllerLookup(FXCollections.observableHashMap());

        this.exitGatesCountLabel.textProperty().bind(this.bean.entryGatesProperty().sizeProperty().asString());
        this.exitGatesTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        ObservableList<ExitGate> exitGates = (ObservableList<ExitGate>)bean.getExitGates();
        for(ExitGate gate : exitGates) {
            getExitControllerLookup().put(gate, new ExitController(gate, this, window));
        }
        this.exitGatesTable.setItems(exitGates);
        this.exitGatesTable.getSelectionModel().selectFirst();
    }

    private void initTicketsTab() throws IOException, NoSuchMethodException {
        GarageController.setTicketControllerLookup(FXCollections.observableHashMap());

        this.ticketCountLabel.textProperty().bind(this.bean.ticketsProperty().sizeProperty().asString());
        this.ticketsTableIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.ticketsTableEntryGateColumn.setCellValueFactory(new PropertyValueFactory<>("entryGate"));
        this.ticketsTableExitGateColumn.setCellValueFactory(new PropertyValueFactory<>("exitGate"));
        this.ticketsTableAssignedDateColumn.setCellValueFactory(new PropertyValueFactory<>("assignedDate"));
        this.ticketsTableAssignedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("assignedTime"));
        this.ticketsTableDueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        this.ticketsTableDueTimeColumn.setCellValueFactory(new PropertyValueFactory<>("dueTime"));
        this.ticketsTableAmountDueColumn.setCellValueFactory(new PropertyValueFactory<>("amountDue"));

        ObservableList<Ticket> tickets = (ObservableList<Ticket>)bean.getTickets();
        for(Ticket ticket : tickets) {
            getTicketControllerLookup().put(ticket, new TicketController(ticket, window));
        }
        this.ticketsTable.setItems(tickets);
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

    public Garage getBean() {
        return this.bean;
    }

    public static ObservableMap<EntryGate, EntryController> getEntryControllerLookup() {
        return GarageController.entryControllerLookup.get();
    }

    public static void setEntryControllerLookup(ObservableMap<EntryGate, EntryController> entryControllerLookup) {
        GarageController.entryControllerLookup.set(entryControllerLookup);
    }

    public static MapProperty<EntryGate, EntryController> entryControllerLookupProperty() {
        return GarageController.entryControllerLookup;
    }

    public static ObservableMap<ExitGate, ExitController> getExitControllerLookup() {
        return GarageController.exitControllerLookup.get();
    }

    public static void setExitControllerLookup(ObservableMap<ExitGate, ExitController> exitControllerLookup) {
        GarageController.exitControllerLookup.set(exitControllerLookup);
    }

    public static MapProperty<ExitGate, ExitController> exitControllerLookupProperty() {
        return GarageController.exitControllerLookup;
    }

    public static ObservableMap<Ticket, TicketController> getTicketControllerLookup() {
        return GarageController.ticketControllerLookup.get();
    }

    public static void setTicketControllerLookup(ObservableMap<Ticket, TicketController> ticketControllerLookup) {
        GarageController.ticketControllerLookup.set(ticketControllerLookup);
    }

    public static MapProperty<Ticket, TicketController> ticketControllerLookupProperty() {
        return GarageController.ticketControllerLookup;
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    @FXML
    protected void handleAddEntryGate(ActionEvent event) throws IOException, NoSuchMethodException {
        try {
            this.em.getTransaction().begin();
            Garage garage = getBean();
            EntryGate gate = new EntryGate(garage);
            this.em.persist(gate);
            garage.getEntryGates().add(gate);
            this.em.merge(garage);
            getEntryControllerLookup().put(gate, new EntryController(gate, window));
            this.em.getTransaction().commit();
        } finally {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleAddExitGate(ActionEvent event) throws IOException, NoSuchMethodException {
        try {
            this.em.getTransaction().begin();
            Garage garage = getBean();
            ExitGate gate = new ExitGate(garage);
            this.em.persist(gate);
            garage.getExitGates().add(gate);
            this.em.merge(garage);
            getExitControllerLookup().put(gate, new ExitController(gate, this, window));
            this.em.getTransaction().commit();
        } finally {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleRemoveEntryGate(ActionEvent event) {
        try {
            this.em.getTransaction().begin();
            EntryGate selected = entryGatesTable.getSelectionModel().getSelectedItem();
            this.em.remove(em.merge(selected));

            GarageController.getEntryControllerLookup().remove(selected).closeView();
            this.em.getTransaction().commit();
        } finally {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleRemoveExitGate(ActionEvent event) {
        try {
            this.em.getTransaction().begin();
            ExitGate selected = exitGatesTable.getSelectionModel().getSelectedItem();
            this.em.remove(em.merge(selected));

            GarageController.getExitControllerLookup().remove(selected).closeView();
            this.em.getTransaction().commit();
        } finally {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleRemoveTicket(ActionEvent event) {
        try {
            this.em.getTransaction().begin();
            Ticket selected = ticketsTable.getSelectionModel().getSelectedItem();
            this.em.remove(em.merge(selected));

            Garage garage = selected.getGarage();
            garage.setOccupancy(garage.getOccupancy() - 1);
            this.em.merge(garage);

            GarageController.getTicketControllerLookup().remove(selected).closeView();
            this.em.getTransaction().commit();
        } finally {
            if (this.em.getTransaction().isActive()) {
                this.em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleShowEntryGate(ActionEvent event) {
        EntryGate entryGate = entryGatesTable.getSelectionModel().getSelectedItem();
        if(null != entryGate) {
            GarageController.getEntryControllerLookup().get(entryGate).showView();
        }
    }

    @FXML
    protected void handleShowExitGate(ActionEvent event) {
        ExitGate exitGate = exitGatesTable.getSelectionModel().getSelectedItem();
        if(null != exitGate) {
            GarageController.getExitControllerLookup().get(exitGate).showView();
        }
    }

    @FXML
    protected void handleShowTicket(ActionEvent event) {
        Ticket ticket = ticketsTable.getSelectionModel().getSelectedItem();
        if(null != ticket) {
            GarageController.getTicketControllerLookup().get(ticket).showView();
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
