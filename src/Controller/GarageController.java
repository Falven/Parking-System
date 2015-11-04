package Controller;

import Model.EntryGate;
import Model.ExitGate;
import Model.Garage;
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
    private ListView<EntryController> entryGatesList;

    @FXML
    private ListView<ExitController> exitGatesList;

    @FXML
    private DatePicker dayDatePicker;

    @FXML
    private ComboBox monthComboBox;

    @FXML
    private ComboBox yearComboBox;

    private SimpleListProperty<EntryController> entryControllerListProperty;

    private SimpleListProperty<ExitController> exitControllerListProperty;

    public GarageController(Garage garage, Window owner) throws IOException {
        this.entryControllerListProperty = new SimpleListProperty(FXCollections.<EntryController>observableArrayList());
        this.exitControllerListProperty = new SimpleListProperty(FXCollections.<ExitController>observableArrayList());
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

        this.entryGatesList.setItems(entryControllerListProperty.get());
        this.exitGatesList.setItems(exitControllerListProperty.get());
        this.entryGatesCountLabel.textProperty().bind(this.entryControllerListProperty.sizeProperty().asString());
        this.exitGatesCountLabel.textProperty().bind(this.exitControllerListProperty.sizeProperty().asString());
        try {
            em.getTransaction().begin();
            List<EntryGate> entryGates = garage.getEntryGates();
            if(null != entryGates) {
                for(EntryGate gate : entryGates) {
                    entryControllerListProperty.get().add(new EntryController(gate, window));
                }
            }
            List<ExitGate> exitGates = garage.getExitGates();
            if(null != exitGates) {
                for(ExitGate gate : exitGates) {
                    exitControllerListProperty.get().add(new ExitController(gate, window));
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
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

    @FXML
    protected void handleAddEntryGate(ActionEvent event) throws IOException {
        try {
            em.getTransaction().begin();
            EntryGate gate = new EntryGate(garage);
            em.persist(gate);
            entryControllerListProperty.get().add(new EntryController(gate, window));
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
            exitControllerListProperty.get().add(new ExitController(gate, window));
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
            entryControllerListProperty.get().remove(controller);
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
            exitControllerListProperty.get().remove(controller);
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
