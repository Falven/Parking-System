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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class AdminController {

    private Scene scene;
    private Stage stage;
    private Window window;
    private static final ListProperty<Garage> garages = new SimpleListProperty<>();
    private static final MapProperty<Garage, GarageController> garageLookup = new SimpleMapProperty<>();

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

    public AdminController(Stage stage) throws IOException, NoSuchMethodException {
        initUI(stage);
        initGarageTable();
    }

    private void initUI(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 380.0, 400.0);
        this.stage = stage;
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle("Admin Controls");
        this.stage.show();
        this.window = this.scene.getWindow();
    }

    private void initGarageTable() throws IOException, NoSuchMethodException {
        AdminController.setGarages(FXCollections.observableArrayList());
        AdminController.setGarageLookup(FXCollections.observableHashMap());

        this.garageCountLabel.textProperty().bind(AdminController.garagesProperty().sizeProperty().asString());
        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.occupancyColumn.setCellValueFactory(new PropertyValueFactory<>("occupancy"));
        this.maxOccupancyColumn.setCellValueFactory(new PropertyValueFactory<>("maxOccupancy"));
        this.entryGatesCountColumn.setCellValueFactory(cellData -> cellData.getValue().entryGatesProperty().sizeProperty());
        this.exitGatesCountColumn.setCellValueFactory(cellData -> cellData.getValue().exitGatesProperty().sizeProperty());

        List<Garage> garages = Main.getDatabase().getGarages();
        for (Garage garage : garages) {
            AdminController.getGarages().add(garage);
            AdminController.getGarageLookup().put(garage, new GarageController(garage, this.window));
        }
        this.garageTable.setItems(AdminController.getGarages());
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

    public static ObservableMap<Garage, GarageController> getGarageLookup() {
        return AdminController.garageLookup.get();
    }

    public static void setGarageLookup(ObservableMap<Garage, GarageController> garageLookup) {
        AdminController.garageLookup.set(garageLookup);
    }

    public static MapProperty<Garage, GarageController> garageLookupProperty() {
        return AdminController.garageLookup;
    }

    @FXML
    protected void handleGarageFieldAction(ActionEvent event) {
        addButton.fire();
    }

    @FXML
    protected void handleAddGarage(ActionEvent event) throws IOException, NoSuchMethodException {
        String garageName = garageField.getText();
        if(null != Main.getDatabase().findById(Garage.class, garageName)) {
            Main.showError("Add Garage error.", "Error creating garage.", "The provided garage already exists.");
        } else if(null == garageName || garageName.isEmpty()) {
            Main.showError("Garage name error.", "Error creating garage.", "The provided garage name is invalid.");
        } else {
            Garage garage = new Garage(garageName);
            Main.getDatabase().persist(garage);
            AdminController.getGarages().add(garage);
            AdminController.getGarageLookup().put(garage, new GarageController(garage, this.window));
            this.garageTable.getSelectionModel().selectLast();
        }
    }

    @FXML
    protected void handleRemoveGarage(ActionEvent event) {
        Garage selected = (Garage)this.garageTable.getSelectionModel().getSelectedItem();
        Main.getDatabase().remove(selected);
        AdminController.getGarages().remove(selected);
        AdminController.getGarageLookup().remove(selected);
    }

    @FXML
    protected void handleManageGarage(ActionEvent event) throws IOException {
        Garage selected = (Garage)this.garageTable.getSelectionModel().getSelectedItem();
        if(null != selected) {
            GarageController controller = AdminController.getGarageLookup().get(selected);
            if(null != controller) {
                controller.showView();
            }
        }
    }
}
