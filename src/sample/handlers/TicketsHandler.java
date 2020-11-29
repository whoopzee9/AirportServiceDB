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
            ResultSet resultSet = statement.executeQuery("SELECT t.Passenger_name, t.BirthDate, t.Passport_number, f.Flight_code, " +
                    "t.Seat, c.Name, t.Price, u.Name\n" +
                    "FROM Tickets t\n" +
                    "INNER JOIN Flights f ON f.Id = t.Flight_id \n" +
                    "INNER JOIN Classes c ON c.id = t.Class_id\n" +
                    "INNER JOIN Users u ON u.Id = t.Cashier_id");

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                Date birthDate = resultSet.getDate(2);
                String passport = resultSet.getString(3);
                String flightCode = resultSet.getString(4);
                Integer seat = resultSet.getInt(5);
                String className = resultSet.getString(6);
                Double price = resultSet.getDouble(7);
                String cashierName = resultSet.getString(8);

                tickets.add(new Ticket(name, birthDate, passport, flightCode, seat, className, price, cashierName));
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

        ps.executeQuery();
    }
}
