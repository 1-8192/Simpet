package test;

import main.PetSimulation;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for main PetSimulation class.
 */
public class PetSimulationTest {

    /**
     * Testing initializing the user.
     */
    @Test
    public void testInitializeUser() {
        String userName = "John";
        ByteArrayInputStream in = new ByteArrayInputStream(userName.getBytes());
        System.setIn(in);

        PetSimulation.initializeUser();

        assertEquals(userName, PetSimulation.currentUser.getUserName());
    }
}
