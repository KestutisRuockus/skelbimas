package skelbimas.model;

import java.sql.*;
import java.util.ArrayList;

public class SkelbimasDAO {

    final static String URL = "jdbc:mysql://localhost:3306/skelbimai";

    public String add(Skelbimas skelbimas){
        String query = "INSERT INTO skelbimas (pavadinimas, skelbimo_tipas, miestas, kaina, naujas_naudotas," +
                " kontaktai, aprasymas) VALUES (?, ?, ?, ?, ?, ?, ?, ?";

        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, skelbimas.getPavadinimas());
            preparedStatement.setString(2, skelbimas.getSkelbimoTipas());
            preparedStatement.setString(3, skelbimas.getMiestas());
            preparedStatement.setDouble(4, skelbimas.getKaina());
            preparedStatement.setString(5, skelbimas.getNaujasNaudotas());
            preparedStatement.setString(6, skelbimas.getKontaktai());
            preparedStatement.setString(7, skelbimas.getAprasymas());
            preparedStatement.executeUpdate();

            return "Successfully created";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Failure creating new add";
        }
    }

    public String searchByPavadinimas(String pavadinimas){
        String query = "SELECR * FROM skelbimas WHERE pavadinimas LIKE '"+pavadinimas+"'";
        ArrayList<Skelbimas> skelbimas = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);
            while (resultSet.next()){
                int id2 = resultSet.getInt("id");
                String pavadinimas2 = resultSet.getString("pavadinimas");
                String skelbimoTipas2 = resultSet.getString("skelbimo_tipas");
                String miestas2 = resultSet.getString("miestas");
                Double kaina2 = resultSet.getDouble("kaina");
                String naujasNaudotas2 = resultSet.getString("naujas_naudotas");
                String kontaktai2 = resultSet.getString("kontaktai");
                String aprasymas2 = resultSet.getString("aprasymas");
                skelbimas.add(new Skelbimas(id2, pavadinimas2, skelbimoTipas2, miestas2, kaina2, naujasNaudotas2,
                        kontaktai2,aprasymas2));
            }
            preparedStatement.close();
            connection.close();
            return "" + skelbimas;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Search Failure";
        }
    }

    public String editById(Skelbimas skelbimas){
        String query = "UPDATE skelbimas set pavadinimas = ?, skelbimo_tipas = ?, miestas = ?, kaina = ?," +
                " naujas_naudotas = ?, kontaktai = ?, aprasymas = ? WHERE id = ?";
        try {
            Connection connection = DriverManager.getConnection(URL, "root", "");
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, skelbimas.getPavadinimas());
            preparedStatement.setString(2, skelbimas.getSkelbimoTipas());
            preparedStatement.setString(3, skelbimas.getMiestas());
            preparedStatement.setDouble(4, skelbimas.getKaina());
            preparedStatement.setString(5, skelbimas.getNaujasNaudotas());
            preparedStatement.setString(6, skelbimas.getKontaktai());
            preparedStatement.setString(7, skelbimas.getAprasymas());
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
