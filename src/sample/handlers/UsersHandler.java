package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Flight;
import sample.tables.Role;
import sample.tables.Ticket;
import sample.tables.User;

import java.sql.*;
import java.util.ArrayList;

public class UsersHandler {
    private Connection con;

    public UsersHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT u.Name, u.BirthDate, u.Address, u.Phone, u.Email, r.Role, u.Login\n" +
                    "FROM Users u\n" +
                    "INNER JOIN Roles r ON r.Id = u.Role_id \n");

            users = getUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from users table!");
            alert.showAndWait();
        }

        return users;
    }

    public void addUser(User user, String username, String password) throws SQLException {
        PreparedStatement ps = con.prepareStatement("EXEC CreateUser ?, ?, ?, ?, ?, ?, ?, ?");
        ps.setString(1, user.getName());
        ps.setDate(2, user.getBirthDate());
        ps.setString(3, user.getAddress());
        ps.setString(4, user.getPhone());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getRole());
        ps.setString(7, username);
        ps.setString(8, password);

        ps.executeUpdate();
    }

    public User getUser(String username) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT u.Name, u.BirthDate, u.Address, u.Phone, u.Email, r.Role, u.Login " +
                "FROM Users u " +
                "INNER JOIN Roles r on u.Role_id = r.id WHERE u.Login = ?");
        ps.setString(1, username);
        ResultSet resultSet = ps.executeQuery();

        resultSet.next();
        String name = resultSet.getString(1);
        Date date = resultSet.getDate(2);
        String address = resultSet.getString(3);
        String phone = resultSet.getString(4);
        String email = resultSet.getString(5);
        String role = resultSet.getString(6);
        String login = resultSet.getString(7);
        User user = new User(name, date, address, phone, email, role, login);
        return user;
    }

    public void updateUser(User user) {
        try {
            PreparedStatement ps = con.prepareStatement("EXEC UpdateUser ?, ?, ?, ?, ?, ?, ?");
            ps.setString(1, user.getName());
            ps.setDate(2,user.getBirthDate());
            ps.setString(3,user.getAddress());
            ps.setString(4,user.getPhone());
            ps.setString(5,user.getEmail());
            ps.setString(6,user.getRole());
            ps.setString(7,user.getUsername());

            ps.executeUpdate();
        } catch (SQLException throwables) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from users table!");
            alert.showAndWait();
        }
    }

    public void deleteUser(User user) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM Users WHERE Login = ?");
        ps.setString(1, user.getUsername());
        ps.executeUpdate();
    }

    public ArrayList<User> getSortedByRole(Role role) {
        ArrayList<User> users = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT u.Name, u.BirthDate, u.Address, u.Phone, u.Email, r.Role, u.Login\n" +
                    "FROM Users u\n" +
                    "INNER JOIN Roles r ON r.Id = u.Role_id \n" +
                    "WHERE r.Role = ?");
            ps.setString(1, role.getName());
            ResultSet resultSet = ps.executeQuery();

            users = getUsersFromResultSet(resultSet);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from users table!");
            alert.showAndWait();
        }

        return users;
    }

    private ArrayList<User> getUsersFromResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString(1);
            Date date = resultSet.getDate(2);
            String address = resultSet.getString(3);
            String phone = resultSet.getString(4);
            String email = resultSet.getString(5);
            String role = resultSet.getString(6);
            String login = resultSet.getString(7);
            users.add(new User(name, date, address, phone, email, role, login));
        }
        return users;
    }
}
