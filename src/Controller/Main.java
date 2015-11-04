package Controller;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

public class Main extends Application {

    @PersistenceContext
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PSPersistence");

    public static void main(String[] args) {
        launch(args);
        emf.close();
    }

    @Override
    public void start(Stage stage) throws Exception {
        new AdminController(stage);
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }

    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showInfo(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
