package sample.handlers;

import javafx.scene.control.Alert;
import sample.tables.Plane;
import sample.tables.Tariff;

import java.sql.*;
import java.util.ArrayList;

public class TariffsHandler {
    private Connection con;

    public TariffsHandler(Connection con) {
        this.con = con;
    }

    public ArrayList<Tariff> getTariffs() {
        ArrayList<Tariff> tariffs = new ArrayList<>();

        try {
            Statement statement = con.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT t.Base_price\n" +
                    "FROM Tariffs t");

            while (resultSet.next()) {
                double price = resultSet.getDouble(1);
                tariffs.add(new Tariff(price));
            }
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from tariffs table!");
            alert.showAndWait();
        }

        return tariffs;
    }

    public void addTariff(Tariff tariff) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO Tariffs(Base_price) " +
                "VALUES (?)");
        ps.setDouble(1, tariff.getBasePrice());

        ps.executeUpdate();
    }

    public void updateTariff(Tariff newTariff, Tariff oldTariff) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE Tariffs " +
                    "SET Base_price = ? " +
                    "WHERE Base_price = ?");
            ps.setDouble(1, newTariff.getBasePrice());
            ps.setDouble(2, oldTariff.getBasePrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No permission");
            alert.setContentText("You don't have permission to read from tariffs table!");
            alert.showAndWait();
        }
    }

    public void deleteTariff(Tariff tariff) throws SQLException {
        PreparedStatement ps = con.prepareStatement("DELETE FROM Tariffs WHERE Base_price = ?");
        ps.setDouble(1, tariff.getBasePrice());
        ps.executeUpdate();
    }
}
