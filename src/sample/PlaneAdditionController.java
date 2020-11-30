package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.handlers.PlanesHandler;
import sample.tables.Flight;
import sample.tables.Plane;
import sample.tables.ServiceClass;
import sample.tables.Ticket;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PlaneAdditionController {
    public TextField TFName;
    public TextField TFBusiness;
    public TextField TFTotal;
    public TextField TFFirst;

    private Connection con;
    private PlanesHandler planesHandler;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public PlaneAdditionController() {
    }

    @FXML
    public void initialize() {

    }

    public void onDoneClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Wrong input");

        String name = TFName.getText();
        String totalStr = TFTotal.getText();
        String businessStr = TFBusiness.getText();
        String firstStr = TFFirst.getText();

        if (name.isEmpty() || totalStr.isEmpty() || businessStr.isEmpty() || firstStr.isEmpty()) {
            alert.setContentText("Missing input, please check all fields!");
            alert.showAndWait();
            return;
        }

        int total;
        int business;
        int first;
        try {
            total = Integer.parseInt(totalStr);
            business = Integer.parseInt(businessStr);
            first = Integer.parseInt(firstStr);
        } catch (NumberFormatException ex) {
            alert.setContentText("Wrong numbers!");
            alert.showAndWait();
            return;
        }

        Plane plane = new Plane(name, total, business, first);
        try {
            planesHandler.addPlanes(plane);

            Stage stage = (Stage) TFName.getScene().getWindow();
            stage.close();

            support.firePropertyChange(MainScreenController.Tables.PLANE.name(),1,0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            alert.setContentText("Request failed!");
            alert.showAndWait();
        }
    }

    public void setConnection(Connection con) {
        this.con = con;
        planesHandler = new PlanesHandler(con);
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
