package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ParkingSystemDB {

    private static final String DB_URL = "jdbc:derby:ParkingSystemDB;create=true";

    public ParkingSystemDB() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            createTicketTable(conn);
            createPaymentTable(conn);
            createEntryGateTable(conn);
            createExitGateTable(conn);
            createGarageTable(conn);
            conn.close();
        } catch (SQLException se) {
            System.out.println("Error connecting to the Parking System Database.");
            System.out.println(se.getMessage());
        }
    }

    private void createTicketTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE TICKET\n" +
                    "(\n" +
                    "  TICKET_ID INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)\n" +
                    ")");
        } catch (SQLException se) {
            if(!se.getSQLState().equals("X0Y32")) {
                System.out.println("Error creating the Garage table.");
                System.out.println(se.getMessage());
            }
        }
    }

    private void createPaymentTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE PAYMENT\n" +
                    "(\n" +
                    "  PAYMENT_ID INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)\n" +
                    ")");
        } catch (SQLException se) {
            if(!se.getSQLState().equals("X0Y32")) {
                System.out.println("Error creating the Garage table.");
                System.out.println(se.getMessage());
            }
        }
    }

    private void createEntryGateTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE ENTRYGATE\n" +
                    "(\n" +
                    "  ENTRYGATE_ID INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  TICKET_ID INTEGER NOT NULL REFERENCES TICKET (TICKET_ID)\n" +
                    ")");
        } catch (SQLException se) {
            if(!se.getSQLState().equals("X0Y32")) {
                System.out.println("Error creating the Garage table.");
                System.out.println(se.getMessage());
            }
        }
    }

    private void createExitGateTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE EXITGATE\n" +
                    "(\n" +
                    "  EXITGATE_ID INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  TICKET_ID INTEGER REFERENCES TICKET (TICKET_ID),\n" +
                    "  PAYMENT_ID INTEGER REFERENCES PAYMENT (PAYMENT_ID)\n" +
                    ")");
        } catch (SQLException se) {
            if(!se.getSQLState().equals("X0Y32")) {
                System.out.println("Error creating the Garage table.");
                System.out.println(se.getMessage());
            }
        }
    }

    private void createGarageTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE GARAGE\n" +
                    "(\n" +
                    "  GARAGE_NAME VARCHAR(50) PRIMARY KEY NOT NULL,\n" +
                    "  ENTRYGATE_ID INTEGER NOT NULL REFERENCES ENTRYGATE (ENTRYGATE_ID),\n" +
                    "  EXITGATE_ID INTEGER NOT NULL REFERENCES EXITGATE (EXITGATE_ID)\n" +
                    ")");
        } catch (SQLException se) {
            if(!se.getSQLState().equals("X0Y32")) {
                System.out.println("Error creating the Garage table.");
                System.out.println(se.getMessage());
            }
        }
    }
}
