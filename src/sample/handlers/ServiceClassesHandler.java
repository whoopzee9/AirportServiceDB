package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Role;
import sample.tables.ServiceClass;

import java.sql.*;
import java.util.ArrayList;

public class ServiceClassesHandler {
    private Connection con;

    public ServiceClassesHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<ServiceClass> getClasses() {
        ArrayList<ServiceClass> classes = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT c.Name, c.Multiplier\n" +
                    "FROM Classes c");

            while (resultSet.next()) {
                String name = resultSet.getString(1);
                Double multiplier = resultSet.getDouble(2);
                classes.add(new ServiceClass(name, multiplier));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from classes table!");
            alert.showAndWait();
        }

        return classes;
    }

    public void updateClass(ServiceClass serviceClass) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE Classes " +
                    "SET Multiplier = ? " +
                    "WHERE Name = ?");
            ps.setDouble(1, serviceClass.getMultiplier());
            ps.setString(2, serviceClass.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from classes table!");
            alert.showAndWait();
        }
    }
}
