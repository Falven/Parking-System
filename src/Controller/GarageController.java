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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class GarageController {

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

    private ParkingSystemDB database;


    public GarageController(Garage garage, Window owner) throws IOException {
        this.entryControllers = FXCollections.observableArrayList();
        this.exitControllers = FXCollections.observableArrayList();
        this.garage = garage;
        this.database = ParkingSystemDB.getInstance();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GarageView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 400.0, 400.0);
        this.stage = new Stage();
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle(garage.getName() + " Garage.");
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(owner);
        this.stage.show();
        this.window = this.scene.getWindow();

        this.entryGatesList.setItems(entryControllers);
        this.exitGatesList.setItems(exitControllers);
        List<EntryGate> entryGates = garage.getEntryGates();
        for(EntryGate gate : entryGates) {
            entryControllers.add(new EntryController(gate, window));
        }
        List<ExitGate> exitGates = garage.getExitGates();
        for(ExitGate gate : exitGates) {
            exitControllers.add(new ExitController(gate, window));
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
        EntryGate gate = new EntryGate(garage);
        entryControllers.add(new EntryController(gate, window));
        database.add(gate);
    }

    @FXML
    protected void handleAddExitGate(ActionEvent event) throws IOException {
        ExitGate gate = new ExitGate(garage);
        exitControllers.add(new ExitController(gate, window));
        database.add(gate);
    }

    @FXML
    protected void handleRemoveEntryGate(ActionEvent event) {
        EntryController controller = entryGatesList.getSelectionModel().getSelectedItem();
        controller.closeView();
        entryControllers.remove(controller);
        database.remove(controller.getGate());
    }

    @FXML
    protected void handleRemoveExitGate(ActionEvent event) {
        ExitController controller = exitGatesList.getSelectionModel().getSelectedItem();
        controller.closeView();
        exitControllers.remove(controller);
        database.remove(controller.getGate());
    }

    @FXML
    protected void handleShowEntryGate(ActionEvent event) {
        EntryController controller = entryGatesList.getSelectionModel().getSelectedItem();
        controller.showView();
    }

    @FXML
    protected void handleShowExitGate(ActionEvent event) {
        ExitController controller = exitGatesList.getSelectionModel().getSelectedItem();
        controller.showView();
    }

    public String toString() {
        return garage.toString();
    }
}
