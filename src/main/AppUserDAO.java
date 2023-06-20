package main;

import java.sql.*;

/**
 * DAO class for saving and retrieving User info from the Postgres DB.
 */
public class AppUserDAO {
    /**
     * connection string.
     */
    private static final String connectionUrl = SimpetConstants.connectionUrl
            + "user=" + SimpetConstants.databaseUsername + "&"
            + "password=" + SimpetConstants.databasePassword;

    /**
     * Checks whether a username is already saved to the DB.
     *
     * @param name the username.
     * @return whether the record exists.
     */
    public boolean checkIfUserExists (String name) {
        // Post-condition: a boolean is returned representing whether the user is already saved to the DB.

        String sql = "SELECT CASE WHEN EXISTS (SELECT * FROM appUser WHERE username = ?) THEN 'TRUE' ELSE 'FALSE' END";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement1 = connection.prepareStatement(sql);) {
            statement1.setString(1, name);
            ResultSet results = statement1.executeQuery();
            if (results.next()) {
                String result = results.getString(1);
                return result.equalsIgnoreCase("TRUE");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Save the current user to the DB.
     *
     * @param name the username.
     */
    public void saveUserInfo (String name) {
        // Post condition: The new user is saved to the DB.

        // The id is serial type, and auto generates, so we only need to pass in the username.
        String sql = "INSERT INTO appUser (username) values (?)";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement1 = connection.prepareStatement(sql);) {
            statement1.setString(1, name);
            statement1.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
