package scripts;

import main.SimpetConstants;

import java.sql.*;

/**
 * Scripts to poopulate the tables needed for the Simpet simulation with seed data. Please replace the postgres
 * address and credentials with you own. If you have a copy of the DB already, no need to run this.
 */
public class PopulateSimpetTables {
    public static void main(String[] args) {
        String connectionUrl = SimpetConstants.connectionUrl
                + "user=" + SimpetConstants.databaseUsername + "&"
                + "password=" + SimpetConstants.databasePassword;

        String userSql = "INSERT INTO appUser (username) values (?)";
        String petSql = "INSERT INTO Pet (pet_name, mood, health, has_passed, pet_type, breed, appuser_id, age) "
         + "values (?, ?, ?, ?, ?, ?, (SELECT appuser_id FROM appuser WHERE username = ?), ?)";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement userStatement = connection.prepareStatement(userSql);
             PreparedStatement petStatement = connection.prepareStatement(petSql);) {
            // Inserting User data.
            userStatement.setString(1, "Billy");
            userStatement.execute();
            userStatement.setString(1, "Sharon");
            userStatement.execute();
            userStatement.setString(1, "test");
            userStatement.execute();

            // Inserting Pet Data.
            petStatement.setString(1, "Happy");
            petStatement.setInt(2, 100);
            petStatement.setInt(3, 100);
            petStatement.setBoolean(4, false);
            petStatement.setString(5, "cat");
            petStatement.setString(6, null);
            petStatement.setString(7, "Billy");
            petStatement.setInt(8, 0);

            petStatement.setString(1, "Happy");
            petStatement.setInt(2, 100);
            petStatement.setInt(3, 100);
            petStatement.setBoolean(4, false);
            petStatement.setString(5, "dog");
            petStatement.setString(6, "spaniel");
            petStatement.setString(7, "Billy");
            petStatement.setInt(8, 0);

            petStatement.setString(1, "Mopey");
            petStatement.setInt(2, 100);
            petStatement.setInt(3, 100);
            petStatement.setBoolean(4, false);
            petStatement.setString(5, "cat");
            petStatement.setString(6, null);
            petStatement.setString(7, "Sharon");
            petStatement.setInt(8, 0);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
