package Controller;

import Model.Garage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class GarageController {

    private Scene scene;
    private Stage stage;
    private Garage garage;

    public GarageController(Garage garage) throws IOException {
        this.garage = garage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/GarageView.fxml"));
        loader.setController(this);
        scene = new Scene(loader.load(), 400.0, 400.0);
        stage = new Stage();
        stage.setTitle(garage.getName() + " Garage.");
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        showView();
    }

    public Garage getGarage() {
        return garage;
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    public String toString() {
        return garage.toString();
    }
}
