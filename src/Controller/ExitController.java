package Controller;

import Model.ExitGate;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class ExitController {

    private Stage stage;
    private Scene scene;
    private ExitGate gate;
    private Window window;

    public ExitController(ExitGate gate, Window owner) throws IOException {
        this.gate = gate;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExitView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 350.0, 400.0);
        this.stage = new Stage();
        this.stage.setTitle("Exit Gate #" + gate.getId());
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX(screenBounds.getWidth() - this.stage.getWidth());
        this.stage.setY((screenBounds.getHeight() - this.stage.getHeight()) / 2);
        this.stage.initModality(Modality.NONE);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    public ExitGate getGate() {
        return gate;
    }

    public String toString() {
        return gate.toString();
    }
}
