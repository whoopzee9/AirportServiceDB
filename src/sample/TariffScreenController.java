package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.handlers.AirlinesHandler;
import sample.handlers.TariffsHandler;
import sample.tables.Airline;
import sample.tables.Tariff;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TariffScreenController {
    public TextField TFBasePrice;

    private Connection con;
    private TariffsHandler tariffsHandler;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public TariffScreenController() {
    }

    @FXML
    public void initialize() {
    }

    public void onDoneClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Wrong input");

        String priceStr = TFBasePrice.getText();

        if (priceStr.isEmpty()) {
            alert.setContentText("Missing input, please check all fields!");
            alert.showAndWait();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            alert.setContentText("Wrong price!");
            alert.showAndWait();
            return;
        }

        Tariff tariff = new Tariff(price);
        try {
            tariffsHandler.addTariff(tariff);

            Stage stage = (Stage) TFBasePrice.getScene().getWindow();
            stage.close();

            support.firePropertyChange(MainScreenController.Tables.TARIFF.name(),1,0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            alert.setContentText("Request failed!");
            alert.showAndWait();
        }
    }

    public void setConnection(Connection con) {
        this.con = con;
        tariffsHandler = new TariffsHandler(con);
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
