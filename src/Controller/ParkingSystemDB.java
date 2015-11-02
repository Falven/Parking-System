package Controller;

import Model.Garage;

import javax.annotation.Resource;
import javax.persistence.*;
import java.util.Collection;

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

    public void add(Object obj) {
        em.getTransaction().begin();
        em.persist(obj);
        em.getTransaction().commit();
    }

    public void remove(Object obj) {
        em.getTransaction().begin();
        em.remove(obj);
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
