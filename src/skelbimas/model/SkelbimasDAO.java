package skelbimas.model;

import java.sql.*;
import java.util.ArrayList;

public class SkelbimasDAO {

    final static String URL = "jdbc:mysql://localhost:3306/skelbimai";

    public String add(Skelbimas skelbimas){
        String query = "INSERT INTO skelbimas (pavadinimas, skelbimo_tipas, miestas, kaina, naujas_naudotas, kontaktai, aprasymas) VALUES (?,?,?,?,?,?,?)";

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, skelbimas.getPavadinimas());
            preparedStatement.setString(2, skelbimas.getSkelbimoTipas());
            preparedStatement.setString(3, skelbimas.getMiestas());
            preparedStatement.setInt(4, skelbimas.getKaina());
            preparedStatement.setString(5, skelbimas.getNaujasNaudotas());
            preparedStatement.setString(6, skelbimas.getKontaktai());
            preparedStatement.setString(7, skelbimas.getAprasymas());
            preparedStatement.executeUpdate();

            return "Successfully created";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure creating new ad";
        }
    }

    public ResultSet searchByPavadinimas(String pavadinimas, User user){
        String query = "";
        if (user.isAdmin()){
            if (pavadinimas.equals("")){
                query = "SELECT * FROM skelbimas";
            } else {
                query = "SELECT * FROM skelbimas WHERE pavadinimas LIKE '" + pavadinimas + "'";
            }
        } else {
            if (pavadinimas.equals("")){
                query = "SELECT * FROM skelbimas WHERE id = '" + user.getId() + "'";
            } else {
                query = "SELECT * FROM skelbimas WHERE id = '" + user.getId() + "' AND pavadinimas LIKE '" + pavadinimas + "'";
            }
        }
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(URL, "root", "");
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public String editById(Skelbimas skelbimas){
        String query = "UPDATE skelbimas SET pavadinimas = ?, skelbimo_tipas = ?, miestas = ?, kaina = ?," +
                " naujas_naudotas = ?, kontaktai = ?, aprasymas = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, skelbimas.getPavadinimas());
            preparedStatement.setString(2, skelbimas.getSkelbimoTipas());
            preparedStatement.setString(3, skelbimas.getMiestas());
            preparedStatement.setInt(4, skelbimas.getKaina());
            preparedStatement.setString(5, skelbimas.getNaujasNaudotas());
            preparedStatement.setString(6, skelbimas.getKontaktai());
            preparedStatement.setString(7, skelbimas.getAprasymas());
            preparedStatement.setInt(8, skelbimas.getId());
            preparedStatement.executeUpdate();

            return "Successfully Updated";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure Update";
        }
    }

    public String deleteById(int id){
        String query = "DELETE FROM skelbimas WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            return "Successfully Deleted";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure Delete";
        }
    }
}
