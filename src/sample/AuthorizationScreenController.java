package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.handlers.UsersHandler;
import sample.tables.User;

import javax.crypto.*;
import javax.xml.bind.DatatypeConverter;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

public class AuthorizationScreenController {

    public TextField TFLogin;
    public PasswordField PFPassword;

    public AuthorizationScreenController() {

    }

    @FXML
    void onLoginClicked() {
        String login = TFLogin.getText();
        String password = PFPassword.getText();

        try {
            String url = "jdbc:sqlserver://localhost;"
                    + "database=kurs;"
                    + "loginTimeout=30;";
            String transformation = "AES/ECB/PKCS5Padding";
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, Constants.key);
            byte[] encrypted = cipher.doFinal(password.getBytes());
            String encPassword = DatatypeConverter.printHexBinary(encrypted);

            Connection con = DriverManager.getConnection(url, login, encPassword);
            UsersHandler usersHandler = new UsersHandler(con);
            User user = usersHandler.getUser(login);

            Stage stage = (Stage) TFLogin.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/MainScreen.fxml"));
            Parent root = loader.load();

            MainScreenController contr = loader.getController();
            contr.setConnection(con);
            contr.setUser(user);

            stage = new Stage();
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Airport Service");
            stage.setScene(new Scene(root, 850, 600));
            stage.show();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Wrong input");
            alert.setContentText("Username or password is incorrect!");
            alert.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    /*public static Optional<String> encryptPassword(String password) {


    }*/
}
