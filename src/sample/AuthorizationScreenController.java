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

import java.io.IOException;
import java.sql.*;

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
            Connection con = DriverManager.getConnection(url, login, password);
            UsersHandler usersHandler = new UsersHandler(con);
            String role = usersHandler.getRole(login);

            Stage stage = (Stage) TFLogin.getScene().getWindow();
            stage.close();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("UI/MainScreen.fxml"));
            Parent root = loader.load();

            MainScreenController contr = loader.getController();
            contr.setConnection(con);
            contr.setRole(role);

            stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Airport Service");
            stage.setScene(new Scene(root, 800, 600));
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
        }
    }
}
