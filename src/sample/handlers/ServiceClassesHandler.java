package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Role;
import sample.tables.ServiceClass;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
}
