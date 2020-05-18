package skelbimas.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import skelbimas.model.Skelbimas;
import skelbimas.model.SkelbimasDAO;
import skelbimas.model.User;
import skelbimas.model.UserDAO;
import skelbimas.utils.Constant;
import skelbimas.utils.Validation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.sql.*;

public class Controller {

    // close program
    @FXML
    private Button exit;

    // Register stage
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

    // Login Stage
    @FXML
    private Label loginWarning;
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;

    // Main Stage
    @FXML
    private Label role;
    @FXML
    private Label loggedAs;

    // CRUD in main stage
    @FXML
    private Button create;
    @FXML
    private Button search;
    @FXML
    private Button update;
    @FXML
    private Button delete;

    // add fields
    @FXML
    private TextField pavadinimas;
    @FXML
    private RadioButton transportas;
    @FXML
    private RadioButton darbas;
    @FXML
    private RadioButton nekilnojamasTurtas;
    @FXML
    private RadioButton drabuziai;
    @FXML
    private RadioButton technika;
    @FXML
    private RadioButton kita;
    @FXML
    private ComboBox miestas;
    @FXML
    private TextField kaina;
    @FXML
    private CheckBox naujas;
    @FXML
    private CheckBox naudotas;
    @FXML
    private TextField kontaktai;
    @FXML
    private TextArea aprasymas;
    @FXML
    private Label warning;
    @FXML
    private TextField id;
    // table
    @FXML
    private TableView table;

    ResultSet rsAllEntries;
    ObservableList<ObservableList> data = FXCollections.observableArrayList();

    User user2;


    // closes login or register windows when clicked on 'X' button
    public void closeWindow(ActionEvent event) {
        if (event.getSource() == exit) {
            System.exit(0);
        }
    }

    // Returns to login window from registration window
    public void backToLoginWindow(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../view/Login.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, 750, 400));
        ((Node) (event.getSource())).getScene().getWindow().hide();
        stage.show();
    }

    // Open new user registration window
    public void openRegistrationWindow(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("../view/Register.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Register");
        stage.setScene(new Scene(root, 700, 400));
        stage.show();
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    // Checks new registration validation and creating new user
    public void registerUser(ActionEvent event) {
        boolean isRegistered = true;

        regWarning.setText(""); // Makes label empty
        if (!Validation.isValidUsername(regUsername.getText())) {
            regWarning.setText("Username is incorrect. From 5 to 20 symbols. \n" +
                    " (only letters and numbers available)");
            isRegistered = false;
        } else if (!Validation.isValidPassword(regPassword.getText())) {
            regWarning.setText("Incorrect password. From 6 to 16 symbols. \n" +
                    " available symbols [a-zA-Z0-9!@#$%^&*()_=*/.,?|");
            isRegistered = false;
        } else if (!regConfirmPassword.getText().equals(regPassword.getText())) {
            regWarning.setText("Passwords doesn't matches.");
            isRegistered = false;
        } else if (!Validation.isValidEmail(regEmail.getText())) {
            regWarning.setText("Email is not correct. \n" +
                    "Pattern- user@skelbimai.ru");
            isRegistered = false;
        }

        if (isRegistered) {
            User user = new User(regUsername.getText(), regPassword.getText(), regEmail.getText(), regIsAdmin.isSelected());
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.register(user);
            if (msg.contains("Successfully added")) {
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
            } else {
                regWarning.setText(msg);
            }
        }
    }

    // Login into Adds
    public void login(ActionEvent event) {
        if (Validation.isValidUsername(loginUsername.getText()) && Validation.isValidPassword(loginPassword.getText())) {
            UserDAO userDAO = new UserDAO();
            String msg = userDAO.login(loginUsername.getText(), loginPassword.getText());
            if (msg.contains("Successfully")) {
                User user = userDAO.getUser(loginUsername.getText());
                mainStage(event, user);
            } else {
                loginWarning.setText(msg);
            }
        } else {
            loginWarning.setText("Wrong username or password.");
        }
    }

    public void mainStage(ActionEvent event, User user) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../view/Skelbimas.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Skelbimai");
            stage.setScene(new Scene(root, 1300, 900));

            user2 = user;
            Label labelLoggedAs = (Label) root.lookup("#loggedAs");
            Label labelRole = (Label) root.lookup("#role");
            if (labelLoggedAs != null) labelLoggedAs.setText(user.getUsername());
            if (labelRole != null) labelRole.setText(user.isAdmin() ? "Admin" : "User");

            stage.show();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        String pavadinimas2 = pavadinimas.getText();
        String kaina2 = kaina.getText();
        String kontaktai2 = kontaktai.getText();
        String aprasymas2 = aprasymas.getText();

        String naujasNaudotas = "";
        if (naujas.isSelected()) {
            naujasNaudotas += naujas.getText();
        }
        if (naudotas.isSelected()) {
            naujasNaudotas += naudotas.getText();
        }
        if (naujasNaudotas.equals("")) {
            warning.setText("Pasirinkite ar tai naujas, ar naudotas produktas.");
        }

        String tipas = "kita";
        if (transportas.isSelected()){
            tipas = transportas.getText();
        }
        if (darbas.isSelected()){
            tipas = darbas.getText();
        }
        if (nekilnojamasTurtas.isSelected()){
            tipas = nekilnojamasTurtas.getText();
        }
        if (drabuziai.isSelected()){
            tipas = drabuziai.getText();
        }
        if (technika.isSelected()){
            tipas = technika.getText();
        }

        String miestas2 = "";
        if (!miestas.getSelectionModel().isEmpty()){
            miestas2 = miestas.getSelectionModel().getSelectedItem().toString();
        } else {
            warning.setText("Pasirinkite miestą.");
        }

        if (!Validation.isValidUsername(pavadinimas2)){
            warning.setText("Reikalingas skelbimo pavadinimas.");
        } else if (kaina2.isEmpty()){
            warning.setText("Įrašykite kainą.");
        } else if (kontaktai2.isEmpty()){
            warning.setText("Įrašykite kontaktinius duomenis.");
        }

        else {
            Skelbimas skelbimas = new Skelbimas(pavadinimas2, tipas, miestas2, Integer.parseInt(kaina2), naujasNaudotas, kontaktai2,
                    aprasymas2);
            SkelbimasDAO skelbimasDAO = new SkelbimasDAO();
            String msg = skelbimasDAO.add(skelbimas);
            warning.setText(msg);
        }
    }

    public void update(){
        String updateId = id.getText();
        String updatePavadinimas = pavadinimas.getText();
        String updateKaina = kaina.getText();
        String updateKontaktai = kontaktai.getText();
        String updateAprasymas = aprasymas.getText();

        String updateNaujasNaudotas = "Kita";
        if (naujas.isSelected()){
            updateNaujasNaudotas = naujas.getText();
        }
        if (naudotas.isSelected()){
            updateNaujasNaudotas = naudotas.getText();
        }

        String updateTipas = "";
        if (transportas.isSelected()){
            updateTipas = transportas.getText();
        } else if (darbas.isSelected()){
            updateTipas = darbas.getText();
        } else if (nekilnojamasTurtas.isSelected()){
            updateTipas = nekilnojamasTurtas.getText();
        } else if (drabuziai.isSelected()){
            updateTipas = drabuziai.getText();
        } else if (technika.isSelected()){
            updateTipas = technika.getText();
        } else if (kita.isSelected()){
            updateTipas = kita.getText();
        }

        String updateMiestas = "";
        if (!miestas.getSelectionModel().isEmpty()){
            updateMiestas = miestas.getSelectionModel().getSelectedItem().toString();
        } else {
            warning.setText("Pasirinkite miestą.");
        }

        if (!Validation.isValidUsername(updatePavadinimas)){
            warning.setText("Įveskite skelbimo pavadinimą.");
        }
        if (kaina.equals("0") || kaina.equals("")){
            warning.setText("Įveskite kainą.");
        } else {
            Skelbimas skelbimas = new Skelbimas(updateId, updateTipas, updateMiestas,
                    Integer.parseInt(updateKaina), updateNaujasNaudotas, updateKontaktai, updateAprasymas);
            SkelbimasDAO skelbimasDAO = new SkelbimasDAO();
            String msg = skelbimasDAO.editById(skelbimas);
            warning.setText(msg);

        }
    }

    public void delete(){
    String delteId = id.getText();
    SkelbimasDAO skelbimasDAO = new SkelbimasDAO();
    String msg = skelbimasDAO.deleteById(Integer.parseInt(delteId));
    warning.setText(msg);
    }

    public void search() throws SQLException {
        updateTableFromDb(pavadinimas.getText());
    }

    public void updateTableFromDb(String pavadinimas) throws SQLException {
        SkelbimasDAO skelbimasDAO = new SkelbimasDAO();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUser(loggedAs.getText());
        try {
            rsAllEntries = skelbimasDAO.searchByPavadinimas(pavadinimas, user);
        } catch (NullPointerException e){
            warning.setText("Nieko nerasta");
        }
        fetColumsList();
        fetRowList();
    }

    private void fetRowList() {
        try {
            data.clear();
            if (rsAllEntries != null){
                while (rsAllEntries.next()){
                    ObservableList row = FXCollections.observableArrayList();
                    for (int i = 1; i <=rsAllEntries.getMetaData().getColumnCount(); i++){
                        row.add(rsAllEntries.getString(i));
                    }
                    data.add(row);
                }
                table.setItems(data);
            } else {
                warning.setText("Nėra ką rodyti.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            warning.setText("Nieko nerasta.");
        }
    }

    private void fetColumsList() throws SQLException {
        try {
            table.getColumns().clear();

            if (rsAllEntries != null){
                for (int i = 0; i < rsAllEntries.getMetaData().getColumnCount(); i++) {
                    final int j = i;
                    TableColumn col = new TableColumn(rsAllEntries.getMetaData().getColumnName(i + 1).toUpperCase());
                    col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                        public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                            return new SimpleStringProperty(param.getValue().get(j).toString());
                        }
                    });
                    table.getColumns().remove(col);
                    table.getColumns().addAll(col);
                }
            } else {
                warning.setText("Nėra ką rodyti.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            warning.setText("Klaida įkeliant lentelę.");
        }
    }
}