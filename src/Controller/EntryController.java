package Controller;

import Model.EntryGate;
import Model.Garage;
import Model.Ticket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class EntryController {

    private Stage stage;
    private Scene scene;

    private Garage garage;
    private EntryGate gate;
    private ParkingSystemDB database;

    public EntryController(Garage owner) throws IOException {
        this.garage = owner;
        this.database = ParkingSystemDB.getInstance();
        this.gate = new EntryGate(garage);
//        this.database.add(gate);

        // Load UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EntryView.fxml"));
        loader.setController(this);
        scene = new Scene(loader.load(), 300.0, 500.0);
        stage = new Stage();
        stage.setTitle("Entry Gate #" + gate.getId());
        stage.setScene(scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getWidth() - stage.getWidth());
        stage.setY(screenBounds.getHeight() - stage.getHeight());
        stage.show();
    }

    @FXML
    protected void handleGetTicket(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TicketView.fxml"));
            TicketController ticketController = new TicketController();
            loader.setController(ticketController);
            Parent root = loader.load();
            Scene ticketScene = new Scene(root, 200.0, 300.0);
            Stage ticketStage = new Stage();
            ticketStage.setTitle("Your Parking Ticket.");
            ticketStage.setScene(ticketScene);
            ticketStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
