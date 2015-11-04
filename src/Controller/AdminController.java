package Controller;

import Model.Garage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
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
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX(0.0);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle("Admin Controls");
        this.stage.show();
        this.window = this.scene.getWindow();

        this.garageList.setItems(garageControllers);

        TypedQuery query = em.createQuery("SELECT g FROM Garage g", Garage.class);
        Collection<Garage> garages = query.getResultList();
        for (Garage garage : garages) {
            garageControllers.add(new GarageController(garage, window));
        }
    }

    @FXML
    protected void handleAddGarage(ActionEvent event) throws IOException {
        String garageName = garageField.getText();
        if(null == garageName || garageName.isEmpty()) {
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
    protected void handleViewGarage(ActionEvent event) throws IOException {
        GarageController controller = garageList.getSelectionModel().getSelectedItem();
        if(null != controller) {
            controller.showView();
        }
    }

    @FXML
    protected void handleRemoveGarage(ActionEvent event) {
        try {
            em.getTransaction().begin();
            GarageController controller = garageList.getSelectionModel().getSelectedItem();
            Garage toBeRemoved = controller.getGarage();
            em.remove(em.merge(toBeRemoved));
            garageControllers.remove(controller);
            controller.closeView();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
}
