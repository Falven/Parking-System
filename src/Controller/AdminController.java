package Controller;

import Model.Garage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collection;

public class AdminController {

    @FXML
    private TextField garageField;

    @FXML
    private ListView<GarageController> garageList;

    private ObservableList<GarageController> garageControllers;

    private ParkingSystemDB database;

    public AdminController(Stage stage) throws IOException {
        database = ParkingSystemDB.getInstance();
        garageControllers = FXCollections.observableArrayList();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
        loader.setController(this);
        Scene scene = new Scene(loader.load(), 200.0, 325.0);
        stage.setTitle("Administrative Controls");
        stage.setScene(scene);
        stage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(0.0);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    protected void initialize() throws IOException {
        garageList.setItems(garageControllers);
        Collection<Garage> garages = database.findAllGarages();
        for (Garage garage : garages) {
            garageControllers.add(new GarageController(garage));
        }
    }

    @FXML
    protected void handleAddGarage(ActionEvent event) throws IOException {
        Garage garage = new Garage(garageField.getText());
        database.add(garage);
        garageControllers.add(new GarageController(garage));
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
