package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Role;
import sample.tables.Tariff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RolesHandler {
    private Connection con;

    public RolesHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<Role> getRoles() {
        ArrayList<Role> roles = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT r.Role\n" +
                    "FROM Roles r");

            while (resultSet.next()) {
                String role = resultSet.getString(1);
                roles.add(new Role(role));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from roles table!");
            alert.showAndWait();
        }

        return roles;
    }
}
