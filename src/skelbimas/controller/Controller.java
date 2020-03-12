package skelbimas.controller;

import skelbimas.model.User;
import skelbimas.model.UserDAO;
import skelbimas.utils.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Controller {

    // close program
    @FXML
    private Button exit;

    // register window
    @FXML
    private javafx.scene.control.Label regWarning;
    @FXML
    private javafx.scene.control.TextField regUsername;
    @FXML
    private TextField regEmail;
    @FXML
    private PasswordField regPassword;
    @FXML
    private PasswordField regConfirmPassword;
    @FXML
    private CheckBox regIsAdmin;


    // closes program when clicked on 'X' button
    public void closeWindow(ActionEvent event) {
        if (event.getSource() == exit) {
            System.exit(0);
        }
    }

    // Returns to login window
    public void backToLoginWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 750, 400));
        ((Node) (event.getSource())).getScene().getWindow().hide();
        stage.show();
    }

    // New user registration window
    public void openRegistrationWindow(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/Register.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    public void registerUser(ActionEvent event) {
        boolean isRegistered = true;

        regWarning.setText(""); // Makes label empty
        if (!Validation.isValidUsername(regUsername.getText())){
            regWarning.setText("Username is incorrect. From 5 to 20 symbols. \n" +
                    " (only letters and numbers available)");
            isRegistered = false;
        } else if (!Validation.isValidPassword(regPassword.getText())) {
            regWarning.setText("Incorrect password. From 6 to 16 symbols. \n" +
                    " available symbols [a-zA-Z0-9!@#$%^&*()_=*/.,?|");
            isRegistered = false;
        } else if (!regConfirmPassword.getText().equals(regPassword.getText())){
            regWarning.setText("Passwords doesn't matches.");
            isRegistered = false;
        } else if (!Validation.isValidEmail(regEmail.getText())){
            regWarning.setText("Email is not correct. \n" +
                    "Pattern- user@skelbimai.ru");
            isRegistered = false;
        }

        if (isRegistered){
            User user = new User(regUsername.getText(), regPassword.getText(), regEmail.getText(), regIsAdmin.isSelected());
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.register(user);
            if (msg.contains("Successfully added")){
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../view/login.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Login");
                    stage.setScene(new Scene(root, 750, 400));
                    stage.show();
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else{
                regWarning.setText(msg);
            }
        }
    }
}