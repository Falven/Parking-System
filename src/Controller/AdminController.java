package Controller;

import Model.Garage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class AdminController extends Controller {

    @FXML
    private TextField garageField;

    @FXML
    private ListView<GarageController> garageList;

    private ObservableList<GarageController> garageControllers;

    private ParkingSystemDB database;

    public AdminController(Stage stage) throws IOException {
        super(stage, "/view/AdminView.fxml", 200.0, 325.0);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(0.0);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.setTitle("Administrative Controls");
        showView();
    }

    @FXML
    protected void initialize() throws IOException {
        garageControllers = FXCollections.observableArrayList();
        database = ParkingSystemDB.getInstance();

        garageList.setItems(garageControllers);
        Collection<Garage> garages = database.findAllGarages();
        for (Garage garage : garages) {
            garageControllers.add(new GarageController(stage, garage));
        }
    }

    @FXML
    protected void handleAddGarage(ActionEvent event) throws IOException {
        Garage garage = new Garage(garageField.getText());
        database.add(garage);
        garageControllers.add(new GarageController(stage, garage));
    }

    @FXML
    protected void handleViewGarage(ActionEvent event) throws IOException {
        GarageController garageController = garageList.getSelectionModel().getSelectedItem();
        garageController.showView();
    }

    @FXML
    protected void handleRemoveGarage(ActionEvent event) {
        GarageController garageController = garageList.getSelectionModel().getSelectedItem();
        garageController.closeView();
        garageControllers.remove(garageController);
        database.remove(garageController.getGarage());
    }
}
