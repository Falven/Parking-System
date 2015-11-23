package controller;

import model.EntryGate;
import model.Garage;
import model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;
import java.sql.SQLException;

public class EntryGateController extends Controller<EntryGate> {

    private GarageController garageController;

    @FXML
    private ImageView availabilityImage;

    @FXML
    private Button getTicketButton;

    public EntryGateController(EntryGate entryGate, GarageController garageController) throws IOException, NoSuchMethodException {
        super(entryGate);
        this.garageController = garageController;
        Window owner = this.garageController.getScene().getWindow();
        double width = 200.0;
        double height = 400.0;
        initUI(getModel().toString(), "/view/EntryView.fxml", width, height, width, height, owner.getX() - width, owner.getY(), false, Modality.NONE, null, owner);

        Garage garage = this.garageController.getModel();
        garage.occupancyProperty().addListener((observable, oldValue, newValue) -> verifyOccupancy(newValue.intValue(), garage.getMaxOccupancy()));
        verifyOccupancy(garage.getOccupancy(), garage.getMaxOccupancy());
    }

    private void verifyOccupancy(int occupancy, int maxOccupancy) {
        if(occupancy >= maxOccupancy) {
            availabilityImage.setImage(new Image(Main.class.getResourceAsStream("/view/Unavailable.png")));
            getTicketButton.setDisable(true);
        } else {
            availabilityImage.setImage(new Image(Main.class.getResourceAsStream("/view/Available.png")));
            getTicketButton.setDisable(false);
        }
    }

    @FXML
    protected void handleGetTicket(ActionEvent event) throws IOException, NoSuchMethodException, SQLException {
        EntryGate gate = getModel();
        Ticket ticket = new Ticket(gate.getId(), garageController.getModel());
        ParkingDatabase.getInstance().add(ticket);
        Garage garage = this.garageController.getModel();
        garage.setOccupancy(garage.getOccupancy() + 1);
        ParkingDatabase.getInstance().merge(garage);
        this.garageController.getTickets().add(ticket);
        TicketController controller = new TicketController(ticket, getScene().getWindow());
        controller.showStage();
    }
}
