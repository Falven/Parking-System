package Controller;

import Model.Garage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Collection;

public class AdminController {

    private Scene scene;
    private Stage stage;
    private Window window;

    @FXML
    private TextField garageField;

    @FXML
    private ListView<GarageController> garageList;

    private ObservableList<GarageController> garageControllers;

    private ParkingSystemDB database;

    public AdminController(Stage stage) throws IOException {
        this.garageControllers = FXCollections.observableArrayList();
        this.database = ParkingSystemDB.getInstance();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 275.0, 375.0);
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.setX(0.0);
        this.stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle("Admin Controls");
        this.stage.show();
        this.window = this.scene.getWindow();
        garageList.setItems(garageControllers);
        Collection<Garage> garages = database.findAllGarages();
        for (Garage garage : garages) {
            garageControllers.add(new GarageController(garage, window));
        }
    }

    @FXML
    protected void handleAddGarage(ActionEvent event) throws IOException {
        String garageName = garageField.getText();
        if(null == garageName || garageName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Garage name error.");
            alert.setHeaderText("Error creating garage.");
            alert.setContentText("The provided garage name is invalid.");
            alert.showAndWait();
        } else {
            Garage garage = new Garage(garageName);
            database.add(garage);
            garageControllers.add(new GarageController(garage, window));
        }
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
