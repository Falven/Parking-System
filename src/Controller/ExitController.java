package Controller;

import Model.ExitGate;
import Model.Garage;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ExitController {

    private Stage stage;
    private Scene scene;

    private Garage garage;
    private ExitGate gate;
    private ParkingSystemDB database;

    public ExitController(Garage owner) throws IOException {
        this.garage = owner;
        this.database = ParkingSystemDB.getInstance();
        this.gate = new ExitGate(garage);
        this.database.add(gate);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExitView.fxml"));
        loader.setController(this);
        scene = new Scene(loader.load(), 300.0, 500.0);
        stage = new Stage();
        stage.setTitle("Exit Gate #" + gate.getId());
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getWidth() - stage.getWidth());
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }
}
