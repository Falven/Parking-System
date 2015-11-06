import controller.ParkingDatabase;
import javafx.collections.ObservableList;
import model.*;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ParkingDatabaseTest {

    ParkingDatabase database;

    public ParkingDatabaseTest() throws SQLException {
        database = ParkingDatabase.getInstance();
    }

    @Test
    public void testTryCreateTables() throws Exception {
        database.tryCreateTables();
        database.dropTables();
    }

    @Test
    public void testCreateTables() throws Exception {
        database.createTables();
        database.dropTables();
    }

    @Test
    public void testTryDropTables() throws Exception {
        database.createTables();
        database.tryDropTables();
    }

    @Test
    public void testDropTables() throws Exception {
        database.createTables();
        database.dropTables();
    }

    @Test
    public void testGetGarage() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        Garage garage = database.getGarage("test_garage");
        assertNotEquals(null, garage);
        database.dropTables();
    }

    @Test
    public void testGetEntryGate() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate gate = new EntryGate("test_garage");
        database.add(gate);
        gate = database.getEntryGate(gate.getId());
        assertNotEquals(null, gate);
        database.dropTables();
    }

    @Test
    public void testGetExitGate() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        ExitGate gate = new ExitGate("test_garage");
        database.add(gate);
        gate = database.getExitGate(gate.getId());
        assertNotEquals(null, gate);
        database.dropTables();
    }

    @Test
    public void testGetTicket() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate gate = new EntryGate("test_garage");
        database.add(gate);
        Ticket ticket = new Ticket(gate.getId());
        database.add(ticket);
        ticket = database.getTicket(ticket.getId());
        assertNotEquals(null, ticket);
        database.dropTables();
    }

    @Test
    public void testGetPayment() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate engate = new EntryGate("test_garage");
        database.add(engate);
        ExitGate exgate = new ExitGate("test_garage");
        database.add(exgate);
        Ticket ticket = new Ticket(engate.getId());
        database.add(ticket);
        Payment payment = new Payment(0, 0, 0, 0, 0, exgate.getId(), engate.getId());
        database.add(payment);
        payment = database.getPayment(payment.getId());
        assertNotEquals(null, payment);
        database.dropTables();
    }

    @Test
    public void testAdd() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        database.dropTables();
    }

    @Test
    public void testAdd1() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        ExitGate gate = new ExitGate("test_garage");
        database.add(gate);
        database.dropTables();
    }

    @Test
    public void testAdd2() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate engate = new EntryGate("test_garage");
        database.add(engate);
        ExitGate exgate = new ExitGate("test_garage");
        database.add(exgate);
        Ticket ticket = new Ticket(engate.getId());
        database.add(ticket);
        Payment payment = new Payment(0, 0, 0, 0, 0, exgate.getId(), engate.getId());
        database.add(payment);
        database.dropTables();
    }

    @Test
    public void testAdd3() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate gate = new EntryGate("test_garage");
        database.add(gate);
        Ticket ticket = new Ticket(gate.getId());
        database.add(ticket);
        database.dropTables();
    }

    @Test
    public void testMerge() throws Exception {
        database.createTables();
        Garage garage = new Garage("test_garage");
        database.add(garage);
        garage.setOccupancy(5);
        database.merge(garage);
        database.dropTables();
    }

    @Test
    public void testRemove() throws Exception {
        database.createTables();
        Garage garage = new Garage("test_garage");
        database.add(garage);
        database.remove(garage);
        database.dropTables();
    }

    @Test
    public void testGetGarages() throws Exception {
        database.createTables();
        Garage garage = new Garage("test_garage");
        database.add(garage);
        ObservableList<Garage> garages = database.getGarages();
        assertEquals(1, garages.size());
        database.dropTables();
    }

    @Test
    public void testGetEntryGates() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate engate = new EntryGate("test_garage");
        database.add(engate);
        ObservableList<EntryGate> gates = database.getEntryGates();
        assertEquals(1, gates.size());
        database.dropTables();
    }

    @Test
    public void testGetExitGates() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate engate = new EntryGate("test_garage");
        database.add(engate);
        ExitGate exgate = new ExitGate("test_garage");
        database.add(exgate);
        ObservableList<ExitGate> gates = database.getExitGates();
        assertEquals(1, gates.size());
        database.dropTables();
    }

    @Test
    public void testGetTickets() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate engate = new EntryGate("test_garage");
        database.add(engate);
        ExitGate exgate = new ExitGate("test_garage");
        database.add(exgate);
        Ticket ticket = new Ticket(engate.getId());
        database.add(ticket);
        ObservableList<Ticket> tickets = database.getTickets();
        assertEquals(1, tickets.size());
        database.dropTables();
    }

    @Test
    public void testGetPayments() throws Exception {
        database.createTables();
        database.add(new Garage("test_garage"));
        EntryGate engate = new EntryGate("test_garage");
        database.add(engate);
        ExitGate exgate = new ExitGate("test_garage");
        database.add(exgate);
        Ticket ticket = new Ticket(engate.getId());
        database.add(ticket);
        Payment payment = new Payment(0, 0, 0, 0, 0, exgate.getId(), engate.getId());
        database.add(payment);
        ObservableList<Payment> payments = database.getPayments();
        assertEquals(1, payments.size());
        database.dropTables();
    }
}