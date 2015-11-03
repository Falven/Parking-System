package Controller;

import Model.Garage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class GarageController {

    private Stage stage;
    private Scene scene;
    private Garage garage;

    public GarageController(Garage garage, Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GarageView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 400.0, 400.0);
        this.stage = new Stage();
        this.stage.setScene(scene);
        this.garage = garage;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX((screenBounds.getWidth() - this.stage.getWidth()) / 2);
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.setTitle(garage.getName() + " Garage.");
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(owner);
        this.stage.show();
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    public Garage getGarage() {
        return garage;
    }

    public String toString() {
        return garage.toString();
    }
}
