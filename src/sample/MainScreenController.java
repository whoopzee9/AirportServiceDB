package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.handlers.*;
import sample.tables.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainScreenController implements PropertyChangeListener {
    
    public TabPane TPTabPane;
    public Tab TFlights;
    public TableView<Flight> TVFlightsTable;
    public TableColumn<Flight, String> TCFlightCode;
    public TableColumn<Flight, String> TCDeparture;
    public TableColumn<Flight, String> TCDestination;
    public TableColumn<Flight, Date> TCDepartureDate;
    public TableColumn<Flight, Date> TCArrivalDate;
    public TableColumn<Flight, String> TCPlane;
    public TableColumn<Flight, String> TCAirline;
    public TableColumn<Flight, String> TCStatus;

    public Tab TTickets;
    public TableView<Ticket> TVTicketsTable;
    public TableColumn<Ticket, String> TCPassengerName;
    public TableColumn<Ticket, Date> TCPassengerBirthDate;
    public TableColumn<Ticket, String> TCPassengerPassport;
    public TableColumn<Ticket, String> TCPassengerFlightCode;
    public TableColumn<Ticket, Integer> TCPassengerSeat;
    public TableColumn<Ticket, String> TCPassengerClass;
    public TableColumn<Ticket, Double> TCTicketPrice;
    public TableColumn<Ticket, String> TCCashierName;

    public Tab TUsers;
    public TableView<User> TVUsersTable;
    public TableColumn<User, String> TCUserName;
    public TableColumn<User, Date> TCUserBirthDate;
    public TableColumn<User, String> TCUserAddress;
    public TableColumn<User, String> TCUserPhone;
    public TableColumn<User, String> TCUserEmail;
    public TableColumn<User, String> TCUserRole;

    public Tab TPlanes;
    public TableView<Plane> TVPlanesTable;
    public TableColumn<Plane, String> TCPlaneName;
    public TableColumn<Plane, Integer> TCTotalSeats;
    public TableColumn<Plane, Integer> TCBusinessSeats;
    public TableColumn<Plane, Integer> TCFirstClassSeats;

    public Tab TRoles;
    public TableView<Role> TVRolesTable;
    public TableColumn<Role, String> TCRoleName;

    public Tab TAirlines;
    public TableView<Airline> TVAirlinesTable;
    public TableColumn<Airline, String> TCAirlineName;
    public TableColumn<Airline, Date> TCFoundationDate;

    public Tab TClasses;
    public TableView<ServiceClass> TVClassesTable;
    public TableColumn<ServiceClass, String> TCClassName;
    public TableColumn<ServiceClass, Double> TCClassMultiplier;

    public Tab TTariffs;
    public TableView<Tariff> TVTariffsTable;
    public TableColumn<Tariff, Double> TCTariffBasePrice;

    public Tab TLogOut;

    private Connection con;
    private FlightsHandler flightsHandler;
    private UsersHandler usersHandler;
    private AirlinesHandler airlinesHandler;
    private PlanesHandler planesHandler;
    private RolesHandler rolesHandler;
    private ServiceClassesHandler serviceClassesHandler;
    private TariffsHandler tariffsHandler;
    private TicketsHandler ticketsHandler;

    enum Tables {
        FLIGHT,
        USER,
        TICKET,
        TARIFF,
        SERVICE_CLASS,
        ROLE,
        PLANE,
        AIRLINE
    }

    public MainScreenController() {
        System.out.println("created!");
    }

    @FXML
    public void initialize() {
        System.out.println("init started");
        TCFlightCode.setCellValueFactory(new PropertyValueFactory<>("flightCode"));
        TCDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        TCDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        TCDepartureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        TCArrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        TCPlane.setCellValueFactory(new PropertyValueFactory<>("planeName"));
        TCAirline.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        TCStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        TCPassengerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TCPassengerBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        TCPassengerPassport.setCellValueFactory(new PropertyValueFactory<>("passport"));
        TCPassengerFlightCode.setCellValueFactory(new PropertyValueFactory<>("flightCode"));
        TCPassengerSeat.setCellValueFactory(new PropertyValueFactory<>("seat"));
        TCPassengerClass.setCellValueFactory(new PropertyValueFactory<>("serviceClass"));
        TCTicketPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        TCCashierName.setCellValueFactory(new PropertyValueFactory<>("cashierName"));

        TCUserName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TCUserBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        TCUserAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        TCUserPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        TCUserEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        TCUserRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        TCPlaneName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TCTotalSeats.setCellValueFactory(new PropertyValueFactory<>("totalSeats"));
        TCBusinessSeats.setCellValueFactory(new PropertyValueFactory<>("businessClassSeats"));
        TCFirstClassSeats.setCellValueFactory(new PropertyValueFactory<>("firstClassSeats"));

        TCRoleName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TCAirlineName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TCFoundationDate.setCellValueFactory(new PropertyValueFactory<>("foundationDate"));

        TCClassName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TCClassMultiplier.setCellValueFactory(new PropertyValueFactory<>("multiplier"));

        TCTariffBasePrice.setCellValueFactory(new PropertyValueFactory<>("basePrice"));

        TFlights.setOnSelectionChanged(event -> {
            if (!TFlights.isSelected()) {
                return;
            }
            ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights());
            ObservableList<Flight> oldFlight = TVFlightsTable.getItems();
            if (!oldFlight.equals(flights)) {
                TVFlightsTable.setItems(flights);
            }
        });

        TUsers.setOnSelectionChanged(event -> {
            if (!TUsers.isSelected()) {
                return;
            }
            ObservableList<User> users = FXCollections.observableArrayList(usersHandler.getUsers());
            ObservableList<User> old = TVUsersTable.getItems();
            if (!old.equals(users)) {
                TVUsersTable.setItems(users);
            }
        });

        TAirlines.setOnSelectionChanged(event -> {
            if (!TAirlines.isSelected()) {
                return;
            }
            ObservableList<Airline> airlines = FXCollections.observableArrayList(airlinesHandler.getAirlines());
            ObservableList<Airline> old = TVAirlinesTable.getItems();
            if (!old.equals(airlines)) {
                TVAirlinesTable.setItems(airlines);
            }
        });

        TPlanes.setOnSelectionChanged(event -> {
            if (!TPlanes.isSelected()) {
                return;
            }
            ObservableList<Plane> planes = FXCollections.observableArrayList(planesHandler.getPlanes());
            ObservableList<Plane> old = TVPlanesTable.getItems();
            if (!old.equals(planes)) {
                TVPlanesTable.setItems(planes);
            }
        });

        TRoles.setOnSelectionChanged(event -> {
            if (!TRoles.isSelected()) {
                return;
            }
            ObservableList<Role> roles = FXCollections.observableArrayList(rolesHandler.getRoles());
            ObservableList<Role> old = TVRolesTable.getItems();
            if (!old.equals(roles)) {
                TVRolesTable.setItems(roles);
            }
        });

        TClasses.setOnSelectionChanged(event -> {
            if (!TClasses.isSelected()) {
                return;
            }
            ObservableList<ServiceClass> classes = FXCollections.observableArrayList(serviceClassesHandler.getClasses());
            ObservableList<ServiceClass> old = TVClassesTable.getItems();
            if (!old.equals(classes)) {
                TVClassesTable.setItems(classes);
            }
        });

        TTariffs.setOnSelectionChanged(event -> {
            if (!TTariffs.isSelected()) {
                return;
            }
            ObservableList<Tariff> tariffs = FXCollections.observableArrayList(tariffsHandler.getTariffs());
            ObservableList<Tariff> old = TVTariffsTable.getItems();
            if (!old.equals(tariffs)) {
                TVTariffsTable.setItems(tariffs);
            }
        });

        TTickets.setOnSelectionChanged(event -> {
            if (!TTickets.isSelected()) {
                return;
            }
            ObservableList<Ticket> tickets = FXCollections.observableArrayList(ticketsHandler.getTickets());
            ObservableList<Ticket> old = TVTicketsTable.getItems();
            if (!old.equals(tickets)) {
                TVTicketsTable.setItems(tickets);
            }
        });

    }

    public void onAddNewFlightClicked() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/FlightAdditionScreen.fxml"));
            Parent root = loader.load();

            FlightAdditionController contr = loader.getController();
            contr.setConnection(con);
            contr.addListener(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Airport Service");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddNewTicketClicked(ActionEvent event) {
    }

    public void onAddNewUserClicked(ActionEvent event) {
    }

    public void onAddNewPlaneClicked(ActionEvent event) {
    }

    public void onAddNewAirlineClicked(Event event) {
    }

    public void onAddNewTariffClicked(ActionEvent event) {
    }

    public void setConnection(Connection con) {
        this.con = con;
        flightsHandler = new FlightsHandler(con);
        usersHandler = new UsersHandler(con);
        airlinesHandler = new AirlinesHandler(con);
        planesHandler = new PlanesHandler(con);
        rolesHandler = new RolesHandler(con);
        serviceClassesHandler = new ServiceClassesHandler(con);
        tariffsHandler = new TariffsHandler(con);
        ticketsHandler = new TicketsHandler(con);

        ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights());
        TVFlightsTable.setItems(flights);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        Tables value = Tables.valueOf(name);

        switch (value) {
            case AIRLINE: {

            }
            case ROLE: {

            }
            case USER: {
                ObservableList<User> users = FXCollections.observableArrayList(usersHandler.getUsers());
                TVUsersTable.setItems(users);
            }
            case PLANE: {

            }
            case FLIGHT: {
                ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights());
                TVFlightsTable.setItems(flights);
            }
            case TARIFF: {

            }
            case TICKET: {

            }
            case SERVICE_CLASS: {

            }
            default: {
                //TODO ERROR
            }
        }
    }
}
