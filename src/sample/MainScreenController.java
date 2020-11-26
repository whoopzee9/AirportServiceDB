package sample;

import Tables.*;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;

public class MainScreenController {
    
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


    public MainScreenController() {
    }

    @FXML
    public void initialize() {
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
        TCFoundationDate.setCellValueFactory(new PropertyValueFactory<>("foundationName"));

        TCClassName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TCClassMultiplier.setCellValueFactory(new PropertyValueFactory<>("multiplier"));

        TCTariffBasePrice.setCellValueFactory(new PropertyValueFactory<>("basePrice"));
    }

    
}
