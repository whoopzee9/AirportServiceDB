package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Flight;
import sample.tables.Plane;

import java.sql.*;
import java.util.ArrayList;

public class FlightsHandler {
    private Connection con;

    public FlightsHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<Flight> getFlights() {
        ArrayList<Flight> flights = new ArrayList<>();

        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT f.Flight_code, f.Departure, f.Destination, f.Departure_date, f.Arrival_date, p.Name, a.Name, f.Status, t.Base_price\n" +
                    "FROM Flights f\n" +
                    "INNER JOIN Airlines a ON a.Id = f.Airline_id \n" +
                    "INNER JOIN Planes p ON p.id = f.Plane_id\n" +
                    "INNER JOIN Tariffs t ON t.Id = f.Tariff_id");

            while (resultSet.next()) {
                String code = resultSet.getString(1);
                String depart = resultSet.getString(2);
                String dest = resultSet.getString(3);
                Timestamp depDate = resultSet.getTimestamp(4);
                Timestamp arrDate = resultSet.getTimestamp(5);
                String plane = resultSet.getString(6);
                String airline = resultSet.getString(7);
                String status = resultSet.getString(8);
                Double basePrice = resultSet.getDouble(9);
                flights.add(new Flight(code, depart, dest, depDate, arrDate, plane, airline, status, basePrice));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from flight table!");
            alert.showAndWait();
        }

        return flights;
    }

    public void addFlight(Flight flight) throws SQLException {
        PreparedStatement ps = con.prepareStatement("EXEC CreateFlight ?,?,?,?,?,?,?,?,?");
        ps.setString(1, flight.getFlightCode());
        ps.setString(2, flight.getDeparture());
        ps.setString(3, flight.getDestination());
        ps.setTimestamp(4, flight.getDepartureDate());
        ps.setTimestamp(5, flight.getArrivalDate());
        ps.setString(6, flight.getPlaneName());
        ps.setString(7, flight.getAirlineName());
        ps.setString(8, flight.getStatus());
        ps.setDouble(9, flight.getBasePrice());

        ps.executeUpdate();
    }

    public double getFlightTariff(String flightCode) {
        double tariff = 0;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT t.Base_price FROM Flights f " +
                    "INNER JOIN Tariffs t on f.Tariff_id = t.Id " +
                    "WHERE f.Flight_code = ?");
            ps.setString(1, flightCode);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            tariff = resultSet.getDouble(1);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Request error");
            alert.setContentText("You don't have permission to read from flight table!");
            alert.showAndWait();
        }
        return tariff;
    }

    public Plane getFlightPlane(String flightCode) {
        Plane plane = null;
        try {
            PreparedStatement ps = con.prepareStatement("SELECT p.Name, p.Total_seats, p.Business_class_seats, p.First_class_seats FROM Flights f " +
                    "INNER JOIN Planes p on f.Plane_id = p.id " +
                    "WHERE f.Flight_code = ?");
            ps.setString(1, flightCode);
            ResultSet resultSet = ps.executeQuery();

            resultSet.next();
            String name = resultSet.getString(1);
            int total = resultSet.getInt(2);
            int business = resultSet.getInt(3);
            int first = resultSet.getInt(4);

            plane = new Plane(name, total, business, first);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Request error");
            alert.setContentText("You don't have permission to read from flight table!");
            alert.showAndWait();
        }
        return plane;
    }

    public void updateFlight(Flight flight) {
        try {
            PreparedStatement ps = con.prepareStatement("EXEC UpdateFlight ?, ?, ?, ?, ?");
            ps.setString(1, flight.getFlightCode());
            ps.setTimestamp(2, flight.getDepartureDate());
            ps.setTimestamp(3, flight.getArrivalDate());
            ps.setString(4, flight.getPlaneName());
            ps.setString(5, flight.getStatus());

            ps.executeUpdate();
        } catch (SQLException throwables) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Request error");
            alert.setContentText("You don't have permission to read from flight table!");
            alert.showAndWait();
        }
    }
}
