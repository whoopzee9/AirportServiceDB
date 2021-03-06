package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Airline;
import sample.tables.Flight;
import sample.tables.Plane;
import sample.tables.User;

import java.sql.*;
import java.util.ArrayList;

public class PlanesHandler {
    private Connection con;

    public PlanesHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<Plane> getPlanes() {
        ArrayList<Plane> planes = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT p.Name, p.Total_seats, p.Business_class_seats, p.First_class_seats\n" +
                    "FROM Planes p");

            planes = getPlaneListFromResultSet(resultSet);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from planes table!");
            alert.showAndWait();
        }

        return planes;
    }

    public void addPlanes(Plane plane) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO Planes(name, total_seats, business_class_seats, first_class_seats) " +
                "VALUES (?, ?, ?, ?)");
        ps.setString(1, plane.getName());
        ps.setInt(2, plane.getTotalSeats());
        ps.setInt(3, plane.getBusinessClassSeats());
        ps.setInt(4, plane.getFirstClassSeats());

        ps.executeUpdate();
    }

    public void deletePlane(Plane plane) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM Planes WHERE Name = ?");
        ps.setString(1, plane.getName());
        ps.executeUpdate();
    }

    public ArrayList<Plane> getSortedByAirlines(Airline airline) {
        ArrayList<Plane> planes = new ArrayList<>();

        try {

            PreparedStatement ps = con.prepareStatement("SELECT DISTINCT p.Name, p.Total_seats, p.First_class_seats, p.Business_class_seats\n" +
                    "FROM Flights f\n" +
                    "INNER JOIN Planes p ON p.id = f.Plane_id\n" +
                    "INNER JOIN Airlines a ON a.Id = f.Airline_id\n" +
                    "WHERE a.Name = ? ");
            ps.setString(1, airline.getName());
            ResultSet resultSet = ps.executeQuery();

            planes = getPlaneListFromResultSet(resultSet);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from planes table!");
            alert.showAndWait();
        }

        return planes;
    }

    private ArrayList<Plane> getPlaneListFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Plane> planes = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            int total = resultSet.getInt(2);
            int business = resultSet.getInt(3);
            int first = resultSet.getInt(4);
            planes.add(new Plane(name, total, business, first));
        }
        return planes;
    }
}
