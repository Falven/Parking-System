package Controller;

import Model.EntryGate;
import Model.ExitGate;
import Model.Garage;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.Collection;
import java.util.List;

public class ParkingSystemDB {

    @PersistenceContext
    EntityManagerFactory factory;

    @Resource
    EntityManager em;

    private static ParkingSystemDB instance;

    private ParkingSystemDB() {
        factory = Persistence.createEntityManagerFactory("PSPersistence");
        em = factory.createEntityManager();
    }

    public static ParkingSystemDB getInstance() {
        if(null == instance) {
            instance = new ParkingSystemDB();
        }
        return instance;
    }

    public void add(Garage garage) {
        em.getTransaction().begin();
        em.persist(garage);
        em.getTransaction().commit();
    }

    public void remove(Garage garage) {
        em.getTransaction().begin();
        em.remove(garage);
        em.getTransaction().commit();
    }

    public Garage findGarage(String name) {
        return em.find(Garage.class, name);
    }

    public Collection<Garage> findAllGarages() {
        Query query = em.createQuery("SELECT g FROM Garage g");
        return (Collection<Garage>) query.getResultList();
    }
}
