package main;

import java.sql.*;
import java.util.List;

public class PetDAO {
    /**
     * connection string.
     */
    private static final String connectionUrl = SimpetConstants.connectionUrl
            + "user=" + SimpetConstants.databaseUsername + "&"
            + "password=" + SimpetConstants.databasePassword;

    /**
     * Checks if the user has saved pets.
     *
     * @param name the user's name.
     */
    public static Integer checkUserPetsInDatabase (String name) throws SQLException{
        // Post condition: The number of pets the user has is returned.

        // Using an aggregate COUNT() function.
        String sql = "SELECT COUNT(pet_id) as count FROM Pet WHERE Pet.appuser_id = " +
                "(SELECT appuser_id FROM appuser WHERE username = ?)";
        Connection connection = DriverManager.getConnection(connectionUrl);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        ResultSet results = statement.executeQuery();
        while (results.next()) {
            return results.getInt("count");
        }
        return 0;
    }

    /**
     * Loads users and how many pets they have from the DB.
     */
    public static void getMostActiveUsers () {
        // Post condition: Most active user info is printed to scree.

        ResultSet results = null;
        // Using an aggregate COUNT() function, GROUP BY, ORDER BY, and selecting from multiple tables.
        String sql = "SELECT username, COUNT(pet_id) FROM appuser a JOIN Pet p ON a.appuser_id = p.appuser_id " +
                "GROUP BY username ORDER BY COUNT(pet_id) DESC LIMIT 5";
        try(Connection connection = DriverManager.getConnection(connectionUrl);
        PreparedStatement statement = connection.prepareStatement(sql);) {
            results = statement.executeQuery();
            while (results.next()) {
                System.out.println(results.getString(1) + ": has " + results.getInt(2) +
                        " pets.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates deceased pet info in the DB.
     *
     * @param pet the pet who is deceased.
     */
    public static void goodbyePet(Pet pet) {
        // precondition: pass in a pet instance that has passed on.
        // postcondition: we update the has_passed field in the database.

        String sql = "UPDATE Pet SET has_passed = ? WHERE Pet.pet_name = ?";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setBoolean(1, true);
            statement.setString(2, pet.getName());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                "AND Pet.has_passed = FALSE ORDER BY pet_type";
        Connection connection = DriverManager.getConnection(connectionUrl);
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        return statement.executeQuery();
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
        String sql = "INSERT INTO Pet (pet_name, mood, health, has_passed, pet_type, breed, appuser_id, age)" +
                // Using a select here to grab the user id so we don't have to load that in a separate query.
                "values (?, ?, ?, ?, ?, ?, (SELECT appuser_id FROM appuser WHERE username = ?), ?)";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, pet.getName());
            statement.setInt(2, pet.getMood());
            statement.setInt(3, pet.getHealth());
            statement.setBoolean(4, pet.getHasPassed());
            if (pet instanceof Dog) {
                statement.setString(5, "dog");
                statement.setString(6, ((Dog) pet).getBreed());
            } else {
                statement.setString(5, "cat");
                statement.setNull(6, 0);
            }
            statement.setString(7, name);
            statement.setInt(8, pet.getAge());
            statement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates pet info for the user after activity cycle.
     *
     * @param pets the list of pets.
     */
    public static void updateUserPetInfo(List<Pet> pets) {
        // precondition: User's list of pets is passed as parameter.
        // postcondition: DB is updated with new pet info.

        String sql = "UPDATE Pet SET age = ?, mood = ?, health = ?, has_passed = ? WHERE Pet.pet_name = ?";
        try (Connection connection = DriverManager.getConnection(connectionUrl);
             PreparedStatement statement = connection.prepareStatement(sql);) {

            for (Pet pet : pets) {
                statement.setInt(1, pet.getAge());
                statement.setInt(2, pet.getMood());
                statement.setInt(3, pet.getHealth());
                statement.setBoolean(4, pet.getHasPassed());
                statement.setString(5, pet.getName());
                statement.execute();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
