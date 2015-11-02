package Controller;

import Model.Garage;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Window;

import java.io.IOException;

public class GarageController extends Controller {

    private Garage garage;

    public GarageController(Window owner, Garage garage) throws IOException {
        super(owner, "/view/GarageView.fxml", 400.0, 400.0);
        this.garage = garage;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.setTitle(garage.getName() + " Garage.");
    }

    public Garage getGarage() {
        return garage;
    }

    public String toString() {
        return garage.toString();
    }
}
