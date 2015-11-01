package Controller;

import Model.Garage;
import Model.Ticket;

import javax.annotation.Resource;
import javax.persistence.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class ParkingSystemDB {

    @PersistenceContext
    EntityManagerFactory factory;

    @Resource
    EntityManager manager;

    private static ParkingSystemDB instance;

    private ParkingSystemDB() {
        factory = Persistence.createEntityManagerFactory("PSPersistence");
        manager = factory.createEntityManager();
    }

    public static ParkingSystemDB getInstance() {
        if(null == instance) {
            instance = new ParkingSystemDB();
        }
        return instance;
    }

    public void persist(Object obj) {
        EntityTransaction et = manager.getTransaction();
        et.begin();
        manager.persist(obj);
        et.commit();
    }

    public Garage retrieve(String name) {
        return manager.find(Garage.class, name);
    }
}
