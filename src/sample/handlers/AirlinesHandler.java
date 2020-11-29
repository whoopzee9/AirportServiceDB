package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Airline;
import sample.tables.Flight;
import sample.tables.Plane;

import java.sql.*;
import java.util.ArrayList;

public class AirlinesHandler {
    private Connection con;

    public AirlinesHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<Airline> getAirlines() {
        ArrayList<Airline> airlines = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT a.Name, a.Foundation_date\n" +
                    "FROM Airlines a");

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                Date date = resultSet.getDate(2);
                airlines.add(new Airline(name, date));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from airlines table!");
            alert.showAndWait();
        }

        return airlines;
    }

    public void addAirline(Airline airline) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO Airlines(name, foundation_date) " +
                "VALUES (?, ?)");
        ps.setString(1, airline.getName());
        ps.setDate(2, airline.getFoundationDate());

        ps.executeUpdate();
    }
}
