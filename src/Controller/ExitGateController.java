package Controller;

import Model.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ExitGateController extends Controller<ExitGate> {

    @FXML
    private TextField ticketIdField;

    @FXML
    private TextField ccNumField;

    @FXML
    private ComboBox expMonthBox;

    @FXML
    private ComboBox expYearBox;

    @FXML
    private TextField csvField;

    private ObservableList<String> monthList;

    private ObservableList<String> yearList;

    public ExitGateController(ExitGate gate, GarageController controller) throws IOException, NoSuchMethodException {
        super(gate);
        initMonthsList();
        initYearsList();
        Window owner = controller.getScene().getWindow();
        initUI("Exit Gate #" + getModel().getId(), "/view/ExitView.fxml", 350.0, 400.0, 350.0, 400.0, owner.getX() + owner.getWidth(), owner.getY(), false, Modality.NONE, null, owner);
        expMonthBox.setItems(monthList);
        expMonthBox.getSelectionModel().selectFirst();
        expYearBox.setItems(yearList);
        expYearBox.getSelectionModel().selectFirst();
        addTextLimit(ticketIdField, 12);
        addTextLimit(ccNumField, 16);
        addTextLimit(csvField, 3);
    }

    private void initMonthsList() {
        monthList = FXCollections.observableArrayList(new DateFormatSymbols().getMonths());
        int lastIndex = monthList.size() - 1;
        String finalMonth = monthList.get(lastIndex);
        if(finalMonth.isEmpty()) {
            monthList.remove(lastIndex);
        }
    }

    private void initYearsList() {
        yearList = FXCollections.observableArrayList();
        int year = Calendar.getInstance().get(Calendar.YEAR) + 1, endYear = year + 10;
        for(; year < endYear; ++year) {
            yearList.add(Integer.toString(year));
        }
    }

    public void addTextLimit(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                String text = tf.getText();
                if (text.length() > maxLength) {
                    tf.setText(text.substring(0, maxLength));
                }
            }
        });
    }

    @FXML
    protected void handleSubmit(ActionEvent event) {
        try {
            Ticket ticket = ParkingDatabase.getInstance().getTicket(Integer.parseInt(ticketIdField.getText()));
            if(null == ParkingDatabase.getInstance().getExitGate(ticket.getExitGateId())) {
                EntryGate ticketGate = ParkingDatabase.getInstance().getEntryGate(ticket.getEntryGateId());
                if(ticketGate.getGarageName().equals(getModel().getGarageName())) {
                    try {
                        long ccNum = Long.parseLong(ccNumField.getText());
                        try {
                            short csv = Short.parseShort(csvField.getText());
                            try {
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(new SimpleDateFormat("MMMM").parse((String)expMonthBox.getSelectionModel().getSelectedItem()));
                                int month = cal.get(Calendar.MONTH);
                                try {
                                    int year = Integer.parseInt((String)expYearBox.getSelectionModel().getSelectedItem());
                                    Payment payment = new Payment(ccNum, csv, ticket.getAmountDue(), month, year, getModel().getId());
//                                    bean.getPayments().add(payment);
//                                    Garage owner = bean.getGarage();
//                                    owner.setOccupancy(owner.getOccupancy() - 1);
//                                    ticket.setExitGate(bean);
//                                    Main.getDatabase().persist(payment);
//                                    Main.showInfo("Success.", "Please drive ahead.", "Thank you for using our Parking Services.");
                                } catch (NumberFormatException nfe) {
                                    Main.showError("Credit card error.", "Error processing your card.", "The provided expiration year is invalid.");
                                }
                            } catch (ParseException pe) {
                                Main.showError("Credit card error.", "Error processing your card.", "The provided expiration month is invalid.");
                            }
                        } catch(NumberFormatException nfe) {
                            Main.showError("Credit card error.", "Error processing your card.", "The provided CSV number is invalid.");
                        }
                    } catch(NumberFormatException nfe) {
                        Main.showError("Credit card error.", "Error reading your card.", "The provided credit card number is invalid.");
                    }
                } else {
                    Main.showError("Ticket ID error.", "Invalid ticket.", "The provided ticket is not for this garage.");
                }
            } else {
                Main.showError("Ticket ID error.", "Invalid ticket.", "The provided ticket has already been used.");
            }
        } catch(NumberFormatException nfe) {
            Main.showError("Ticket ID error.", "Error reading your ticket.", "The provided ticket ID is invalid.");
        }
    }
}
