package Controller;

import Model.Garage;
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

public class AdminController {

    @Resource
    EntityManager em;

    private Scene scene;
    private Stage stage;
    private Window window;

    @FXML
    private TextField garageField;

    @FXML
    private Button addButton;

    @FXML
    private TableView garageTable;

    @FXML
    private TableColumn<Garage, String> nameColumn;

    @FXML
    private TableColumn<Garage, Integer> occupancyColumn;

    private ObservableList<Garage> garages;

    ObservableMap<Garage, GarageController> controllerMap;

    public AdminController(Stage stage) throws IOException {
        this.em = Main.getEmf().createEntityManager();
        this.garages = FXCollections.observableArrayList(em.createQuery("SELECT g FROM Garage g", Garage.class).getResultList());
        this.controllerMap = Main.getGarageControllerMap();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 275.0, 375.0);
        this.stage = stage;
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle("Admin Controls");
        this.stage.show();
        this.window = this.scene.getWindow();

        this.nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.occupancyColumn.setCellValueFactory(new PropertyValueFactory<>("occupancy"));
        this.garageTable.setItems(garages);
        this.garageTable.getSelectionModel().selectFirst();
    }

    @FXML
    protected void handleGarageFieldAction(ActionEvent event) {
        addButton.fire();
    }

    @FXML
    protected void handleAddGarage(ActionEvent event) throws IOException {
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
                garages.add(garage);
                controllerMap.put(garage, new GarageController(garage, window));
                em.getTransaction().commit();
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
            garages.remove(selected);
            controllerMap.get(selected).closeView();
            controllerMap.remove(selected);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleManageGarage(ActionEvent event) throws IOException {
        GarageController controller = controllerMap.get((Garage)this.garageTable.getSelectionModel().getSelectedItem());
        if(null != controller) {
            controller.showView();
        }
    }
}
