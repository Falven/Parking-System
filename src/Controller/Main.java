package Controller;

import javafx.application.Application;
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
        AdminController adminController = new AdminController(stage);
    }

    public static EntityManagerFactory getEmf() {
        return emf;
    }
}
