package Controller;

import Model.EntryGate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class EntryController {

    private Stage stage;
    private Scene scene;
    private EntryGate gate;
    private Window window;

    public EntryController(EntryGate gate, Window owner) throws IOException {
        this.gate = gate;

        // Load UI
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EntryView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 200.0, 300.0);
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
