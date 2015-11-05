package Controller;

import Model.ExitGate;
import Model.Garage;
import Model.Payment;
import Model.Ticket;
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

public class ExitController {

    private final ExitGate bean;

    private Stage stage;
    private Scene scene;
    private Window window;

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

    public ExitController(ExitGate gate, Window owner) throws IOException, NoSuchMethodException {
        this.bean = gate;
        monthList = FXCollections.observableArrayList(new DateFormatSymbols().getMonths());
        int lastIndex = monthList.size() - 1;
        String finalMonth = monthList.get(lastIndex);
        if(finalMonth.isEmpty()) {
            monthList.remove(lastIndex);
        }
        yearList = FXCollections.observableArrayList();
        int year = Calendar.getInstance().get(Calendar.YEAR) + 1, endYear = year + 10;
        for(; year < endYear; ++year) {
            yearList.add(Integer.toString(year));
        }

        initUI(gate, owner);

        expMonthBox.setItems(monthList);
        expMonthBox.getSelectionModel().selectFirst();
        expYearBox.setItems(yearList);
        expYearBox.getSelectionModel().selectFirst();

        addTextLimit(ticketIdField, 12);
        addTextLimit(ccNumField, 16);
        addTextLimit(csvField, 3);
    }

    public void initUI(ExitGate gate, Window owner) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ExitView.fxml"));
        loader.setController(this);
        this.scene = new Scene(loader.load(), 350.0, 400.0);
        this.stage = new Stage();
        this.stage.setMaxWidth(350.0);
        this.stage.setMaxHeight(400.0);
        this.stage.setTitle("Exit Gate #" + gate.getId());
        this.stage.setScene(this.scene);
        this.stage.setX(owner.getX() + owner.getWidth());
        this.stage.setY(owner.getY());
        this.stage.resizableProperty().setValue(Boolean.FALSE);
        this.stage.initModality(Modality.NONE);
        this.stage.initOwner(owner);
        this.window = this.scene.getWindow();
    }

    public ExitGate getBean() {
        return this.bean;
    }

    public void showView() {
        stage.show();
    }

    public void closeView() {
        stage.close();
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
            Ticket ticket = em.find(Ticket.class, Integer.parseInt(ticketIdField.getText()));
            if(null == ticket.getExitGate()) {
                if(ticket.getEntryGate().getGarage().getName().equals(bean.getGarage().getName())) {
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
                                    try {
                                        em.getTransaction().begin();
                                        Payment payment = new Payment(ccNum, csv, ticket.getAmountDue(), month, year, bean);
                                        bean.getPayments().add(payment);
                                        Garage owner = bean.getGarage();
                                        owner.setOccupancy(owner.getOccupancy() - 1);
                                        ticket.setExitGate(bean);
                                        em.persist(payment);
                                        em.merge(bean);
                                        em.merge(owner);
                                        em.merge(ticket);
                                        em.getTransaction().commit();
                                        Main.showInfo("Success.", "Please drive ahead.", "Thank you for using our Parking Services.");
                                    } finally {
                                        if (em.getTransaction().isActive()) {
                                            em.getTransaction().rollback();
                                        }
                                    }
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
