package Controller;

import Model.Garage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.IOException;

public class Main extends Application {

    private ParkingSystemDB database;

    private Stage entryStage;
    private Stage exitStage;

    private Scene entryScene;
    private Scene exitScene;

    public static void main(String[] args) {
        launch(args);
    }

    @PersistenceContext
    EntityManagerFactory factory;

    @Resource
    EntityManager manager;

    @Override
    public void start(Stage stage) throws Exception {
//        database = new ParkingSystemDB();
        factory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        EntityManager em = factory.createEntityManager();
        manager.getTransaction().begin();

        Garage g = new Garage("Garage1");
        em.persist(g);

        manager.getTransaction().commit();
    }
}
