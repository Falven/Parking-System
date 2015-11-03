package Controller;

import Model.EntryGate;
import Model.Ticket;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class TicketController {

    private Stage stage;
    private Scene scene;
    private Ticket ticket;
    private Window window;

    public TicketController(Ticket ticket, Window owner) throws IOException {
        this.ticket = ticket;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TicketView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 200.0, 300.0);
        this.stage = new Stage();
        this.stage.setTitle("Parking Ticket #" + ticket.getId());
        this.stage.setScene(this.scene);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        this.stage.setX(screenBounds.getWidth() - this.stage.getWidth());
        this.stage.setY(screenBounds.getHeight() - this.stage.getHeight());
        this.stage.initModality(Modality.NONE);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }

    public Ticket getTicket() {
        return ticket;
    }

    public String toString() {
        return ticket.toString();
    }
}
