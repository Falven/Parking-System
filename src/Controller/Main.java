package Controller;

import Model.Garage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private ParkingSystemDB database;

    private Stage adminStage;
    private Scene adminScene;
    private AdminController adminController;

    public static void main(String[] args) {
        launch(args);
    }

    public Main() {
        database = ParkingSystemDB.getInstance();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Garage garage = database.retrieve("Safeway");
        if(null == garage) {
            garage = new Garage("Safeway");
            database.persist(garage);
        }

        EntryController testController = new EntryController(garage);
        ExitController testController1 = new ExitController(garage);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminView.fxml"));
            adminController = new AdminController();
            loader.setController(adminController);
            adminScene = new Scene(loader.load(), 500.0, 500.0);
            adminStage = stage;
            adminStage.setTitle("Administrative Controls");
            adminStage.setScene(adminScene);
            adminStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        adminStage.setX(0.0);
        adminStage.setY((screenBounds.getHeight() - adminStage.getHeight()) / 2);
    }
}
