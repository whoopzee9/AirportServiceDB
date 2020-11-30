package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.handlers.AirlinesHandler;
import sample.handlers.PlanesHandler;
import sample.tables.Airline;
import sample.tables.Plane;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AirlineScreenController {
    public TextField TFName;
    public DatePicker DPDate;

    private Connection con;
    private AirlinesHandler airlinesHandler;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public AirlineScreenController() {
    }

    @FXML
    public void initialize() {

    }

    public void onDoneClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Wrong input");

        String name = TFName.getText();
        String dateStr = DPDate.getEditor().getText();

        if (name.isEmpty() || dateStr.isEmpty()) {
            alert.setContentText("Missing input, please check all fields!");
            alert.showAndWait();
            return;
        }

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateTmp;
        try {
            dateTmp = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            alert.setContentText("Wrong date!");
            alert.showAndWait();
            return;
        }

        java.sql.Date foundationDate = new java.sql.Date(dateTmp.getTime());

        Airline airline = new Airline(name, foundationDate);
        try {
            airlinesHandler.addAirline(airline);

            Stage stage = (Stage) TFName.getScene().getWindow();
            stage.close();

            support.firePropertyChange(MainScreenController.Tables.AIRLINE.name(),1,0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            alert.setContentText("Request failed!");
            alert.showAndWait();
        }
    }

    public void setConnection(Connection con) {
        this.con = con;
        airlinesHandler = new AirlinesHandler(con);
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
