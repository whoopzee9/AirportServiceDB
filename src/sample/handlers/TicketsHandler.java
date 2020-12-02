package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Flight;
import sample.tables.Ticket;

import java.sql.*;
import java.util.ArrayList;

public class TicketsHandler {
    private Connection con;

    public TicketsHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<Ticket> getTickets() {
        ArrayList<Ticket> tickets = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT t.id, t.Passenger_name, t.BirthDate, t.Passport_number, f.Flight_code, " +
                    "t.Seat, c.Name, t.Price, u.Name\n" +
                    "FROM Tickets t\n" +
                    "INNER JOIN Flights f ON f.Id = t.Flight_id \n" +
                    "INNER JOIN Classes c ON c.id = t.Class_id\n" +
                    "INNER JOIN Users u ON u.Id = t.Cashier_id");

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
}
