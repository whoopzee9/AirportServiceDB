package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Flight;
import sample.tables.ServiceClass;
import sample.tables.Ticket;
import sample.tables.User;

import javax.print.attribute.standard.PresentationDirection;
import java.sql.*;
import java.util.ArrayList;

public class TicketsHandler {
    private Connection con;

    public TicketsHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<Ticket> getTickets(boolean isRelevant) {
        ArrayList<Ticket> tickets = new ArrayList<>();

        String added = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (isRelevant) {
            System.out.println(timestamp);
            added = "WHERE f.Departure_date >= ? ";
            System.out.println(added);
        }

        try {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.Passenger_name, t.BirthDate, t.Passport_number, f.Flight_code, " +
                    "t.Seat, c.Name, t.Price, u.Name\n" +
                    "FROM Tickets t\n" +
                    "INNER JOIN Flights f ON f.Id = t.Flight_id \n" +
                    "INNER JOIN Classes c ON c.id = t.Class_id\n" +
                    "INNER JOIN Users u ON u.Id = t.Cashier_id " + added);
            if (isRelevant) {
                ps.setTimestamp(1, timestamp);
            }
            ResultSet resultSet = ps.executeQuery();

            tickets = getTicketListFromResultSet(resultSet);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from tickets table!");
            alert.showAndWait();
        }

        return tickets;
    }

    public void addTicket(Ticket ticket) throws SQLException {
        PreparedStatement ps = con.prepareStatement("EXEC CreateTicket ?,?,?,?,?,?,?");
        ps.setString(1, ticket.getName());
        ps.setDate(2, ticket.getBirthDate());
        ps.setString(3, ticket.getPassport());
        ps.setString(4, ticket.getFlightCode());
        ps.setInt(5, ticket.getSeat());
        ps.setString(6, ticket.getServiceClass());
        ps.setString(7, ticket.getCashierName());

        ps.executeUpdate();
    }

    public ArrayList<Integer> getFreeSeats(String flightId, String className, int totalSeats) {
        ArrayList<Integer> seats = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT t.Seat \n" +
                    "FROM Tickets t\n" +
                    "INNER JOIN Classes c ON c.Id = t.Class_id\n" +
                    "INNER JOIN Flights f ON f.Id = t.Flight_id\n" +
                    "WHERE c.Name = ? AND f.Flight_code = ?");
            ps.setString(1, className);
            ps.setString(2, flightId);
            ResultSet resultSet = ps.executeQuery();

            for (int i = 1; i <= totalSeats; i++) {
                seats.add(i);
            }

            while (resultSet.next()) {
                Integer seat = resultSet.getInt(1);
                seats.remove(seat);
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from tickets table!");
            alert.showAndWait();
        }
        return seats;
    }

    public void deleteTicket(Ticket ticket) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM Tickets WHERE id = ?");
        ps.setInt(1, ticket.getId());
        ps.executeUpdate();
    }

    public ArrayList<Ticket> getSortedByFlight(Flight flight, boolean isRelevant) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String added = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (isRelevant) {
            System.out.println(timestamp);
            added = "AND f.Departure_date >= ? ";
            System.out.println(added);
        }

        try {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.Passenger_name, t.BirthDate, t.Passport_number, f.Flight_code, " +
                    "t.Seat, c.Name, t.Price, u.Name\n" +
                    "FROM Tickets t\n" +
                    "INNER JOIN Flights f ON f.Id = t.Flight_id \n" +
                    "INNER JOIN Classes c ON c.id = t.Class_id\n" +
                    "INNER JOIN Users u ON u.Id = t.Cashier_id " +
                    "WHERE f.Flight_code = ? " + added);
            ps.setString(1, flight.getFlightCode());
            if (isRelevant) {
                ps.setTimestamp(2, timestamp);
            }

            ResultSet resultSet = ps.executeQuery();
            tickets = getTicketListFromResultSet(resultSet);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from tickets table!");
            alert.showAndWait();
        }

        return tickets;
    }

    public ArrayList<Ticket> getSortedByCashier(User user, boolean isRelevant) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String added = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (isRelevant) {
            added = "AND f.Departure_date >= ? ";
        }

        try {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.Passenger_name, t.BirthDate, t.Passport_number, f.Flight_code, " +
                    "t.Seat, c.Name, t.Price, u.Name\n" +
                    "FROM Tickets t\n" +
                    "INNER JOIN Flights f ON f.Id = t.Flight_id \n" +
                    "INNER JOIN Classes c ON c.id = t.Class_id\n" +
                    "INNER JOIN Users u ON u.Id = t.Cashier_id " +
                    "WHERE u.Name = ? " + added);
            ps.setString(1, user.getName());
            if (isRelevant) {
                ps.setTimestamp(2, timestamp);
            }

            ResultSet resultSet = ps.executeQuery();
            tickets = getTicketListFromResultSet(resultSet);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from tickets table!");
            alert.showAndWait();
        }

        return tickets;
    }

    public ArrayList<Ticket> getSortedByFlightAndServiceClass(Flight flight, ServiceClass serviceClass, boolean isRelevant) {
        ArrayList<Ticket> tickets = new ArrayList<>();
        String added = "";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (isRelevant) {
            System.out.println(timestamp);
            added = "AND f.Departure_date >= ? ";
            System.out.println(added);
        }

        try {
            PreparedStatement ps = con.prepareStatement("SELECT t.id, t.Passenger_name, t.BirthDate, t.Passport_number, f.Flight_code, " +
                    "t.Seat, c.Name, t.Price, u.Name\n" +
                    "FROM Tickets t\n" +
                    "INNER JOIN Flights f ON f.Id = t.Flight_id \n" +
                    "INNER JOIN Classes c ON c.id = t.Class_id\n" +
                    "INNER JOIN Users u ON u.Id = t.Cashier_id " +
                    "WHERE f.Flight_code = ? AND c.Name = ? " + added);
            ps.setString(1, flight.getFlightCode());
            ps.setString(2, serviceClass.getName());
            if (isRelevant) {
                ps.setTimestamp(3, timestamp);
            }

            ResultSet resultSet = ps.executeQuery();
            tickets = getTicketListFromResultSet(resultSet);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from tickets table!");
            alert.showAndWait();
        }

        return tickets;
    }

    private ArrayList<Ticket> getTicketListFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Date birthDate = resultSet.getDate(3);
            String passport = resultSet.getString(4);
            String flightCode = resultSet.getString(5);
            Integer seat = resultSet.getInt(6);
            String className = resultSet.getString(7);
            Double price = resultSet.getDouble(8);
            String cashierName = resultSet.getString(9);

            Ticket ticket = new Ticket(name, birthDate, passport, flightCode, seat, className, price, cashierName);
            ticket.setId(id);
            tickets.add(ticket);
        }
        return tickets;
    }
}
