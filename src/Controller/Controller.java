package Controller;

import Model.Garage;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Collection;

public class Controller {

    protected Stage stage;
    protected Scene scene;

    public Controller(Stage stage, String view, double width, double height) throws IOException {
        if(null == stage)
            throw new IllegalArgumentException("Stage cannot be null.");
        this.stage = stage;
        if(null == view || view.isEmpty())
            throw new IllegalArgumentException("View cannot be null or empty.");
        FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
        loader.setController(this);
        if(0.0 == width)
            throw new IllegalArgumentException("Width must be > 0.");
        if(0.0 == height)
            throw new IllegalArgumentException("Height must be > 0.");
        this.scene = new Scene(loader.load(), width, height);
        stage.setScene(scene);
    }

    public Controller(String view, double width, double height) throws IOException {
        this(new Stage(), view, width, height);
    }

    public Controller(Window owner, String view, double width, double height) throws IOException {
        this(view, width, height);
        if(null == owner)
            throw new IllegalArgumentException("Owner cannot be null.");
        stage.initOwner(owner);
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }
}
