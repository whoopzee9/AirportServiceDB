package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.handlers.FlightsHandler;
import sample.handlers.ServiceClassesHandler;
import sample.handlers.TicketsHandler;
import sample.tables.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketAdditionController {

    public TextField TFName;
    public DatePicker DPBirthDate;
    public TextField TFPassport;
    public ComboBox<Integer> CBSeat;
    public ComboBox<Flight> CBFlight;
    public ComboBox<ServiceClass> CBClass;
    public TextField TFPrice;

    private Connection con;
    private FlightsHandler flightsHandler;
    private ServiceClassesHandler serviceClassesHandler;
    private TicketsHandler ticketsHandler;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private User currUser;
    private Flight currFlight;
    private ServiceClass currClass;
    private Plane currPlane;
    private int currTotalSeats;
    private double currTariff;

    public TicketAdditionController() {
        currFlight = null;
        currClass = null;
        currPlane = null;
        currTotalSeats = 0;
        currTariff = 0;
    }

    @FXML
    public void initialize() {
        TFPrice.setText("0");
        CBFlight.setOnMouseClicked(event -> {
            ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights());
            ObservableList<Flight> oldFlights = CBFlight.getItems();
            if (!flights.equals(oldFlights)) {
                CBFlight.setItems(flights);
            }
        });
        CBClass.setOnMouseClicked(event -> {
            ObservableList<ServiceClass> classes = FXCollections.observableArrayList(serviceClassesHandler.getClasses());
            ObservableList<ServiceClass> oldClasses = CBClass.getItems();
            if (!classes.equals(oldClasses)) {
                CBClass.setItems(classes);
            }
        });

        CBSeat.setOnMouseClicked(event -> {
            if (currFlight == null || currClass == null) {
                return;
            }
            ObservableList<Integer> seats = FXCollections.observableArrayList(
                    ticketsHandler.getFreeSeats(currFlight.getFlightCode(), currClass.getName(), currTotalSeats));
            ObservableList<Integer> oldSeats = CBSeat.getItems();
            if (!seats.equals(oldSeats)) {
                CBSeat.setItems(seats);
            }
        });

        CBFlight.setOnAction(event -> {
            currFlight = CBFlight.getValue();
            currClass = CBClass.getValue();

            if (currFlight != null) {
                currTariff = flightsHandler.getFlightTariff(currFlight.getFlightCode());
                currPlane = flightsHandler.getFlightPlane(currFlight.getFlightCode());
            }
            if (currClass != null) {
                classComboBoxHandler();
            }
        });

        CBClass.setOnAction(event -> {
            currClass = CBClass.getValue();

            if (currClass != null && currFlight != null) {
                classComboBoxHandler();
            }
        });
    }

    public void onDoneClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Wrong input");

        String name = TFName.getText();
        String dateStr = DPBirthDate.getEditor().getText();
        String passport = TFPassport.getText();
        Integer seat = CBSeat.getValue();
        Flight flight = CBFlight.getValue();
        ServiceClass serviceClass = CBClass.getValue();

        if (name.isEmpty() || dateStr.isEmpty() || passport.isEmpty() || seat == null
            || flight == null || serviceClass == null) {
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

        java.sql.Date birthDate = new java.sql.Date(dateTmp.getTime());

        Ticket ticket = new Ticket(name, birthDate, passport, flight.getFlightCode(), seat, serviceClass.getName(), 0, currUser.getName());
        try {
            ticketsHandler.addTicket(ticket);

            Stage stage = (Stage) TFName.getScene().getWindow();
            stage.close();

            support.firePropertyChange(MainScreenController.Tables.TICKET.name(),1,0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            alert.setContentText("Request failed!");
            alert.showAndWait();
        }
    }

    public void setParameters(Connection con, User user) {
        this.con = con;
        currUser = user;
        flightsHandler = new FlightsHandler(con);
        serviceClassesHandler = new ServiceClassesHandler(con);
        ticketsHandler = new TicketsHandler(con);
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void classComboBoxHandler() {
        double currMultiplier = currClass.getMultiplier();
        Double price = currMultiplier * currTariff;
        TFPrice.setText(price.toString());
        switch (currClass.getName()) {
            case "economy" -> {
                currTotalSeats = currPlane.getTotalSeats() - currPlane.getBusinessClassSeats() - currPlane.getFirstClassSeats();
            }
            case "business" -> {
                currTotalSeats = currPlane.getBusinessClassSeats();
            }
            case "first" -> {
                currTotalSeats = currPlane.getFirstClassSeats();
            }
            default -> {
                //TODO ERROR
            }
        }
    }

}
