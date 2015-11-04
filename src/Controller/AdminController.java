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
import java.util.List;

public class AdminController {

    @Resource
    EntityManager em;

    private Scene scene;
    private Stage stage;
    private Window window;
    private ObservableList<GarageController> garageControllers;

    @FXML
    private TextField garageField;

    @FXML
    private Button addButton;

    @FXML
    private TableView garageTable;

    @FXML
    private TableColumn<GarageController, String> nameColumn;

    @FXML
    private TableColumn<GarageController, Integer> occupancyColumn;

    @FXML
    private TableColumn<GarageController, Integer> entryGatesColumn;

    @FXML
    private TableColumn<GarageController, Integer> exitGatesColumn;

    public AdminController(Stage stage) throws IOException, NoSuchMethodException {
        this.em = Main.getEmf().createEntityManager();
        this.garageControllers = FXCollections.observableArrayList();

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

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        occupancyColumn.setCellValueFactory(new PropertyValueFactory<>("occupancy"));
        entryGatesColumn.setCellValueFactory(new PropertyValueFactory<>("entryGateCount"));
        exitGatesColumn.setCellValueFactory(new PropertyValueFactory<>("exitGateCount"));

        List<Garage> garages = em.createQuery("SELECT g FROM Garage g", Garage.class).getResultList();
        for(Garage garage : garages) {
            garageControllers.add(new GarageController(garage, window));
        }
        this.garageTable.setItems(garageControllers);
        this.garageTable.getSelectionModel().selectFirst();
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
                garageControllers.add(new GarageController(garage, window));
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
            GarageController selected = (GarageController)this.garageTable.getSelectionModel().getSelectedItem();
            em.remove(em.merge(selected));
            garageControllers.remove(selected);
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @FXML
    protected void handleManageGarage(ActionEvent event) throws IOException {
        GarageController controller = (GarageController)this.garageTable.getSelectionModel().getSelectedItem();
        if(null != controller) {
            controller.showView();
        }
    }
}
