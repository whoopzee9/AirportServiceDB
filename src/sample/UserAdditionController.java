package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.handlers.RolesHandler;
import sample.handlers.UsersHandler;
import sample.tables.Plane;
import sample.tables.Role;
import sample.tables.User;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserAdditionController {
    public TextField TFName;
    public TextField TFPhone;
    public TextField TFAddress;
    public DatePicker DPBirthDate;
    public TextField TFEmail;
    public ComboBox<Role> CBRole;
    public PasswordField PFPassword;
    public TextField TFUsername;
    public PasswordField PFRepeatPassword;

    private Connection con;
    private RolesHandler rolesHandler;
    private UsersHandler usersHandler;
    private PropertyChangeSupport support = new PropertyChangeSupport(this);

    public UserAdditionController() {
    }

    @FXML
    public void initialize() {
        CBRole.setOnMouseClicked(event -> {
            ObservableList<Role> roles = FXCollections.observableArrayList(rolesHandler.getRoles());
            ObservableList<Role> oldRoles = CBRole.getItems();
            if (!roles.equals(oldRoles)) {
                CBRole.setItems(roles);
            }
        });
    }

    public void onDoneClicked(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Wrong input");

        String name = TFName.getText();
        String phone = TFPhone.getText();
        String address = TFAddress.getText();
        String dateStr = DPBirthDate.getEditor().getText();
        String email = TFEmail.getText();
        Role role = CBRole.getValue();
        String password = PFPassword.getText();
        String repeatPassword = PFRepeatPassword.getText();
        String username = TFUsername.getText();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || dateStr.isEmpty() || email.isEmpty()
            || role == null || password.isEmpty() || repeatPassword.isEmpty() || username.isEmpty()) {
            alert.setContentText("Missing input, please check all fields!");
            alert.showAndWait();
            return;
        }
        if (!password.equals(repeatPassword)) {
            alert.setContentText("Passwords are not the same!");
            alert.showAndWait();
            return;
        }

        String transformation = "AES/ECB/PKCS5Padding";
        byte[] encrypted;
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, Constants.key);
            encrypted = cipher.doFinal(password.getBytes());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            return;
        }
        String encPassword = DatatypeConverter.printHexBinary(encrypted);

        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        Date dateTmp;
        try {
            dateTmp = format.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            alert.setContentText("Wrong date! It should be dd.mm.yyyy");
            alert.showAndWait();
            return;
        }

        java.sql.Date birthDate = new java.sql.Date(dateTmp.getTime());

        User user = new User(name, birthDate, address, phone, email, role.getName(), username);

        try {
            usersHandler.addUser(user, encPassword);

            Stage stage = (Stage) TFAddress.getScene().getWindow();
            stage.close();

            support.firePropertyChange(MainScreenController.Tables.USER.name(),1,0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            alert.setContentText("Request failed!");
            alert.showAndWait();
        }
    }

    public void setConnection(Connection con) {
        this.con = con;
        rolesHandler = new RolesHandler(con);
        usersHandler = new UsersHandler(con);
    }

    public void addListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
