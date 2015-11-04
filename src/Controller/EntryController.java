package Controller;

import Model.EntryGate;
import Model.Garage;
import Model.Ticket;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.adapter.JavaBeanIntegerProperty;
import javafx.beans.property.adapter.JavaBeanIntegerPropertyBuilder;
import javafx.beans.property.adapter.JavaBeanObjectProperty;
import javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;

public class EntryController {

    @Resource
    EntityManager em;

    private final EntryGate bean;
    private Window window;
    private Stage stage;
    private Scene scene;
    private JavaBeanIntegerProperty idProperty;
    private JavaBeanObjectProperty<List<Ticket>> ticketsProperty;
    private JavaBeanObjectProperty<Garage> garageProperty;

    public EntryController(EntryGate gate, Window owner) throws IOException, NoSuchMethodException {
        this.bean = gate;
        this.idProperty = JavaBeanIntegerPropertyBuilder.create().bean(this.bean).name("id").build();
        this.ticketsProperty = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("tickets").build();
        this.garageProperty = JavaBeanObjectPropertyBuilder.create().bean(this.bean).name("garage").build();
        this.em = Main.getEmf().createEntityManager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EntryView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 200.0, 400.0);
        this.stage = new Stage();
        this.stage.setMaxWidth(200.0);
        this.stage.setMaxHeight(400.0);
        this.stage.setTitle("Entry Gate #" + gate.getId());
        this.stage.setScene(this.scene);
        this.stage.setX(owner.getX());
        this.stage.setY(owner.getY());
        this.stage.resizableProperty().setValue(Boolean.FALSE);
        this.stage.initModality(Modality.NONE);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();
    }

    public EntryGate getBean() {
        return this.bean;
    }

    public IntegerProperty idProperty() {
        return this.idProperty;
    }

    public ObjectProperty<List<Ticket>> ticketsProperty() {
        return this.ticketsProperty;
    }

    public ObjectProperty<Garage> garageProperty() {
        return this.garageProperty;
    }

    public Integer getId() {
        return this.idProperty.get();
    }

    public void setId(Integer id) {
        this.idProperty.set(id);
    }

    public List<Ticket> getTickets() {
        return this.ticketsProperty.get();
    }

    public void setTickets(List<Ticket> tickets) {
        this.ticketsProperty.set(tickets);
    }

    public Garage getGarage() {
        return this.garageProperty.get();
    }

    public void setGarage(Garage garage) {
        this.garageProperty.set(garage);
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    @FXML
    protected void handleGetTicket(ActionEvent event) throws IOException, NoSuchMethodException {
        try {
            em.getTransaction().begin();
            Ticket ticket = new Ticket(bean);
            em.persist(ticket);
            bean.getTickets().add(ticket);
            TicketController controller = new TicketController(ticket, window);
//            parent.getTicketControllerList().add(controller);
            getGarage().setOccupancy(getGarage().getOccupancy() + 1);
            controller.showView();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    @Override
    public String toString() {
        return bean.toString();
    }
}
