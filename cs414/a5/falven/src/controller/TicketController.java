package controller;

import model.Ticket;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Window;

import java.io.IOException;

public class TicketController extends Controller<Ticket> {

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
    private Label amountDueLabel;

    public TicketController(Ticket ticket, Window owner) throws IOException, NoSuchMethodException {
        super(ticket);
        double stageWidth = 200.0;
        double stageHeight = 300.0;
        initUI("Parking Ticket #" + getModel().getId(), "/view/TicketView.fxml", stageWidth, stageHeight, stageWidth, stageHeight,
                owner.getX(), owner.getY() + 30.0, false, Modality.NONE, null, owner);
        Ticket model = getModel();
        idLabel.textProperty().bind(getModel().idProperty().asString());
        assignedDateLabel.textProperty().bind(model.assignedDateProperty().asString());
        assignedTimeLabel.textProperty().bind(model.assignedTimeProperty().asString());
        dueDateLabel.textProperty().bind(model.dueDateProperty().asString());
        dueTimeLabel.textProperty().bind(model.dueTimeProperty().asString());
        amountDueLabel.textProperty().bind(model.amountDueProperty().asString());
    }
}
