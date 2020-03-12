package skelbimas.model;

import skelbimas.utils.Constant;

import java.sql.*;

public class UserDAO {

    PreparedStatement preparedStatement = null;
    Connection connection = null;
    ResultSet resultSet = null;


    // New user Registration
    public String register(User user){
        String query = "INSERT INTO " + Constant.TABLE_NAME + " (username, password, email, admin) VALUES (?, ?, ?, ?)";
        String msg = "";
        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setBoolean(4, user.isAdmin());
            preparedStatement.executeUpdate();
            msg = "New user successfully added.";
        } catch (SQLException e) {
            e.printStackTrace();
            msg = "Failure adding new user.";
        }
        return msg;
    }

    // Login into
    public String login(String username, String password){
        String msg = "";
        String query = "SELECT * FROM " + Constant.TABLE_NAME + " WHERE username LIKE ? AND password LIKE ?";
        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                msg = "Successfully logged in";
            } else {
                msg = "No user found";
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return msg;
    }

    public User getUser(String username){
        boolean isAdmin = false;
        User user = null;
        String query = "SELECT * FROM " + Constant.TABLE_NAME + " WHERE username LIKE ?";
        try {
            connection = DriverManager.getConnection(Constant.URL + Constant.DB_NAME, "root", "");
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id1 = resultSet.getInt("id");
                String username1 = resultSet.getString("username");
                String password1 = resultSet.getString("password");
                String email1 = resultSet.getString("email");
                boolean admin1 = resultSet.getBoolean("admin");
                user = new User(id1, username1, password1, email1, admin1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }
}