package Controller;

import Model.Garage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.annotation.Resource;
import javax.persistence.*;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @PersistenceContext
    EntityManagerFactory factory;

    @Resource
    EntityManager manager;

    @Override
    public void start(Stage stage) throws Exception {
        factory = Persistence.createEntityManagerFactory("PSPersistence");
        manager = factory.createEntityManager();
        EntityTransaction et = manager.getTransaction();
        et.begin();

        Garage g = new Garage("Garage1");
        manager.persist(g);

        et.commit();
    }
}
