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

    @Resource
    private EntityManager em;
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
        this.em = Main.getEmf().createEntityManager();
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

        List<Garage> garages = em.createQuery("SELECT g FROM Garage g", Garage.class).getResultList();
        if(null != garages) {
            for (Garage garage : garages) {
                AdminController.getGarages().add(garage);
                AdminController.getGarageLookup().put(garage, new GarageController(garage, this.window));
            }
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
        if(null != em.find(Garage.class, garageName)) {
            Main.showError("Add Garage error.", "Error creating garage.", "The provided garage already exists.");
        } else if(null == garageName || garageName.isEmpty()) {
            Main.showError("Garage name error.", "Error creating garage.", "The provided garage name is invalid.");
        } else {
            try {
                em.getTransaction().begin();
                Garage garage = new Garage(garageName);
                em.persist(garage);
                em.getTransaction().commit();

                AdminController.getGarages().add(garage);
                AdminController.getGarageLookup().put(garage, new GarageController(garage, this.window));
                this.garageTable.getSelectionModel().selectLast();
            } finally {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
            }
        }
    }

    @FXML
    protected void handleRemoveGarage(ActionEvent event) {
        try {
            em.getTransaction().begin();
            Garage selected = (Garage)this.garageTable.getSelectionModel().getSelectedItem();
            em.remove(em.merge(selected));

            AdminController.getGarages().remove(selected);
            AdminController.getGarageLookup().remove(selected);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
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
