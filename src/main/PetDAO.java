package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PetDAO {
    /**
     * connection string.
     */
    private static final String connectionUrl = "jdbc:postgresql://localhost/Simpet?"
            + "user=alessandroallegranzi&"
            + "password=Gwyn1/8192";

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