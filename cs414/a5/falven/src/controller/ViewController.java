package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public abstract class ViewController {

    private Stage stage;
    private Scene scene;

    protected void initUI(String stageTitle, String gui, double stageWidth, double stageHeight, double maxStageWidth, double maxStageHeight,
                          double stageX, double stageY, boolean resizable, Modality modality, Stage stage, Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(gui));
        loader.setController(this);
        this.scene = new Scene(loader.load(), stageWidth, stageHeight);
        this.stage = (null != stage) ? stage : new Stage();
        this.stage.setMaxWidth(maxStageWidth);
        this.stage.setMaxHeight(maxStageHeight);
        this.stage.setTitle(stageTitle);
        this.stage.setScene(this.scene);
        this.stage.setX(stageX);
        this.stage.setY(stageY);
        this.stage.resizableProperty().setValue(resizable);
        if(null != modality)
            this.stage.initModality(modality);
        if(null != owner) this.stage.initOwner(owner);
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void showStage() {
        stage.show();
    }

    public void closeStage() {
        stage.close();
    }
}
