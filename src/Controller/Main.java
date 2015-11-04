package Controller;

import Model.EntryGate;
import Model.ExitGate;
import Model.Garage;
import Model.Ticket;
import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class Main extends Application {

    private static SimpleObjectProperty<AdminController> adminControllerProperty;
    private static final SimpleMapProperty<Garage, GarageController> garageControllerMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
    private static final SimpleMapProperty<EntryGate, EntryController> entryControllerMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
    private static final SimpleMapProperty<ExitGate, ExitController> exitControllerMap = new SimpleMapProperty<>(FXCollections.observableHashMap());
    private static final SimpleMapProperty<Ticket, TicketController> ticketControllerMap = new SimpleMapProperty<>(FXCollections.observableHashMap());

    @PersistenceContext
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PSPersistence");

    public static void main(String[] args) {
        launch(args);
        emf.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        adminControllerProperty = new SimpleObjectProperty<>(new AdminController(stage));
    }

    public static AdminController getAdminController() {
        return Main.adminControllerProperty.get();
    }

    public static SimpleObjectProperty<AdminController> adminControllerProperty() {
        return Main.adminControllerProperty;
    }

    public static final ObservableMap<Garage, GarageController> getGarageControllerMap() {
        return Main.garageControllerMap.get();
    }

    public static SimpleMapProperty<Garage, GarageController> garageControllerListProperty() {
        return Main.garageControllerMap;
    }

    public static final ObservableMap<EntryGate, EntryController> getEntryControllerMap() {
        return Main.entryControllerMap.get();
    }

    public static SimpleMapProperty<EntryGate, EntryController> entryControllerListProperty() {
        return Main.entryControllerMap;
    }

    public static final ObservableMap<ExitGate, ExitController> getExitControllerMap() {
        return Main.exitControllerMap.get();
    }

    public static SimpleMapProperty<ExitGate, ExitController> exitControllerListProperty() {
        return Main.exitControllerMap;
    }

    public static final ObservableMap<Ticket, TicketController> getTicketControllerMap() {
        return Main.ticketControllerMap.get();
    }

    public static SimpleMapProperty<Ticket, TicketController> ticketControllerListProperty() {
        return Main.ticketControllerMap;
    }

    public static EntityManagerFactory getEmf() {
        return Main.emf;
    }

    public static void setEmf(EntityManagerFactory emf) {
        Main.emf = emf;
    }

    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showInfo(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
