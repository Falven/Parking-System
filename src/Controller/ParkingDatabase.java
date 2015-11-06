package Controller;

import Model.*;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * A Database Client that manages a Parking System.
 */
public class ParkingDatabase {

    private static final String URL = "jdbc:derby:ParkingSystemDB";
    private static ParkingDatabase instance;
    private Connection conn;

    /**
     * @throws SQLException The ParkingDatabase was unable to create the default Library tables on the provided Database.
     * @see ParkingDatabase#ParkingDatabase()
     */
    public ParkingDatabase() {
        try {
            Properties properties = new Properties();
            this.conn = DriverManager.getConnection(URL, properties);
            this.conn.setAutoCommit(true);
        } catch (SQLException se) {
            showException(se);
        }
    }

    public static ParkingDatabase getInstance() {
        if (null == instance) {
            instance = new ParkingDatabase();
        }
        return instance;
    }

    /**
     * Creates default tables on the Database.
     *
     * @throws SQLException The ParkingDatabase was unable to create the default tables.
     */
    public void tryCreateTables() {
        createGarageTable();
        createEntryGateTable();
        createExitGateTable();
        createTicketTable();
        createPaymentTable();
    }

    /**
     * Drops default tables on the Database.
     *
     * @throws SQLException The ParkingDatabase was unable to create the default tables.
     */
    public void dropTables() {
        dropGarageTable();
        dropEntryGateTable();
        dropExitGateTable();
        dropTicketTable();
        dropPaymentTable();
    }

    public void createGarageTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE GARAGE (\n" +
                    "  NAME VARCHAR(255) NOT NULL,\n" +
                    "  OCCUPANCY INTEGER,\n" +
                    "  MAX_OCCUPANCY INTEGER,\n" +
                    "  ENTRYGATES INTEGER,\n" +
                    "  EXITGATES INTEGER,\n" +
                    "  PRIMARY KEY (NAME)\n" +
                    ");");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void dropGarageTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE GARAGE;");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void createEntryGateTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE ENTRYGATE (\n" +
                    "  ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  GARAGE_NAME VARCHAR(255) NOT NULL,\n" +
                    "  PRIMARY KEY (ID),\n" +
                    "  FOREIGN KEY (GARAGE_NAME) REFERENCES GARAGE(NAME)\n" +
                    ");");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void dropEntryGateTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE ENTRYGATE;");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void createExitGateTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE EXITGATE (\n" +
                    "  ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  GARAGE_NAME VARCHAR(255) NOT NULL,\n" +
                    "  PRIMARY KEY (ID),\n" +
                    "  FOREIGN KEY (GARAGE_NAME) REFERENCES GARAGE(NAME)\n" +
                    ");");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void dropExitGateTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE EXITGATE;");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void createTicketTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE TICKET (\n" +
                    "  ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  ASSIGNED_DATE DATE,\n" +
                    "  ASSIGNED_TIME TIME,\n" +
                    "  DUE_DATE DATE,\n" +
                    "  DUE_TIME TIME,\n" +
                    "  AMOUNT_DUE DOUBLE,\n" +
                    "  ENTRYGATE_ID INT NOT NULL,\n" +
                    "  EXITGATE_ID INT,\n" +
                    "  PRIMARY KEY (ID),\n" +
                    "  FOREIGN KEY (ENTRYGATE_ID) REFERENCES ENTRYGATE(ID),\n" +
                    "  FOREIGN KEY (EXITGATE_ID) REFERENCES EXITGATE(ID)\n" +
                    ");");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void dropTicketTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE TICKET;");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void createPaymentTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE PAYMENT (\n" +
                    "  ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  CCNUM BIGINT,\n" +
                    "  CSV INTEGER,\n" +
                    "  AMOUNTPAID DOUBLE,\n" +
                    "  EXP_MONTH INT,\n" +
                    "  EXP_YEAR INT,\n" +
                    "  EXITGATE_ID INT NOT NULL,\n" +
                    "  PRIMARY KEY (ID),\n" +
                    "  FOREIGN KEY (EXITGATE_ID) REFERENCES EXITGATE(ID)\n" +
                    ");");
        } catch (SQLException se) {
            showException(se);
        }
    }

    public void dropPaymentTable() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE PAYMENT;");
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Performs final cleanup before GC resource acquisition.
     *
     * @throws Throwable
     */
    @Override
    protected void finalize() throws Throwable {
        try {
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.close();
        }
    }

    /**
     * Gets the garage with the provided name from the database.
     *
     * @param name The name to search the Database for.
     * @throws SQLException If there was a problem searching the ParkingDatabase.
     */
    public Garage getGarage(String name) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM GARAGE g WHERE g.NAME = ?;");
            prepStmt.setString(1, name);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                return null;
            } else {
                return new Garage(rs.getString("NAME"), rs.getInt("OCCUPANCY"), rs.getInt("MAX_OCCUPANCY"),
                        rs.getInt("ENTRYGATES"), rs.getInt("EXITGATES"));
            }
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets the EntryGate with the provided id from the database.
     *
     * @param id The id to search the Database for.
     * @throws SQLException If there was a problem searching the ParkingDatabase.
     */
    public EntryGate getEntryGate(int id) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM ENTRYGATE e WHERE e.ID = ?;");
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                return null;
            } else {
                return new EntryGate(rs.getInt("ID"), rs.getString("GARAGE_NAME"));
            }
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets the ExitGate with the provided id from the database.
     *
     * @param id The id to search the Database for.
     * @throws SQLException If there was a problem searching the ParkingDatabase.
     */
    public ExitGate getExitGate(int id) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM EXITGATE e WHERE e.ID = ?;");
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                return null;
            } else {
                return new ExitGate(rs.getInt("ID"), rs.getString("GARAGE_NAME"));
            }
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets the Ticket with the provided id from the database.
     *
     * @param id The id to search the Database for.
     * @throws SQLException If there was a problem searching the ParkingDatabase.
     */
    public Ticket getTicket(int id) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM TICKET t WHERE t.ID = ?;");
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                return null;
            } else {
                return new Ticket(rs.getInt("ID"), rs.getDate("ASSIGNED_DATE"), rs.getTime("ASSIGNED_TIME"),
                        rs.getDate("DUE_DATE"), rs.getTime("DUE_TIME"), rs.getDouble("AMOUNT_DUE"),
                        rs.getInt("ENTRYGATE_ID"), rs.getInt("EXITGATE_ID"));
            }
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets the Payment with the provided id from the database.
     *
     * @param id The id to search the Database for.
     * @throws SQLException If there was a problem searching the ParkingDatabase.
     */
    public Payment getPayment(int id) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM PAYMENT p WHERE p.ID = ?;");
            prepStmt.setInt(1, id);
            ResultSet rs = prepStmt.executeQuery();
            if(rs.next()) {
                return null;
            } else {
                return new Payment(rs.getInt("ID"), rs.getLong("CCNUM"), rs.getInt("CSV"),
                        rs.getDouble("AMOUNT_PAID"), rs.getInt("EXP_MONTH"), rs.getInt("EXP_YEAR"),
                        rs.getInt("EXITGATE_ID"));
            }
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Adds the given Garage to the ParkingDatabase.
     *
     * @param garage The Garage to add to the Database.
     * @throws SQLException If there was a problem adding a student to the ParkingDatabase.
     */
    public void add(Garage garage) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO GARAGE VALUES (?, ?, ?, ?, ?);");
            prepStmt.setString(1, garage.getName());
            prepStmt.setInt(2, garage.getOccupancy());
            prepStmt.setInt(3, garage.getMaxOccupancy());
            prepStmt.setInt(4, garage.getEntryGates());
            prepStmt.setInt(5, garage.getExitGates());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Adds the given EntryGate to the ParkingDatabase.
     *
     * @param entryGate The EntryGate to add to the Database.
     * @throws SQLException If there was an error adding the EntryGate to the Database.
     */
    public void add(EntryGate entryGate) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO ENTRYGATE VALUES (?);");
            prepStmt.setString(1, entryGate.getGarageName());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Adds the given ExitGate to the ParkingDatabase.
     *
     * @param exitGate The ExitGate to add to the Database.
     * @throws SQLException If there was an error adding the ExitGate to the Database.
     */
    public void add(ExitGate exitGate) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO EXITGATE VALUES (?);");
            prepStmt.setString(1, exitGate.getGarageName());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Adds the given Payment to the ParkingDatabase.
     *
     * @param payment The Payment to add to the Database.
     * @throws SQLException If there was an error adding the Payment to the Database.
     */
    public void add(Payment payment) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO PAYMENT VALUES (?, ?, ?, ?, ?, ?);");
            prepStmt.setLong(1, payment.getCcNum());
            prepStmt.setInt(2, payment.getCsv());
            prepStmt.setDouble(3, payment.getAmountPaid());
            prepStmt.setInt(4, payment.getExpMonth());
            prepStmt.setInt(5, payment.getExpYear());
            prepStmt.setInt(6, payment.getExitGateId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Adds the given Ticket to the ParkingDatabase.
     *
     * @param ticket The Ticket to add to the Database.
     * @throws SQLException If there was an error adding the Ticket to the Database.
     */
    public void add(Ticket ticket) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO TICKET VALUES (?, ?, ?, ?, ?, ?, ?);");
            prepStmt.setDate(1, ticket.getAssignedDate());
            prepStmt.setTime(2, ticket.getAssignedTime());
            prepStmt.setDate(3, ticket.getDueDate());
            prepStmt.setTime(4, ticket.getDueTime());
            prepStmt.setDouble(5, ticket.getAmountDue());
            prepStmt.setInt(6, ticket.getEntryGateId());
            prepStmt.setInt(7, ticket.getExitGateId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Modifies the given Garage to the Database.
     *
     * @param garage The Garage to modify in the Database.
     * @throws SQLException If there was an issue merging the Garage to the Database.
     */
    public void merge(Garage garage) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE GARAGE SET OCCUPANCY = ?, MAX_OCCUPANCY = ?, ENTRYGATES = ?, EXITGATES = ? WHERE NAME = ?;");
            prepStmt.setInt(1, garage.getOccupancy());
            prepStmt.setInt(2, garage.getMaxOccupancy());
            prepStmt.setInt(3, garage.getEntryGates());
            prepStmt.setInt(4, garage.getExitGates());
            prepStmt.setString(5, garage.getName());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Modifies the given EntryGate to the Database.
     *
     * @param entryGate The EntryGate to modify in the Database.
     * @throws SQLException If there was an issue merging the EntryGate to the Database.
     */
    public void merge(EntryGate entryGate) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE ENTRYGATE SET GARAGE_NAME = ? WHERE ID = ?;");
            prepStmt.setString(1, entryGate.getGarageName());
            prepStmt.setInt(2, entryGate.getId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Modifies the given ExitGate to the Database.
     *
     * @param exitGate The ExitGate to modify in the Database.
     * @throws SQLException If there was an issue merging the ExitGate to the Database.
     */
    public void merge(ExitGate exitGate) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE EXITGATE SET GARAGE_NAME = ? WHERE ID = ?;");
            prepStmt.setString(1, exitGate.getGarageName());
            prepStmt.setInt(2, exitGate.getId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Modifies the given Payment to the Database.
     *
     * @param payment The Payment to modify in the Database.
     * @throws SQLException If there was an issue merging the Payment to the Database.
     */
    public void merge(Payment payment) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE PAYMENT SET CCNUM = ?, CSV = ?, " +
                    "AMOUNTPAID = ?, EXP_MONTH = ?, EXP_YEAR = ?, EXITGATE_ID = ?  WHERE ID = ?;");
            prepStmt.setLong(1, payment.getCcNum());
            prepStmt.setInt(2, payment.getCsv());
            prepStmt.setDouble(3, payment.getAmountPaid());
            prepStmt.setInt(4, payment.getExpMonth());
            prepStmt.setInt(5, payment.getExpYear());
            prepStmt.setInt(6, payment.getExitGateId());
            prepStmt.setInt(7, payment.getId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Modifies the given Ticket to the Database.
     *
     * @param ticket The Ticket to modify in the Database.
     * @throws SQLException If there was an issue merging the Ticket to the Database.
     */
    public void merge(Ticket ticket) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("UPDATE TICKET SET ENTRYGATE_ID = ?, EXITGATE_ID = ?, " +
                    "ASSIGNED_DATE = ?, ASSIGNED_TIME = ?, DUE_DATE = ?, DUE_TIME = ?, AMOUNT_DUE = ?  WHERE ID = ?;");
            prepStmt.setInt(1, ticket.getEntryGateId());
            prepStmt.setInt(2, ticket.getExitGateId());
            prepStmt.setDate(3, ticket.getAssignedDate());
            prepStmt.setTime(4, ticket.getAssignedTime());
            prepStmt.setDate(5, ticket.getDueDate());
            prepStmt.setTime(6, ticket.getDueTime());
            prepStmt.setDouble(7, ticket.getAmountDue());
            prepStmt.setInt(7, ticket.getId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Deletes the given Garage from the Database.
     *
     * @param garage The Garage to remove from the Database.
     * @throws SQLException If there was an issue removing the Garage from the Database.
     */
    public void remove(Garage garage) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM GARAGE g WHERE g.NAME = ?;");
            prepStmt.setString(1, garage.getName());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Deletes the given EntryGate from the Database.
     *
     * @param entryGate The EntryGate to remove from the Database.
     * @throws SQLException If there was an issue removing the EntryGate from the Database.
     */
    public void remove(EntryGate entryGate) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM ENTRYGATE e WHERE e.ID = ?;");
            prepStmt.setInt(1, entryGate.getId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Deletes the given ExitGate from the Database.
     *
     * @param exitGate The ExitGate to remove from the Database.
     * @throws SQLException If there was an issue removing the ExitGate from the Database.
     */
    public void remove(ExitGate exitGate) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM EXITGATE e WHERE e.ID = ?;");
            prepStmt.setInt(1, exitGate.getId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Deletes the given Payment from the Database.
     *
     * @param payment The Payment to remove from the Database.
     * @throws SQLException If there was an issue removing the Payment from the Database.
     */
    public void remove(Payment payment) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM PAYMENT p WHERE p.ID = ?;");
            prepStmt.setInt(1, payment.getId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Deletes the given Ticket from the Database.
     *
     * @param ticket The Ticket to remove from the Database.
     * @throws SQLException If there was an issue removing the Ticket from the Database.
     */
    public void remove(Ticket ticket) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("DELETE FROM TICKET t WHERE t.ID = ?;");
            prepStmt.setInt(1, ticket.getId());
            prepStmt.execute();
        } catch (SQLException se) {
            showException(se);
        }
    }

    /**
     * Gets all garages from the Database.
     */
    public List<Garage> getGarages() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM GARAGE g;");
            List<Garage> garages = FXCollections.observableArrayList();
            while (rs.next()) {
                garages.add(new Garage(rs.getString("NAME"), rs.getInt("OCCUPANCY"), rs.getInt("MAX_OCCUPANCY"),
                        rs.getInt("ENTRYGATES"), rs.getInt("EXITGATES")));
            }
            return garages;
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets all EntryGates from the Database.
     */
    public List<EntryGate> getEntryGates() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ENTRYGATE e;");
            List<EntryGate> entryGates = FXCollections.observableArrayList();
            while (rs.next()) {
                entryGates.add(new EntryGate(rs.getInt("ID"), rs.getString("GARAGE_NAME")));
            }
            return entryGates;
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets all ExitGates from the Database.
     */
    public List<ExitGate> getExitGates() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EXITGATE e;");
            List<ExitGate> exitGates = FXCollections.observableArrayList();
            while (rs.next()) {
                exitGates.add(new ExitGate(rs.getInt("ID"), rs.getString("GARAGE_NAME")));
            }
            return exitGates;
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets all Tickets from the Database.
     */
    public List<Ticket> getTickets() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM TICKET e;");
            List<Ticket> tickets = FXCollections.observableArrayList();
            while (rs.next()) {
                tickets.add(new Ticket(rs.getInt("ID"), rs.getDate("ASSIGNED_DATE"), rs.getTime("ASSIGNED_TIME"),
                        rs.getDate("DUE_DATE"), rs.getTime("DUE_TIME"), rs.getDouble("AMOUNT_DUE"),
                        rs.getInt("ENTRYGATE_ID"), rs.getInt("EXITGATE_ID")));
            }
            return tickets;
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets all Payments from the Database.
     */
    public List<Payment> getPayments() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PAYMENT p;");
            List<Payment> payments = FXCollections.observableArrayList();
            while (rs.next()) {
                payments.add(new Payment(rs.getInt("ID"), rs.getLong("CCNUM"), rs.getInt("CSV"),
                        rs.getDouble("AMOUNT_PAID"), rs.getInt("EXP_MONTH"), rs.getInt("EXP_YEAR"),
                        rs.getInt("EXITGATE_ID")));
            }
            return payments;
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    /**
     * Gets all Tickets from the Database.
     */
    public List<Ticket> getTickets(EntryGate entryGate) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("SELECT * FROM TICKET e WHERE e.ENTRYGATE_ID = ?;");
            prepStmt.setInt(1, entryGate.getId());
            ResultSet rs = prepStmt.executeQuery();
            List<Ticket> tickets = FXCollections.observableArrayList();
            while (rs.next()) {
                tickets.add(new Ticket(rs.getInt("ID"), rs.getDate("ASSIGNED_DATE"), rs.getTime("ASSIGNED_TIME"),
                        rs.getDate("DUE_DATE"), rs.getTime("DUE_TIME"), rs.getDouble("AMOUNT_DUE"),
                        rs.getInt("ENTRYGATE_ID"), rs.getInt("EXITGATE_ID")));
            }
            return tickets;
        } catch (SQLException se) {
            showException(se);
            return null;
        }
    }

    private void showException(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("An exception has occurred.");

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Exception stacktrace:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }
}