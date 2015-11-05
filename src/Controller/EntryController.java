package Controller;

import Model.EntryGate;
import Model.Garage;
import Model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.IOException;

public class EntryController {

    @Resource
    EntityManager em;

    private final EntryGate bean;
    private Window window;
    private Stage stage;
    private Scene scene;

    @FXML
    private ImageView availabilityImage;

    @FXML
    private Button getTicketButton;

    public EntryController(EntryGate gate, Window owner) throws IOException, NoSuchMethodException {
        this.bean = gate;
        this.em = Main.getEmf().createEntityManager();
        initUI(gate, owner);

        Garage garage = bean.getGarage();
        garage.occupancyProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if(newValue.intValue() >= garage.getMaxOccupancy()) {
                        availabilityImage.setImage(new Image(Main.class.getResourceAsStream("/view/Unavailable.png")));
                        getTicketButton.setDisable(true);
                    } else {
                        availabilityImage.setImage(new Image(Main.class.getResourceAsStream("/view/Available.png")));
                        getTicketButton.setDisable(false);
                    }
                });
    }

    private void initUI(EntryGate gate, Window owner) throws IOException {
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
            Garage garage = bean.getGarage();
            Ticket ticket = new Ticket(bean);
            em.persist(ticket);

            garage.getTickets().add(ticket);
            garage.setOccupancy(garage.getOccupancy() + 1);
            em.merge(garage);
            em.getTransaction().commit();

            TicketController controller = new TicketController(ticket, window);
            controller.showView();
            GarageController.getTicketControllerLookup().put(ticket, controller);
        } finally {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }
    }
}
