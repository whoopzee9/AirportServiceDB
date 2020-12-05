package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.handlers.*;
import sample.tables.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainScreenController implements PropertyChangeListener {

    public TabPane TPTabPane;
    public Tab TFlights;
    public TableView<Flight> TVFlightsTable;
    public TableColumn<Flight, String> TCFlightCode;
    public TableColumn<Flight, String> TCDeparture;
    public TableColumn<Flight, String> TCDestination;
    public TableColumn<Flight, Timestamp> TCDepartureDate;
    public TableColumn<Flight, Timestamp> TCArrivalDate;
    public TableColumn<Flight, Plane> TCPlane;
    public TableColumn<Flight, Airline> TCAirline;
    public TableColumn<Flight, String> TCStatus;
    public TableColumn<Flight, Double> TCFlightPrice;
    public Button BAddFlights;
    public DatePicker DPDepartureFrom;
    public DatePicker DPDepartureTo;
    public ComboBox<String> CBFlightsSort;
    public CheckBox ChBFlightsIsRelevant;
    public TextField TFFlightsCustom;
    public Label LFlightsCustom;
    public Label LDepartureFrom;
    public Label LDepartureTo;

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
    public ComboBox<String> CBTicketsSort;
    public CheckBox ChBTicketsIsRelevant;
    public ComboBox<Flight> CBTicketsFlights;
    public ComboBox<User> CBTicketsCashiers;
    public ComboBox<ServiceClass> CBTicketsClasses;
    public Label LTicketsFlights;
    public Label LTicketsClasses;
    public Label LTicketsCashiers;
    public Button BAddTickets;

    public Tab TUsers;
    public TableView<User> TVUsersTable;
    public TableColumn<User, String> TCUserName;
    public TableColumn<User, Date> TCUserBirthDate;
    public TableColumn<User, String> TCUserAddress;
    public TableColumn<User, String> TCUserPhone;
    public TableColumn<User, String> TCUserEmail;
    public TableColumn<User, Role> TCUserRole;
    public TableColumn<User, String> TCUserLogin;
    public ComboBox<String> CBUsersSort;
    public ComboBox<Role> CBUsersRoles;
    public Label LUsersRole;

    public Tab TPlanes;
    public TableView<Plane> TVPlanesTable;
    public TableColumn<Plane, String> TCPlaneName;
    public TableColumn<Plane, Integer> TCTotalSeats;
    public TableColumn<Plane, Integer> TCBusinessSeats;
    public TableColumn<Plane, Integer> TCFirstClassSeats;
    public ComboBox<String> CBPlanesSort;
    public ComboBox<Airline> CBPlanesAirlines;
    public Label LPlanesAirline;
    public Button BAddPlanes;

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
    private Roles currRole;
    private User currUser;

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

    enum Roles {
        ADMIN,
        CASHIER,
        DISPATCHER
    }

    public MainScreenController() {
        System.out.println("created!");
    }

    @FXML
    public void initialize() {
        //Tables init
        TCFlightCode.setCellValueFactory(new PropertyValueFactory<>("flightCode"));
        TCDeparture.setCellValueFactory(new PropertyValueFactory<>("departure"));
        TCDestination.setCellValueFactory(new PropertyValueFactory<>("destination"));
        TCDepartureDate.setCellValueFactory(new PropertyValueFactory<>("departureDate"));
        TCArrivalDate.setCellValueFactory(new PropertyValueFactory<>("arrivalDate"));
        TCPlane.setCellValueFactory(new PropertyValueFactory<>("planeName"));
        TCAirline.setCellValueFactory(new PropertyValueFactory<>("airlineName"));
        TCStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        TCFlightPrice.setCellValueFactory(new PropertyValueFactory<>("basePrice"));

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
        TCUserLogin.setCellValueFactory(new PropertyValueFactory<>("username"));

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

        //Flights comboBoxes init---------------------
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("none");
        list.add("Destination and departure dates");
        list.add("specific airline");
        list.add("flights with first and business class");
        list.add("lower base price");
        CBFlightsSort.setItems(list);
        CBFlightsSort.getSelectionModel().select(0);
        DPDepartureFrom.setVisible(false);
        DPDepartureTo.setVisible(false);
        LDepartureFrom.setVisible(false);
        LDepartureTo.setVisible(false);
        TFFlightsCustom.setVisible(false);
        LFlightsCustom.setVisible(false);

        //Tickets comboBox init-----------------------------
        list = FXCollections.observableArrayList();
        list.add("none");
        list.add("specific flight");
        list.add("specific cashier");
        list.add("specific flight and service class");
        CBTicketsSort.setItems(list);
        CBTicketsSort.getSelectionModel().select(0);
        LTicketsFlights.setVisible(false);
        LTicketsClasses.setVisible(false);
        LTicketsCashiers.setVisible(false);
        CBTicketsFlights.setVisible(false);
        CBTicketsClasses.setVisible(false);
        CBTicketsCashiers.setVisible(false);

        list = FXCollections.observableArrayList();
        list.add("none");
        list.add("specific airline");
        CBPlanesSort.setItems(list);
        CBPlanesSort.getSelectionModel().select(0);
        LPlanesAirline.setVisible(false);
        CBPlanesAirlines.setVisible(false);

        list = FXCollections.observableArrayList();
        list.add("none");
        list.add("specific role");
        CBUsersSort.setItems(list);
        CBUsersSort.getSelectionModel().select(0);
        LUsersRole.setVisible(false);
        CBUsersRoles.setVisible(false);

        CBTicketsFlights.setOnMouseClicked(event -> {
            ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights(ChBTicketsIsRelevant.isSelected()));
            ObservableList<Flight> oldFlights = CBTicketsFlights.getItems();
            if (!flights.equals(oldFlights)) {
                CBTicketsFlights.setItems(flights);
            }
        });

        CBTicketsClasses.setOnMouseClicked(event -> {
            ObservableList<ServiceClass> serviceClasses = FXCollections.observableArrayList(serviceClassesHandler.getClasses());
            ObservableList<ServiceClass> oldClasses = CBTicketsClasses.getItems();
            if (!serviceClasses.equals(oldClasses)) {
                CBTicketsClasses.setItems(serviceClasses);
            }
        });

        CBTicketsCashiers.setOnMouseClicked(event -> {
            ObservableList<User> cashiers = FXCollections.observableArrayList(usersHandler.getSortedByRole(new Role("cashier")));
            ObservableList<User> oldCashiers = CBTicketsCashiers.getItems();
            if (!cashiers.equals(oldCashiers)) {
                CBTicketsCashiers.setItems(cashiers);
            }
        });

        CBPlanesAirlines.setOnMouseClicked(event -> {
            ObservableList<Airline> airlines = FXCollections.observableArrayList(airlinesHandler.getAirlines());
            ObservableList<Airline> oldAirlines = CBPlanesAirlines.getItems();
            if (!airlines.equals(oldAirlines)) {
                CBPlanesAirlines.setItems(airlines);
            }
        });

        CBUsersRoles.setOnMouseClicked(event -> {
            ObservableList<Role> roles = FXCollections.observableArrayList(rolesHandler.getRoles());
            ObservableList<Role> oldRoles = CBUsersRoles.getItems();
            if (!roles.equals(oldRoles)) {
                CBUsersRoles.setItems(roles);
            }
        });

        //Editing initialization
        StringConverter<Timestamp> timestampStringConverter = new StringConverter<Timestamp>() {
            @Override
            public String toString(Timestamp object) {
                if (object == null) {
                    return null;
                }
                return object.toString();
            }

            @Override
            public Timestamp fromString(String string) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
                java.util.Date parsedDate;
                try {
                    parsedDate = dateFormat.parse(string);
                } catch (ParseException e) {
                    showAlert("Wrong input!", "Wrong timestamp!");
                    return null;
                }
                return new Timestamp(parsedDate.getTime());
            }
        };

        StringConverter<Date> dateStringConverter = new StringConverter<Date>() {
            @Override
            public String toString(Date object) {
                if (object == null) {
                    return null;
                }
                return object.toString();
            }

            @Override
            public Date fromString(String string) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date parsedDate;
                try {
                    parsedDate = dateFormat.parse(string);
                } catch (ParseException e) {
                    showAlert("Wrong input!", "Wrong date!");
                    return null;
                }
                return new Date(parsedDate.getTime());
            }
        };

        StringConverter<Double> doubleStringConverter = new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                if (object == null) {
                    return null;
                }
                return object.toString();
            }

            @Override
            public Double fromString(String string) {
                Double value;
                try {
                    value = Double.parseDouble(string);
                } catch (NumberFormatException e) {
                    showAlert("Wrong input!", "Wrong number!");
                    return null;
                }
                return value;
            }
        };
        TCDepartureDate.setCellFactory(param -> new TextFieldTableCell<>(timestampStringConverter));
        TCArrivalDate.setCellFactory(param -> new TextFieldTableCell<>(timestampStringConverter));
        TCPlaneName.setCellFactory(ComboBoxTableCell.forTableColumn());
        TCStatus.setCellFactory(TextFieldTableCell.forTableColumn());

        TCUserName.setCellFactory(TextFieldTableCell.forTableColumn());
        TCUserBirthDate.setCellFactory(param -> new TextFieldTableCell<>(dateStringConverter));
        TCUserAddress.setCellFactory(TextFieldTableCell.forTableColumn());
        TCUserPhone.setCellFactory(TextFieldTableCell.forTableColumn());
        TCUserEmail.setCellFactory(TextFieldTableCell.forTableColumn());

        TCClassMultiplier.setCellFactory(param -> new TextFieldTableCell<>(doubleStringConverter));

        TCTariffBasePrice.setCellFactory(param -> new TextFieldTableCell<>(doubleStringConverter));

        //Tab updating
        TFlights.setOnSelectionChanged(event -> {
            if (!TFlights.isSelected()) {
                return;
            }
            ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights(false));
            ObservableList<Flight> oldFlight = TVFlightsTable.getItems();
            if (!oldFlight.equals(flights)) {
                TVFlightsTable.setItems(flights);
            }
            ChBFlightsIsRelevant.setSelected(false);
            TCPlane.setCellFactory(param -> new ComboBoxTableCell<>(FXCollections.observableArrayList(planesHandler.getPlanes())));
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
            ObservableList<Ticket> tickets = FXCollections.observableArrayList(ticketsHandler.getTickets(false));
            ObservableList<Ticket> old = TVTicketsTable.getItems();
            if (!old.equals(tickets)) {
                TVTicketsTable.setItems(tickets);
            }
            ChBTicketsIsRelevant.setSelected(false);
        });
    }

    //ADDING HANDLERS---------------------------------------------------------------------

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
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/TicketAdditionScreen.fxml"));
            Parent root = loader.load();

            TicketAdditionController contr = loader.getController();
            contr.setParameters(con, currUser);
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

    public void onAddNewUserClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/UserAdditionScreen.fxml"));
            Parent root = loader.load();

            UserAdditionController contr = loader.getController();
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

    public void onAddNewPlaneClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/PlaneAdditionScreen.fxml"));
            Parent root = loader.load();

            PlaneAdditionController contr = loader.getController();
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

    public void onAddNewAirlineClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/AirlineAdditionScreen.fxml"));
            Parent root = loader.load();

            AirlineScreenController contr = loader.getController();
            contr.setConnection(con);
            contr.addListener(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Airport Service");
            stage.setScene(new Scene(root, 500, 300));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onAddNewTariffClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/TariffAdditionScreen.fxml"));
            Parent root = loader.load();

            TariffScreenController contr = loader.getController();
            contr.setConnection(con);
            contr.addListener(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Airport Service");
            stage.setScene(new Scene(root, 500, 300));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onLogOutClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) TPTabPane.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/AuthorizationScreen.fxml"));
            Parent root = loader.load();

            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Airport Service");
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onExitClicked() {
        Stage stage = (Stage) TPTabPane.getScene().getWindow();
        stage.close();
    }

    //EDITING HANDLERS--------------------------------------------------------------------------

    public void onDepartureDateEditCommit(TableColumn.CellEditEvent<Flight, Timestamp> event) {
        TablePosition<Flight, Timestamp> pos = event.getTablePosition();

        Timestamp newTimestamp = event.getNewValue();
        int row = pos.getRow();
        Flight flight = event.getTableView().getItems().get(row);
        if (newTimestamp == null) {
            event.getTableView().getItems().set(row, flight);
            return;
        }
        flight.setDepartureDate(newTimestamp);

        flightsHandler.updateFlight(flight);
    }

    public void onArrivalDateEditCommit(TableColumn.CellEditEvent<Flight, Timestamp> event) {
        onDepartureDateEditCommit(event);
    }

    public void onPlaneNameEditCommit(TableColumn.CellEditEvent<Flight, Plane> event) {
        TablePosition<Flight, Plane> pos = event.getTablePosition();

        Plane plane = event.getNewValue();
        int row = pos.getRow();
        Flight flight = event.getTableView().getItems().get(row);
        if (plane == null) {
            event.getTableView().getItems().set(row, flight);
            return;
        }
        flight.setPlaneName(plane.getName());

        flightsHandler.updateFlight(flight);
    }

    public void onStatusEditCommit(TableColumn.CellEditEvent<Flight, String> event) {
        TablePosition<Flight, String> pos = event.getTablePosition();

        String status = event.getNewValue();
        int row = pos.getRow();
        Flight flight = event.getTableView().getItems().get(row);
        if (status.isEmpty()) {
            event.getTableView().getItems().set(row, flight);
            return;
        }
        flight.setStatus(status);

        flightsHandler.updateFlight(flight);
    }

    public void onUserNameEditCommit(TableColumn.CellEditEvent<User, String> event) {
        TablePosition<User, String> pos = event.getTablePosition();

        String name = event.getNewValue();
        int row = pos.getRow();
        User user = event.getTableView().getItems().get(row);
        if (name.isEmpty()) {
            event.getTableView().getItems().set(row, user);
            return;
        }
        user.setName(name);

        usersHandler.updateUser(user);
    }

    public void onUserBirthDateEditCommit(TableColumn.CellEditEvent<User, Date> event) {
        TablePosition<User, Date> pos = event.getTablePosition();

        Date date = event.getNewValue();
        int row = pos.getRow();
        User user = event.getTableView().getItems().get(row);
        if (date == null) {
            event.getTableView().getItems().set(row, user);
            return;
        }
        user.setBirthDate(date);

        usersHandler.updateUser(user);
    }

    public void onUserAddressEditCommit(TableColumn.CellEditEvent<User, String> event) {
        TablePosition<User, String> pos = event.getTablePosition();

        String address = event.getNewValue();
        int row = pos.getRow();
        User user = event.getTableView().getItems().get(row);
        if (address.isEmpty()) {
            event.getTableView().getItems().set(row, user);
            return;
        }
        user.setAddress(address);

        usersHandler.updateUser(user);
    }

    public void onUserPhoneEditCommit(TableColumn.CellEditEvent<User, String> event) {
        TablePosition<User, String> pos = event.getTablePosition();

        String phone = event.getNewValue();
        int row = pos.getRow();
        User user = event.getTableView().getItems().get(row);
        if (phone.isEmpty()) {
            event.getTableView().getItems().set(row, user);
            return;
        }
        user.setPhone(phone);

        usersHandler.updateUser(user);
    }

    public void onUserEmailEditCommit(TableColumn.CellEditEvent<User, String> event) {
        TablePosition<User, String> pos = event.getTablePosition();

        String email = event.getNewValue();
        int row = pos.getRow();
        User user = event.getTableView().getItems().get(row);
        if (email.isEmpty()) {
            event.getTableView().getItems().set(row, user);
            return;
        }
        user.setEmail(email);

        usersHandler.updateUser(user);
    }

    public void onUserRoleEditCommit(TableColumn.CellEditEvent<User, Role> event) {
        TablePosition<User, Role> pos = event.getTablePosition();

        Role role = event.getNewValue();
        int row = pos.getRow();
        User user = event.getTableView().getItems().get(row);
        if (role == null) {
            event.getTableView().getItems().set(row, user);
            return;
        }
        user.setRole(role.getName());

        usersHandler.updateUser(user);
    }

    public void onClassMultiplierEditCommit(TableColumn.CellEditEvent<ServiceClass, Double> event) {
        TablePosition<ServiceClass, Double> pos = event.getTablePosition();

        Double multiplier = event.getNewValue();
        int row = pos.getRow();
        ServiceClass serviceClass = event.getTableView().getItems().get(row);
        if (multiplier == null || multiplier == 0) {
            event.getTableView().getItems().set(row, serviceClass);
            return;
        }
        serviceClass.setMultiplier(multiplier);

        serviceClassesHandler.updateClass(serviceClass);
    }

    public void onBasePriceEditCommit(TableColumn.CellEditEvent<Tariff, Double> event) {
        TablePosition<Tariff, Double> pos = event.getTablePosition();

        Double newPrice = event.getNewValue();
        int row = pos.getRow();
        Tariff oldTariff = event.getTableView().getItems().get(row);
        if (newPrice == null || newPrice == 0) {
            event.getTableView().getItems().set(row, oldTariff);
            return;
        }
        Tariff newTariff = new Tariff(newPrice);

        tariffsHandler.updateTariff(newTariff, oldTariff);
    }


    //DELETING HANDLERS---------------------------------------------------------------------

    public void onDeleteTicketClicked(ActionEvent event) {
        int index = TVTicketsTable.getSelectionModel().getFocusedIndex();
        if (index == 0 && !TVTicketsTable.getSelectionModel().isSelected(0)) {
            showAlert("Wrong input!", "Nothing is selected!");
            return;
        }
        Ticket ticket = TVTicketsTable.getItems().get(index);
        try {
            ticketsHandler.deleteTicket(ticket);
            TVTicketsTable.getItems().remove(index);
        } catch (SQLException throwables) {
            showAlert("Deleting conflict!", "Can not delete. Please check all dependencies!");
        }
    }

    public void onDeleteUserClicked(ActionEvent event) {
        int index = TVUsersTable.getSelectionModel().getFocusedIndex();
        if (index == 0 && !TVUsersTable.getSelectionModel().isSelected(0)) {
            showAlert("Wrong input!", "Nothing is selected!");
            return;
        }
        User user = TVUsersTable.getItems().get(index);
        try {
            usersHandler.deleteUser(user);
            TVUsersTable.getItems().remove(index);
        } catch (SQLException throwables) {
            showAlert("Deleting conflict!", "Can not delete. Please check all dependencies!");
        }
    }

    public void onDeletePlaneClicked(ActionEvent event) {
        int index = TVPlanesTable.getSelectionModel().getFocusedIndex();
        if (index == 0 && !TVPlanesTable.getSelectionModel().isSelected(0)) {
            showAlert("Wrong input!", "Nothing is selected!");
            return;
        }
        Plane plane = TVPlanesTable.getItems().get(index);
        try {
            planesHandler.deletePlane(plane);
            TVPlanesTable.getItems().remove(index);
        } catch (SQLException throwables) {
            showAlert("Deleting conflict!", "Can not delete. Please check all dependencies!");
        }
    }

    public void onDeleteAirlineClicked(ActionEvent event) {
        int index = TVAirlinesTable.getSelectionModel().getFocusedIndex();
        if (index == 0 && !TVAirlinesTable.getSelectionModel().isSelected(0)) {
            showAlert("Wrong input!", "Nothing is selected!");
            return;
        }
        Airline airline = TVAirlinesTable.getItems().get(index);
        try {
            airlinesHandler.deleteAirline(airline);
            TVAirlinesTable.getItems().remove(index);
        } catch (SQLException throwables) {
            showAlert("Deleting conflict!", "Can not delete. Please check all dependencies!");
        }
    }

    public void onDeleteTariffClicked(ActionEvent event) {
        int index = TVTariffsTable.getSelectionModel().getFocusedIndex();
        if (index == 0 && !TVTariffsTable.getSelectionModel().isSelected(0)) {
            showAlert("Wrong input!", "Nothing is selected!");
            return;
        }
        Tariff tariff = TVTariffsTable.getItems().get(index);
        try {
            tariffsHandler.deleteTariff(tariff);
            TVTariffsTable.getItems().remove(index);
        } catch (SQLException throwables) {
            showAlert("Deleting conflict!", "Can not delete. Please check all dependencies!");
        }
    }

    //SORTING HANDLERS----------------------------------------------------------------
    public void CBFlightsOnAction(ActionEvent event) {
        int index = CBFlightsSort.getSelectionModel().getSelectedIndex();
        DPDepartureFrom.setVisible(false);
        DPDepartureTo.setVisible(false);
        LDepartureFrom.setVisible(false);
        LDepartureTo.setVisible(false);
        TFFlightsCustom.clear();
        DPDepartureFrom.getEditor().clear();
        DPDepartureTo.getEditor().clear();
        switch (index) {
            case 0, 3 -> {      //none     //flights with first and business class
                TFFlightsCustom.setVisible(false);
                LFlightsCustom.setVisible(false);
            }
            case 1 -> {      //Destination and departure dates
                DPDepartureFrom.setVisible(true);
                DPDepartureTo.setVisible(true);
                TFFlightsCustom.setVisible(true);
                LFlightsCustom.setVisible(true);
                LFlightsCustom.setText("Destination");
                LDepartureFrom.setVisible(true);
                LDepartureTo.setVisible(true);
            }
            case 2 -> {      //specific airline
                TFFlightsCustom.setVisible(true);
                LFlightsCustom.setVisible(true);
                LFlightsCustom.setText("Airline");
            }
            case 4 -> {      //lower base price
                TFFlightsCustom.setVisible(true);
                LFlightsCustom.setVisible(true);
                LFlightsCustom.setText("Base price");
            }
            default -> {

            }
        }
    }

    public void onFlightsSortClicked(ActionEvent event) {
        int sortIndex = CBFlightsSort.getSelectionModel().getSelectedIndex();
        boolean isRelevant = ChBFlightsIsRelevant.isSelected();
        String text = TFFlightsCustom.getText();

        switch (sortIndex) {
            case 0 -> {      //none
                ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights(isRelevant));
                TVFlightsTable.setItems(flights);
            }
            case 1 -> {      //Destination and departure dates
                if (text.isEmpty()) {
                    showAlert("Wrong input!", "Text is empty!");
                    return;
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
                java.util.Date parsedFrom;
                java.util.Date parsedTo;
                try {
                    parsedFrom = dateFormat.parse(DPDepartureFrom.getEditor().getText());
                    parsedTo = dateFormat.parse(DPDepartureTo.getEditor().getText());
                } catch (ParseException e) {
                    showAlert("Wrong input!", "Wrong timestamp!");
                    return;
                }
                Timestamp from = new Timestamp(parsedFrom.getTime());
                Timestamp to = new Timestamp(parsedTo.getTime());
                ArrayList<Flight> list = flightsHandler.getSortedByDestinationAndDates(text, from, to, isRelevant);
                ObservableList<Flight> flights = FXCollections.observableArrayList(list);
                TVFlightsTable.setItems(flights);
            }
            case 2 -> {      //specific airline
                if (text.isEmpty()) {
                    showAlert("Wrong input!", "Text is empty!");
                    return;
                }
                ArrayList<Flight> list = flightsHandler.getSortedByAirline(text, isRelevant);
                ObservableList<Flight> flights = FXCollections.observableArrayList(list);
                TVFlightsTable.setItems(flights);
            }
            case 3 -> {      //flights with first and business class
                ArrayList<Flight> list = flightsHandler.getSortedByClassesSeats(isRelevant);
                ObservableList<Flight> flights = FXCollections.observableArrayList(list);
                TVFlightsTable.setItems(flights);
            }
            case 4 -> {      //lower base price
                if (text.isEmpty()) {
                    showAlert("Wrong input!", "Text is empty!");
                    return;
                }
                double price;
                try {
                    price = Double.parseDouble(text);
                } catch (NumberFormatException e) {
                    showAlert("Wrong input!", "Wrong price!");
                    return;
                }
                ArrayList<Flight> list = flightsHandler.getSortedByPrice(price, isRelevant);
                ObservableList<Flight> flights = FXCollections.observableArrayList(list);
                TVFlightsTable.setItems(flights);
            }
        }
    }

    public void CBTicketsSortOnAction(ActionEvent event) {
        int index = CBTicketsSort.getSelectionModel().getSelectedIndex();
        LTicketsFlights.setVisible(false);
        LTicketsClasses.setVisible(false);
        LTicketsCashiers.setVisible(false);
        CBTicketsFlights.setVisible(false);
        CBTicketsClasses.setVisible(false);
        CBTicketsCashiers.setVisible(false);
        switch (index) {
            case 0 -> {      //none
            }
            case 1 -> {      //specific flight
                LTicketsFlights.setVisible(true);
                CBTicketsFlights.setVisible(true);
            }
            case 2 -> {      //specific cashier
                LTicketsCashiers.setVisible(true);
                CBTicketsCashiers.setVisible(true);
            }
            case 3 -> {      //specific flight and service class
                LTicketsFlights.setVisible(true);
                LTicketsClasses.setVisible(true);
                CBTicketsFlights.setVisible(true);
                CBTicketsClasses.setVisible(true);
            }
            default -> {
            }
        }
    }

    public void onTicketsSortClicked(ActionEvent event) {
        int sortIndex = CBTicketsSort.getSelectionModel().getSelectedIndex();
        boolean isRelevant = ChBTicketsIsRelevant.isSelected();

        switch (sortIndex) {
            case 0 -> {      //none
                ObservableList<Ticket> tickets = FXCollections.observableArrayList(ticketsHandler.getTickets(isRelevant));
                TVTicketsTable.setItems(tickets);
            }
            case 1 -> {      //specific flight
                Flight flight = CBTicketsFlights.getValue();
                if (flight == null) {
                    showAlert("Wrong input!", "Choose flight!");
                    return;
                }
                ArrayList<Ticket> list = ticketsHandler.getSortedByFlight(flight, isRelevant);
                ObservableList<Ticket> tickets = FXCollections.observableArrayList(list);
                TVTicketsTable.setItems(tickets);
            }
            case 2 -> {      //specific cashier
                User cashier = CBTicketsCashiers.getValue();
                if (cashier == null) {
                    showAlert("Wrong input!", "Choose cashier!");
                    return;
                }
                ArrayList<Ticket> list = ticketsHandler.getSortedByCashier(cashier, isRelevant);
                ObservableList<Ticket> tickets = FXCollections.observableArrayList(list);
                TVTicketsTable.setItems(tickets);
            }
            case 3 -> {      //specific flight and service class
                Flight flight = CBTicketsFlights.getValue();
                ServiceClass serviceClass = CBTicketsClasses.getValue();
                if (flight == null || serviceClass == null) {
                    showAlert("Wrong input!", "Choose values!");
                    return;
                }
                ArrayList<Ticket> list = ticketsHandler.getSortedByFlightAndServiceClass(flight, serviceClass, isRelevant);
                ObservableList<Ticket> tickets = FXCollections.observableArrayList(list);
                TVTicketsTable.setItems(tickets);
            }
        }
    }

    public void CBPlanesSortOnAction(ActionEvent event) {
        int index = CBPlanesSort.getSelectionModel().getSelectedIndex();
        switch (index) {
            case 0 -> {      //none
                LPlanesAirline.setVisible(false);
                CBPlanesAirlines.setVisible(false);
            }
            case 1 -> {      //specific airline
                LPlanesAirline.setVisible(true);
                CBPlanesAirlines.setVisible(true);
            }
            default -> {
            }
        }
    }

    public void onPlanesSortClicked(ActionEvent event) {
        int sortIndex = CBPlanesSort.getSelectionModel().getSelectedIndex();

        switch (sortIndex) {
            case 0 -> {      //none
                ObservableList<Plane> planes = FXCollections.observableArrayList(planesHandler.getPlanes());
                TVPlanesTable.setItems(planes);
            }
            case 1 -> {      //specific flight
                Airline airline = CBPlanesAirlines.getValue();
                if (airline == null) {
                    showAlert("Wrong input!", "Choose airline!");
                    return;
                }
                ArrayList<Plane> list = planesHandler.getSortedByAirlines(airline);
                ObservableList<Plane> planes = FXCollections.observableArrayList(list);
                TVPlanesTable.setItems(planes);
            }
        }
    }

    public void CBUsersSortOnAction(ActionEvent event) {
        int index = CBUsersSort.getSelectionModel().getSelectedIndex();
        switch (index) {
            case 0 -> {      //none
                LUsersRole.setVisible(false);
                CBUsersRoles.setVisible(false);
            }
            case 1 -> {      //specific roles
                LUsersRole.setVisible(true);
                CBUsersRoles.setVisible(true);
            }
            default -> {
            }
        }
    }

    public void onUsersSortClicked(ActionEvent event) {
        int sortIndex = CBUsersSort.getSelectionModel().getSelectedIndex();

        switch (sortIndex) {
            case 0 -> {      //none
                ObservableList<User> users = FXCollections.observableArrayList(usersHandler.getUsers());
                TVUsersTable.setItems(users);
            }
            case 1 -> {      //specific role
                Role role = CBUsersRoles.getValue();
                if (role == null) {
                    showAlert("Wrong input!", "Choose role!");
                    return;
                }
                ArrayList<User> list = usersHandler.getSortedByRole(role);
                ObservableList<User> users = FXCollections.observableArrayList(list);
                TVUsersTable.setItems(users);
            }
        }
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

        ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights(false));
        TVFlightsTable.setItems(flights);

        TCUserRole.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(rolesHandler.getRoles())));
        TCPlane.setCellFactory(param -> new ComboBoxTableCell<>(FXCollections.observableArrayList(planesHandler.getPlanes())));
    }

    public void setUser(User user) {
        currUser = user;
        currRole = Roles.valueOf(currUser.getRole().toUpperCase());
        closeTabs();
    }

    public void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void closeTabs() {
        switch (currRole) {
            case ADMIN -> {
                BAddFlights.setVisible(false);
                BAddTickets.setVisible(false);
                TVTicketsTable.setEditable(false);
                TVFlightsTable.setEditable(false);
            }
            case CASHIER -> {
                TPTabPane.getTabs().remove(TUsers);
                TPTabPane.getTabs().remove(TTariffs);
                TPTabPane.getTabs().remove(TRoles);
                TPTabPane.getTabs().remove(TClasses);
                TPTabPane.getTabs().remove(TPlanes);
                TPTabPane.getTabs().remove(TAirlines);
                BAddFlights.setVisible(false);
                TVFlightsTable.setEditable(false);
            }
            case DISPATCHER -> {
                TPTabPane.getTabs().remove(TUsers);
                TPTabPane.getTabs().remove(TTariffs);
                TPTabPane.getTabs().remove(TRoles);
                TPTabPane.getTabs().remove(TClasses);
                BAddPlanes.setVisible(false);
                TPTabPane.getTabs().remove(TAirlines);
                TPTabPane.getTabs().remove(TTickets);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String name = evt.getPropertyName();
        Tables value = Tables.valueOf(name);

        switch (value) {
            case AIRLINE -> {
                ObservableList<Airline> airlines = FXCollections.observableArrayList(airlinesHandler.getAirlines());
                TVAirlinesTable.setItems(airlines);
            }
            case ROLE, SERVICE_CLASS -> {

            }
            case USER -> {
                ObservableList<User> users = FXCollections.observableArrayList(usersHandler.getUsers());
                TVUsersTable.setItems(users);
            }
            case PLANE -> {
                ObservableList<Plane> planes = FXCollections.observableArrayList(planesHandler.getPlanes());
                TVPlanesTable.setItems(planes);
            }
            case FLIGHT -> {
                ObservableList<Flight> flights = FXCollections.observableArrayList(flightsHandler.getFlights(false));
                TVFlightsTable.setItems(flights);
                ChBFlightsIsRelevant.setSelected(false);
            }
            case TARIFF -> {
                ObservableList<Tariff> tariffs = FXCollections.observableArrayList(tariffsHandler.getTariffs());
                TVTariffsTable.setItems(tariffs);
            }
            case TICKET -> {
                ObservableList<Ticket> tickets = FXCollections.observableArrayList(ticketsHandler.getTickets(false));
                TVTicketsTable.setItems(tickets);
                ChBTicketsIsRelevant.setSelected(false);
            }
            default -> {
                //TODO ERROR
            }
        }
    }
}
