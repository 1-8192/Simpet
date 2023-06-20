package test;

import main.AppUserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test class for the User DAO model.
 */
public class AppUserDAOTest {
    // Make sure the DB has a test user saved with the username "test" for this test.
    private static final String TEST_USERNAME = "test";

    private static AppUserDAO appUserDAO;

    @BeforeAll
    public static void setup() {
        appUserDAO = new AppUserDAO();
    }

    @Test
    public void testCheckIfUserExists_ExistingUser_ReturnsTrue() {
        boolean result = appUserDAO.checkIfUserExists(TEST_USERNAME);
        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckIfUserExists_NonExistingUser_ReturnsFalse() {
        boolean result = appUserDAO.checkIfUserExists("nonexistinguser");
        Assertions.assertFalse(result);
    }

    @Test
    public void testSaveUserInfo_NewUser_UserSavedToDB() {
        String newUsername = "newuser";
        appUserDAO.saveUserInfo(newUsername);

        boolean result = appUserDAO.checkIfUserExists(newUsername);
        Assertions.assertTrue(result);
    }
}
