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

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.Collection;

public class AdminController {

    @Resource
    EntityManager em;

    private Scene scene;
    private Stage stage;
    private Window window;

    @FXML
    private TextField garageField;

    @FXML
    private ListView<GarageController> garageList;

    private ObservableList<GarageController> garageControllers;

    public AdminController(Stage stage) throws IOException {
        this.garageControllers = FXCollections.observableArrayList();
        this.em = Main.getEmf().createEntityManager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 275.0, 375.0);
        this.stage = stage;
        this.stage.setScene(this.scene);
        this.stage.setX(0.0);
        this.stage.setY((Screen.getPrimary().getVisualBounds().getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle("Admin Controls");
        this.stage.show();
        this.window = this.scene.getWindow();

        this.garageList.setItems(garageControllers);

        Collection<Garage> garages = em.createQuery("SELECT g FROM Garage g", Garage.class).getResultList();
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
    protected void handleViewGarage(ActionEvent event) throws IOException {
        garageList.getSelectionModel().getSelectedItem().showView();
    }

    @FXML
    protected void handleRemoveGarage(ActionEvent event) {
        try {
            em.getTransaction().begin();
            GarageController garageController = garageList.getSelectionModel().getSelectedItem();
            em.remove(garageController.getGarage());
            garageControllers.remove(garageController);
            garageController.closeView();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
}
