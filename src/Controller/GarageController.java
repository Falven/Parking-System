package Controller;

import Model.EntryGate;
import Model.ExitGate;
import Model.Garage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class GarageController {

    @Resource
    EntityManager em;

    private Stage stage;
    private Scene scene;
    private Garage garage;
    private Window window;

    @FXML
    private Label entryGatesLabel;

    @FXML
    private Label exitGatesLabel;

    @FXML
    private ListView<EntryController> entryGatesList;

    @FXML
    private ListView<ExitController> exitGatesList;

    private ObservableList<EntryController> entryControllers;

    private ObservableList<ExitController> exitControllers;

    public GarageController(Garage garage, Window owner) throws IOException {
        this.entryControllers = FXCollections.observableArrayList();
        this.exitControllers = FXCollections.observableArrayList();
        this.garage = garage;
        this.em = Main.getEmf().createEntityManager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GarageView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 400.0, 400.0);
        this.stage = new Stage();
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle(garage.getName() + " Garage.");
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();

        this.entryGatesList.setItems(entryControllers);
        this.exitGatesList.setItems(exitControllers);
        try {
            em.getTransaction().begin();
            List<EntryGate> entryGates = garage.getEntryGates();
            if(null != entryGates) {
                for(EntryGate gate : entryGates) {
                    entryControllers.add(new EntryController(gate, window));
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
            entryControllers.add(new EntryController(gate, window));
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
    }

    @FXML
    protected void handleRemoveEntryGate(ActionEvent event) {
        try {
            em.getTransaction().begin();
            EntryController controller = entryGatesList.getSelectionModel().getSelectedItem();
            EntryGate toBeRemoved = em.merge(controller.getGate());
            em.remove(toBeRemoved);
            entryControllers.remove(controller);
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
    }

    @FXML
    protected void handleShowEntryGate(ActionEvent event) {
        EntryController controller = entryGatesList.getSelectionModel().getSelectedItem();
        controller.showView();
    }

    @FXML
    protected void handleShowExitGate(ActionEvent event) {
    }

    public String toString() {
        return garage.toString();
    }
}
