package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.handlers.AirlinesHandler;
import sample.handlers.FlightsHandler;
import sample.handlers.PlanesHandler;
import sample.handlers.TariffsHandler;
import sample.tables.Airline;
import sample.tables.Flight;
import sample.tables.Plane;
import sample.tables.Tariff;

import javax.xml.bind.SchemaOutputResolver;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FlightAdditionController {

    public TextField TfFlightCode;
    public TextField TFDeparture;
    public TextField TFDestination;
    public DatePicker DPDepartureDate;
    public DatePicker DPArrivalDate;
    public ComboBox<Plane> CBPlane;
    public ComboBox<Airline> CBAirline;
    public TextField TFStatus;
    public ComboBox<Tariff> CBBasePrice;
    public Spinner<Integer> SpDepartureTimeHours;
    public Spinner<Integer> SpDepartureTimeMins;
    public Spinner<Integer> SpArrivalTimeHours;
    public Spinner<Integer> SpArrivalTimeMins;
    private Connection con;
    private PlanesHandler planesHandler;
    private AirlinesHandler airlinesHandler;
    private TariffsHandler tariffsHandler;
    private FlightsHandler flightsHandler;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public FlightAdditionController() {
    }

    @FXML
    public void initialize() {
        CBPlane.setOnMouseClicked(event -> {
            ObservableList<Plane> planes = FXCollections.observableArrayList(planesHandler.getPlanes());
            ObservableList<Plane> oldPlanes = CBPlane.getItems();
            if (!planes.equals(oldPlanes)) {
                CBPlane.setItems(planes);
            }
        });

        CBAirline.setOnMouseClicked(event -> {
            ObservableList<Airline> airlines = FXCollections.observableArrayList(airlinesHandler.getAirlines());
            ObservableList<Airline> oldAirlines = CBAirline.getItems();

            if (!airlines.equals(oldAirlines)) {
                CBAirline.setItems(airlines);
            }
        });

        CBBasePrice.setOnMouseClicked(event -> {
            ObservableList<Tariff> tariffs = FXCollections.observableArrayList(tariffsHandler.getTariffs());
            ObservableList<Tariff> oldTariffs = CBBasePrice.getItems();

            if (!tariffs.equals(oldTariffs)) {
                CBBasePrice.setItems(tariffs);
            }
        });

        SpDepartureTimeHours.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                ValueFactory fact = new ValueFactory(23);
                this.setValue(fact.decrement(this.getValue(), steps));
            }

            @Override
            public void increment(int steps) {
                ValueFactory fact = new ValueFactory(23);
                this.setValue(fact.increment(this.getValue(), steps));
            }
        });
        SpDepartureTimeHours.getValueFactory().setValue(0);
        SpDepartureTimeHours.getEditor().setOnAction(event -> {
            ValueFactory fact = new ValueFactory(23);
            fact.onAction(event, SpDepartureTimeHours);
        });

        SpDepartureTimeMins.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                ValueFactory fact = new ValueFactory(59);
                this.setValue(fact.decrement(this.getValue(), steps));
            }

            @Override
            public void increment(int steps) {
                ValueFactory fact = new ValueFactory(59);
                this.setValue(fact.increment(this.getValue(), steps));
            }
        });
        SpDepartureTimeMins.getValueFactory().setValue(0);
        SpDepartureTimeMins.getEditor().setOnAction(event -> {
            ValueFactory fact = new ValueFactory(59);
            fact.onAction(event, SpDepartureTimeMins);
        });

        SpArrivalTimeHours.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                ValueFactory fact = new ValueFactory(23);
                this.setValue(fact.decrement(this.getValue(), steps));
            }

            @Override
            public void increment(int steps) {
                ValueFactory fact = new ValueFactory(23);
                this.setValue(fact.increment(this.getValue(), steps));
            }
        });
        SpArrivalTimeHours.getValueFactory().setValue(0);
        SpArrivalTimeHours.getEditor().setOnAction(event -> {
            ValueFactory fact = new ValueFactory(23);
            fact.onAction(event, SpArrivalTimeHours);
        });

        SpArrivalTimeMins.setValueFactory(new SpinnerValueFactory<Integer>() {
            @Override
            public void decrement(int steps) {
                ValueFactory fact = new ValueFactory(59);
                this.setValue(fact.decrement(this.getValue(), steps));
            }

            @Override
            public void increment(int steps) {
                ValueFactory fact = new ValueFactory(59);
                this.setValue(fact.increment(this.getValue(), steps));
            }
        });
        SpArrivalTimeMins.getValueFactory().setValue(0);
        SpArrivalTimeMins.getEditor().setOnAction(event -> {
            ValueFactory fact = new ValueFactory(59);
            fact.onAction(event, SpArrivalTimeMins);
        });
    }

    public void onDoneClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Wrong input");

        String flightCode = TfFlightCode.getText();
        String departure = TFDeparture.getText();
        String destination = TFDestination.getText();
        String departureDate = DPDepartureDate.getEditor().getText();
        int departureTimeHours = SpDepartureTimeHours.getValue();
        int departureTimeMins = SpDepartureTimeMins.getValue();
        String arrivalDate = DPArrivalDate.getEditor().getText();
        int arrivalTimeHours = SpArrivalTimeHours.getValue();
        int arrivalTimeMins = SpArrivalTimeMins.getValue();
        Plane plane = CBPlane.getValue();
        Airline airline = CBAirline.getValue();
        String status = TFStatus.getText();
        Tariff tariff = CBBasePrice.getValue();

        if (flightCode.isEmpty() || departure.isEmpty() || destination.isEmpty() || departureDate.isEmpty()
            || arrivalDate.isEmpty() || plane == null || airline == null || status.isEmpty() || tariff == null) {
            alert.setContentText("Missing input, please check all fields!");
            alert.showAndWait();
            return;
        }

        if (departure.equals(destination) || (!departure.equals("Moscow") && !destination.equals("Moscow"))) {
            alert.setContentText("Wrong destination and/or departure!");
            alert.showAndWait();
            return;
        }

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date depDate;
        Date arrDate;
        Timestamp departureTime;
        Timestamp arrivalTime;
        try {
            depDate = format.parse(departureDate);
            arrDate = format.parse(arrivalDate);
        } catch (ParseException e) {
            e.printStackTrace();
            alert.setContentText("Wrong dates!");
            alert.showAndWait();
            return;
        }
        if (depDate != null && arrDate != null) {
            long time = depDate.getTime();
            time += departureTimeHours * 60 * 60 * 1000;
            time += departureTimeMins * 60 * 1000;
            departureTime = new Timestamp(time);

            time = arrDate.getTime();
            time += arrivalTimeHours * 60 * 60 * 1000;
            time += arrivalTimeMins * 60 * 1000;
            arrivalTime = new Timestamp(time);
        } else {
            alert.setContentText("Wrong dates!");
            alert.showAndWait();
            return;
        }

        if (arrivalTime.before(departureTime) || arrivalTime.equals(departureTime)) {
            alert.setContentText("Wrong dates!");
            alert.showAndWait();
            return;
        }

        Flight flight = new Flight(flightCode, departure, destination, departureTime, arrivalTime,
                plane.getName(), airline.getName(), status, tariff.getBasePrice());

        try {
            flightsHandler.addFlight(flight);

            Stage stage = (Stage) TFDeparture.getScene().getWindow();
            stage.close();

            support.firePropertyChange(MainScreenController.Tables.FLIGHT.name(),1,0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            alert.setContentText("Request failed!");
            alert.showAndWait();
        }
    }

    public void setConnection(Connection con) {
        this.con = con;
        planesHandler = new PlanesHandler(con);
        airlinesHandler = new AirlinesHandler(con);
        tariffsHandler = new TariffsHandler(con);
        flightsHandler = new FlightsHandler(con);
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private static class ValueFactory {
        private final int max;

        public ValueFactory(int max) {
            this.max = max;
        }

        public int increment(int curr, int steps) {
            if (curr != max) {
                curr += steps;
            } else {
                curr = 0;
            }
            return curr;
        }

        public int decrement(int curr, int steps) {
            if (curr != 0) {
                curr -= steps;
            } else {
                curr = max;
            }
            return curr;
        }

        public void onAction(ActionEvent event, Spinner<Integer> spinner) {
            Integer oldValue = spinner.getValue();
            String text = spinner.getEditor().getText();
            Integer newValue;
            try {
                newValue = Integer.parseInt(text);
            } catch (NumberFormatException e) {
                newValue = oldValue;
            }
            if (newValue > max || newValue < 0) {
                newValue = oldValue;
            }
            spinner.getEditor().setText(newValue.toString());
            spinner.getValueFactory().setValue(newValue);
        }
    }
}
