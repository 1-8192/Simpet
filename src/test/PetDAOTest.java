package test;
import main.Dog;
import main.Pet;
import main.PetDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * test class for PetDAO model.
 */
public class PetDAOTest {
    private static final String TEST_USERNAME = "test";

    private static PetDAO petDAO;

    @BeforeAll
    public static void setup() {
        petDAO = new PetDAO();
    }

    @Test
    public void testCheckUserPetsInDatabaseNoPets() throws SQLException {
        int expectedCount = 0; // Assuming the test user has no pets in the database

        int result = petDAO.checkUserPetsInDatabase("nonexistinguser");

        Assertions.assertEquals(expectedCount, result);
    }

    @Test
    public void testSavePetInfo() throws SQLException {
        Pet pet = new Dog("Timmy", "Labrador Retriever");
        String petType = "dog";
        String petBreed = "Labrador Retriever";

        petDAO.savePetInfo(pet, TEST_USERNAME);

        ResultSet resultSet = petDAO.loadUserPetsFromDB(TEST_USERNAME);
        boolean petFound = false;

        while (resultSet.next()) {
            String petName = resultSet.getString("pet_name");
            String type = resultSet.getString("pet_type");
            String breed = resultSet.getString("breed");

            if (petName.equals(pet.getName()) && type.equals(petType) && breed.equals(petBreed)) {
                petFound = true;
                break;
            }
        }

        Assertions.assertTrue(petFound);
    }
}



