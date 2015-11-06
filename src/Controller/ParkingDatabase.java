package Controller;

import Model.Garage;
import Model.Ticket;

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
        this.em.getTransaction().begin();
        em.remove(em.merge(object));
        this.em.getTransaction().commit();
    }

    public <T> T merge(T var) {
        this.em.getTransaction().begin();
        T result = em.merge(var);
        this.em.getTransaction().commit();
        return result;
    }

    public void refresh(Object object) {
        this.em.getTransaction().begin();
        this.em.refresh(object);
        this.em.getTransaction().commit();
    }

    public List<Garage> getGarages() {
        return em.createQuery("SELECT g FROM Garage g", Garage.class).getResultList();
    }

    public List<Ticket> getTickets(String garageName) {
        return em.createQuery("SELECT t FROM Ticket t WHERE t.garage.name LIKE :garageName", Ticket.class).setParameter("garageName", garageName).getResultList();
    }

    public void close() {
        em.close();
        emf.close();
    }
}
