package Controller;

import Model.Garage;
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
import java.sql.SQLException;
import java.util.List;

public class AdminController extends ViewController {

    private static final ListProperty<Garage> garages = new SimpleListProperty<>();

    @FXML
    private TextField garageField;

    @FXML
    private Button addButton;

    @FXML
    private Label garageCountLabel;

    @FXML
    private TableView<Garage> garageTable;

    @FXML
    private TableColumn<Garage, String> nameColumn;

    @FXML
    private TableColumn<Garage, Number> occupancyColumn;

    @FXML
    private TableColumn<Garage, Number> maxOccupancyColumn;

    @FXML
    private TableColumn<Garage, Number> entryGatesCountColumn;

    @FXML
    private TableColumn<Garage, Number> exitGatesCountColumn;

    public AdminController(Stage stage) throws IOException, NoSuchMethodException, SQLException {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double stageWidth = 380.0;
        double stageHeight = 400.0;
        double stageX = (screenBounds.getWidth() - stageWidth) / 2;
        double stageY = (screenBounds.getHeight() - stageHeight) / 2;
        initUI("Admin Controls", "/view/AdminView.fxml", stageWidth, stageHeight, Double.MAX_VALUE, Double.MAX_VALUE, stageX, stageY, true, null, stage, null);
        initGarageTable();
        showStage();
    }

    private void initGarageTable() throws IOException, NoSuchMethodException, SQLException {
        setGarages(FXCollections.observableArrayList(ParkingDatabase.getInstance().getGarages()));
        this.garageCountLabel.textProperty().bind(garagesProperty().sizeProperty().asString());
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.occupancyColumn.setCellValueFactory(new PropertyValueFactory<>("occupancy"));
        this.maxOccupancyColumn.setCellValueFactory(new PropertyValueFactory<>("maxOccupancy"));
        this.entryGatesCountColumn.setCellValueFactory(new PropertyValueFactory<>("entryGates"));
        this.exitGatesCountColumn.setCellValueFactory(new PropertyValueFactory<>("exitGates"));
        this.garageTable.setItems(getGarages());
        this.garageTable.getSelectionModel().selectFirst();
    }

    public static ObservableList<Garage> getGarages() {
        return AdminController.garages.get();
    }

    public static void setGarages(ObservableList<Garage> garages) {
        AdminController.garages.set(garages);
    }

    public static ListProperty<Garage> garagesProperty() {
        return AdminController.garages;
    }

    @FXML
    protected void handleGarageFieldAction(ActionEvent event) {
        addButton.fire();
    }

    @FXML
    protected void handleAddGarage(ActionEvent event) throws IOException, NoSuchMethodException, SQLException {
        String garageName = garageField.getText();
        if(null != ParkingDatabase.getInstance().getGarage(garageName)) {
            Main.showError("Add Garage error.", "Error creating garage.", "The provided garage already exists.");
        } else if(null == garageName || garageName.isEmpty()) {
            Main.showError("Garage name error.", "Error creating garage.", "The provided garage name is invalid.");
        } else {
            Garage garage = new Garage(garageName);
            ParkingDatabase.getInstance().add(garage);
            getGarages().add(garage);
            this.garageTable.getSelectionModel().selectLast();
        }
    }

    @FXML
    protected void handleRemoveGarage(ActionEvent event) throws SQLException {
        Garage selected = this.garageTable.getSelectionModel().getSelectedItem();
        ParkingDatabase.getInstance().remove(selected);
        getGarages().remove(selected);
    }

    @FXML
    protected void handleManageGarage(ActionEvent event) throws IOException, NoSuchMethodException {
        Garage selected = this.garageTable.getSelectionModel().getSelectedItem();
        if(null != selected) {
            GarageController controller = selected.getController();
            if(null == controller) {
                controller = new GarageController(selected, getScene().getWindow());
                selected.setController(controller);
            }
            controller.showStage();
        }
    }
}
