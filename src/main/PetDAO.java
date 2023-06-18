package main;

import java.sql.*;

public class PetDAO {
    /**
     * connection string.
     */
    private static final String connectionUrl = "jdbc:postgresql://localhost/Simpet?"
            + "user=alessandroallegranzi&"
            + "password=Gwyn1/8192";

    /**
     * Checks if the user has saved pets.
     *
     * @param name the user's name.
     */
    public static Integer checkUserPetsInDatabase (String name) throws SQLException{
        // Post condition: The new pet is saved the DB.

        // Using an aggregate COUNT() function.
        String sql = "SELECT COUNT(pet_id) as count FROM Pet WHERE Pet.appuser_id = " +
                "(SELECT appuser_id FROM appuser WHERE username = ?)";
        Connection connection = DriverManager.getConnection(connectionUrl);
        PreparedStatement statement1 = connection.prepareStatement(sql);
        statement1.setString(1, name);
        ResultSet results = statement1.executeQuery();
        while (results.next()) {
            return results.getInt("count");
        }
        return 0;
    }

    /**
     * Load pets from the DB associated with the user.
     *
     * @param name the user's name.
     */
    public static ResultSet loadUserPetsFromDB (String name) throws SQLException{
        // Post condition: The new pet is saved the DB.

        // Using a select here to grab the user id so we don't have to load that in a separate query.
        String sql = "SELECT * FROM Pet WHERE Pet.appuser_id = (SELECT appuser_id FROM appuser WHERE username = ?)" +
                "AND Pet.has_passed = FALSE";
        Connection connection = DriverManager.getConnection(connectionUrl);
        PreparedStatement statement1 = connection.prepareStatement(sql);
        statement1.setString(1, name);
        return statement1.executeQuery();
    }

    /**
     * Save the pet info to the DB.
     *
     * @param pet  the pet we want to save.
     * @param name the user's name.
     */
    public static void savePetInfo (Pet pet, String name) {
        // Post condition: The new pet is saved the DB.

        // The id is serial type, and auto generates, so we do not pass in the ID value.
        String sql = "INSERT INTO Pet (pet_name, mood, health, has_passed, pet_type, breed, appuser_id)" +
                // Using a select here to grab the user id so we don't have to load that in a separate query.
                "values (?, ?, ?, ?, ?, ?, (SELECT appuser_id FROM appuser WHERE username = ?))";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement1 = connection.prepareStatement(sql);) {
            statement1.setString(1, pet.getName());
            statement1.setInt(2, pet.getMood());
            statement1.setInt(3, pet.getHealth());
            statement1.setBoolean(4, pet.getHasPassed());
            if (pet instanceof Dog) {
                statement1.setString(5, "dog");
                statement1.setString(6, ((Dog) pet).getBreed());
            } else {
                statement1.setString(5, "cat");
                statement1.setNull(6, 0);
            }
            statement1.setString(7, name);
            statement1.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
