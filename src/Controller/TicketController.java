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

public class TicketController {

    private Stage stage;
    private Scene scene;
    private Window window;
    private final Ticket bean;

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
        this.bean = ticket;

        initUI(owner);

        idLabel.textProperty().bind(getBean().idProperty().asString());
        assignedDateLabel.textProperty().bind(getBean().assignedDateProperty().asString());
        assignedTimeLabel.textProperty().bind(getBean().assignedTimeProperty().asString());
        dueDateLabel.textProperty().bind(getBean().dueDateProperty().asString());
        dueTimeLabel.textProperty().bind(getBean().dueTimeProperty().asString());
        amountDueLabel.textProperty().bind(getBean().amountDueProperty().asString());
    }

    public void initUI(Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TicketView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 200.0, 300.0);
        this.stage = new Stage();
        this.stage.setMaxWidth(200.0);
        this.stage.setMaxHeight(300.0);
        this.stage.setTitle("Parking Ticket #" + getBean().getId());
        this.stage.setScene(this.scene);
        this.stage.setX(owner.getX());
        this.stage.setY(owner.getY() + 30.0);
        this.stage.resizableProperty().setValue(Boolean.FALSE);
        this.stage.initModality(Modality.NONE);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();
    }

    public Ticket getBean() {
        return this.bean;
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
    }
}
