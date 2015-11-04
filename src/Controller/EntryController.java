package Controller;

import Model.EntryGate;
import Model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.IOException;

public class EntryController {

    @Resource
    EntityManager em;

    @FXML
    private Label entryGateNumber;

    private Stage stage;
    private Scene scene;
    private EntryGate gate;
    private Window window;

    public EntryController(EntryGate gate, Window owner) throws IOException {
        this.gate = gate;
        this.em = Main.getEmf().createEntityManager();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EntryView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 200.0, 350.0);
        this.stage = new Stage();
        this.stage.setTitle("Entry Gate #" + gate.getId());
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX(screenBounds.getWidth() - this.stage.getWidth());
        this.stage.setY(screenBounds.getHeight() - this.stage.getHeight());
        this.stage.initModality(Modality.NONE);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();
    }

    @FXML
    protected void handleGetTicket(ActionEvent event) throws IOException {
        try {
            em.getTransaction().begin();
            Ticket ticket = new Ticket(gate);
            em.persist(ticket);
            gate.getTickets().add(ticket);
            TicketController controller = new TicketController(ticket, window);
            controller.showView();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    public EntryGate getGate() {
        return gate;
    }

    public String toString() {
        return gate.toString();
    }
}
