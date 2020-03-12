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
}