package Controller;

import Model.Ticket;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

public class TicketController {

    private Stage stage;
    private Scene scene;
    private Ticket ticket;
    private Window window;

    @FXML
    private Label idLabel;

    @FXML
    private Label assignedDateLabel;

    @FXML
    private Label assignedTimeLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label dueTimeLabel;

    @FXML
    private Label totalLabel;

    public TicketController(Ticket ticket, Window owner) throws IOException {
        this.ticket = ticket;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TicketView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 200.0, 300.0);
        this.stage = new Stage();
        this.stage.setMaxWidth(200.0);
        this.stage.setMaxHeight(300.0);
        this.stage.setTitle("Parking Ticket #" + ticket.getId());
        this.stage.setScene(this.scene);
        this.stage.setX(owner.getX());
        this.stage.setY(owner.getY());
        this.stage.resizableProperty().setValue(Boolean.FALSE);
        this.stage.initModality(Modality.NONE);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();

        idLabel.setText(Integer.toString(ticket.getId()));
        assignedDateLabel.setText(ticket.getAssignedDate().toString());
        assignedTimeLabel.setText(new SimpleDateFormat("hh:mm").format(ticket.getAssignedTime()));
        dueDateLabel.setText(ticket.getDueDate().toString());
        dueTimeLabel.setText(new SimpleDateFormat("hh:mm").format(ticket.getDueTime()));
        totalLabel.setText(NumberFormat.getCurrencyInstance().format(ticket.getAmountDue()));
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
