package Controller;

import Model.Garage;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ParkingDatabase {

    @PersistenceContext
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("PSPersistence");

    @Resource
    private final EntityManager em;

    public ParkingDatabase() {
        em = emf.createEntityManager();
    }

    public <T> T findById(Class<T> c, Object id) {
        return em.find(c, id);
    }

    public void persist(Object object) {
        this.em.getTransaction().begin();
        this.em.persist(object);
        this.em.getTransaction().commit();
    }

    public void remove(Object object) {
        em.remove(em.merge(object));
    }

    public List<Garage> getGarages() {
        return em.createQuery("SELECT g FROM Garage g", Garage.class).getResultList();
    }

    public void close() {
        em.close();
        emf.close();
    }
}
