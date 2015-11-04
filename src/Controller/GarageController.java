package Controller;

import Model.EntryGate;
import Model.ExitGate;
import Model.Garage;
import Model.Ticket;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.adapter.*;
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
    private final Garage bean;
    private Window window;
    private JavaBeanStringProperty name;
    private JavaBeanIntegerProperty occupancy;
    private JavaBeanObjectProperty<List<EntryGate>> entryGates;
    private JavaBeanIntegerProperty entryGateCount;
    private JavaBeanObjectProperty<List<ExitGate>> exitGates;
    private JavaBeanIntegerProperty exitGateCount;

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
    private DatePicker dayStatDatePicker;

    @FXML
    private ComboBox monthStatComboBox;

    @FXML
    private ComboBox yearStatComboBox;

    private SimpleListProperty<EntryController> entryControllerList;

    private SimpleListProperty<ExitController> exitControllerList;

    private SimpleListProperty<TicketController> ticketControllerList;

    private SimpleListProperty<String> monthStatList;

    private SimpleListProperty<String> yearStatList;

    public GarageController(Garage garage, Window owner) throws IOException, NoSuchMethodException {
        this.bean = garage;
        this.name = JavaBeanStringPropertyBuilder.create().bean(this.bean).name("name").build();
        this.occupancy = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("occupancy").build();
        this.entryGates = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("entryGates").build();
        this.entryGateCount = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("entryGateCount").build();
        this.exitGates = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("exitGates").build();
        this.exitGateCount = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("exitGateCount").build();
        this.entryControllerList = new SimpleListProperty(FXCollections.observableArrayList());
        this.exitControllerList = new SimpleListProperty(FXCollections.observableArrayList());
        this.ticketControllerList = new SimpleListProperty(FXCollections.observableArrayList());
        this.monthStatList = new SimpleListProperty(FXCollections.observableArrayList());
        this.yearStatList = new SimpleListProperty(FXCollections.observableArrayList());
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

        this.entryGatesList.setItems(this.entryControllerList.get());
        this.exitGatesList.setItems(this.exitControllerList.get());
        this.ticketList.setItems(this.ticketControllerList.get());
        this.entryGatesCountLabel.textProperty().bind(this.entryControllerList.sizeProperty().asString());
        this.exitGatesCountLabel.textProperty().bind(this.exitControllerList.sizeProperty().asString());
        this.ticketCountLabel.textProperty().bind(this.ticketControllerList.sizeProperty().asString());
        try {
            em.getTransaction().begin();
            List<EntryGate> entryGates = garage.getEntryGates();
            if(null != entryGates) {
                for(EntryGate gate : entryGates) {
                    entryControllerList.get().add(new EntryController(gate, window));
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

        this.monthStatComboBox.setItems(this.monthStatList.get());
        this.yearStatComboBox.setItems(this.yearStatList.get());
        Collection<Ticket> tickets = em.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    public ObservableList<EntryController> getEntryControllerList() {
        return entryControllerList.get();
    }

    public SimpleListProperty<EntryController> entryControllerListProperty() {
        return entryControllerList;
    }

    public ObservableList<ExitController> getExitControllerList() {
        return exitControllerList.get();
    }

    public SimpleListProperty<ExitController> exitControllerListProperty() {
        return exitControllerList;
    }

    public ObservableList<TicketController> getTicketControllerList() {
        return ticketControllerList.get();
    }

    public SimpleListProperty<TicketController> ticketControllerListProperty() {
        return ticketControllerList;
    }

    public Garage getBean() {
        return this.bean;
    }

    public IntegerProperty occupancyProperty() {
        return this.occupancy;
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public ObjectProperty<List<EntryGate>> entryGatesProperty() {
        return this.entryGates;
    }

    public ObjectProperty<List<ExitGate>> exitGatesProperty() {
        return this.exitGates;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String value) {
        this.name.set(value);
    }

    public int getOccupancy() {
        return this.occupancy.get();
    }

    public void setOccupancy(int value) {
        this.occupancy.set(value);
    }

    public List<EntryGate> getEntryGates() {
        return this.entryGates.get();
    }

    public void setEntryGates(List<EntryGate> value) {
        this.entryGates.set(value);
    }

    public int getEntryGateCount() {
        return this.entryGateCount.get();
    }

    public void setEntryGateCount(int value) {
        this.entryGateCount.set(value);
    }

    public List<ExitGate> getExitGates() {
        return this.exitGates.get();
    }

    public void setExitGates(List<ExitGate> value) {
        this.exitGates.set(value);
    }

    public int getExitGateCount() {
        return this.exitGateCount.get();
    }

    public void setExitGateCount(int value) {
        this.exitGateCount.set(value);
    }

    @FXML
    protected void handleAddEntryGate(ActionEvent event) throws IOException, NoSuchMethodException {
        try {
            em.getTransaction().begin();
            EntryGate gate = new EntryGate(bean);
            em.persist(gate);
            entryControllerList.get().add(new EntryController(gate, window));
            bean.getEntryGates().add(gate);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleAddExitGate(ActionEvent event) throws IOException, NoSuchMethodException {
        try {
            em.getTransaction().begin();
            ExitGate gate = new ExitGate(bean);
            em.persist(gate);
            exitControllerList.get().add(new ExitController(gate, this, window));
            bean.getExitGates().add(gate);
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
            EntryGate gate = controller.getBean();
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
            ExitGate gate = controller.getBean();
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
