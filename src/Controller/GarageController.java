package Controller;

import Model.EntryGate;
import Model.ExitGate;
import Model.Garage;
import Model.Ticket;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class GarageController {

    @Resource
    EntityManager em;

    private Stage stage;
    private Scene scene;
    private Garage garage;
    private Window window;

    @FXML
    private Label entryGatesCountLabel;

    @FXML
    private Label exitGatesCountLabel;

    @FXML
    private Label ticketCountLabel;

    @FXML
    private ListView<EntryController> entryGatesList;

    @FXML
    private ListView<ExitController> exitGatesList;

    @FXML
    private ListView<TicketController> ticketList;

    @FXML
    private DatePicker dayDatePicker;

    @FXML
    private ComboBox monthComboBox;

    @FXML
    private ComboBox yearComboBox;

    private SimpleListProperty<EntryController> entryControllerList;

    private SimpleListProperty<ExitController> exitControllerList;

    private SimpleListProperty<TicketController> ticketControllerList;

    public GarageController(Garage garage, Window owner) throws IOException {
        this.entryControllerList = new SimpleListProperty(FXCollections.<EntryController>observableArrayList());
        this.exitControllerList = new SimpleListProperty(FXCollections.<ExitController>observableArrayList());
        this.ticketControllerList = new SimpleListProperty(FXCollections.<ExitController>observableArrayList());
        this.garage = garage;
        this.em = Main.getEmf().createEntityManager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GarageView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 650.0, 500.0);
        this.stage = new Stage();
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle(garage.getName() + " Garage.");
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();

        this.entryGatesList.setItems(entryControllerList.get());
        this.exitGatesList.setItems(exitControllerList.get());
        this.ticketList.setItems(ticketControllerList.get());
        this.entryGatesCountLabel.textProperty().bind(this.entryControllerList.sizeProperty().asString());
        this.exitGatesCountLabel.textProperty().bind(this.exitControllerList.sizeProperty().asString());
        this.ticketCountLabel.textProperty().bind(this.ticketControllerList.sizeProperty().asString());
        try {
            em.getTransaction().begin();
            List<EntryGate> entryGates = garage.getEntryGates();
            if(null != entryGates) {
                for(EntryGate gate : entryGates) {
                    entryControllerList.get().add(new EntryController(gate, this, window));
                }
            }
            List<ExitGate> exitGates = garage.getExitGates();
            if(null != exitGates) {
                for(ExitGate gate : exitGates) {
                    exitControllerList.get().add(new ExitController(gate, this, window));
                }
            }
            Collection<Ticket> tickets = em.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
            if(null != tickets) {
                for(Ticket ticket : tickets) {
                    ticketControllerList.get().add(new TicketController(ticket, window));
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
        entryGatesList.getSelectionModel().selectFirst();
        exitGatesList.getSelectionModel().selectFirst();
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    public Garage getGarage() {
        return garage;
    }

    public final ObservableList<EntryController> getEntryControllerList() {
        return entryControllerList.get();
    }

    public SimpleListProperty<EntryController> entryControllerListProperty() {
        return entryControllerList;
    }

    public final ObservableList<ExitController> getExitControllerList() {
        return exitControllerList.get();
    }

    public SimpleListProperty<ExitController> exitControllerListProperty() {
        return exitControllerList;
    }

    public final ObservableList<TicketController> getTicketControllerList() {
        return ticketControllerList.get();
    }

    public SimpleListProperty<TicketController> ticketControllerListProperty() {
        return ticketControllerList;
    }

    @FXML
    protected void handleAddEntryGate(ActionEvent event) throws IOException {
        try {
            em.getTransaction().begin();
            EntryGate gate = new EntryGate(garage);
            em.persist(gate);
            entryControllerList.get().add(new EntryController(gate, this, window));
            garage.getEntryGates().add(gate);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleAddExitGate(ActionEvent event) throws IOException {
        try {
            em.getTransaction().begin();
            ExitGate gate = new ExitGate(garage);
            em.persist(gate);
            exitControllerList.get().add(new ExitController(gate, this, window));
            garage.getExitGates().add(gate);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleRemoveEntryGate(ActionEvent event) {
        try {
            em.getTransaction().begin();
            EntryController controller = entryGatesList.getSelectionModel().getSelectedItem();
            EntryGate gate = controller.getGate();
            em.remove(em.merge(gate));
            entryControllerList.get().remove(controller);
            controller.closeView();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleRemoveExitGate(ActionEvent event) {
        try {
            em.getTransaction().begin();
            ExitController controller = exitGatesList.getSelectionModel().getSelectedItem();
            ExitGate gate = controller.getGate();
            em.remove(em.merge(gate));
            exitControllerList.get().remove(controller);
            controller.closeView();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleRemoveTicket(ActionEvent event) {
        try {
            em.getTransaction().begin();
            TicketController controller = ticketList.getSelectionModel().getSelectedItem();
            Ticket ticket = controller.getTicket();
            em.remove(em.merge(ticket));
            ticketControllerList.get().remove(controller);
            controller.closeView();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleShowEntryGate(ActionEvent event) {
        EntryController controller = entryGatesList.getSelectionModel().getSelectedItem();
        if(null != controller) {
            controller.showView();
        }
    }

    @FXML
    protected void handleShowExitGate(ActionEvent event) {
        ExitController controller = exitGatesList.getSelectionModel().getSelectedItem();
        if(null != controller) {
            controller.showView();
        }
    }

    @FXML
    protected void handleShowTicket(ActionEvent event) {
        TicketController controller = ticketList.getSelectionModel().getSelectedItem();
        if(null != controller) {
            controller.showView();
        }
    }

    @FXML
    protected void handleDaySelected(ActionEvent event) throws IOException {
        LocalDate localDate = dayDatePicker.getValue();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
    }

    @FXML
    protected void handleMonthSelected(ActionEvent event) throws IOException {
    }

    @FXML
    protected void handleYearSelected(ActionEvent event) throws IOException {
    }

    public String toString() {
        return garage.toString();
    }
}
