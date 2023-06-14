package test;

import main.Dog;
import main.Pet;
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

    /**
     * Testing helper method to verify selecetd activity.
     */
    @Test
    void testIsValidActivityTrue() {
        assertTrue(PetSimulation.isValidActivity("feed"));
        assertTrue(PetSimulation.isValidActivity("play"));
        assertTrue(PetSimulation.isValidActivity("train"));
        assertTrue(PetSimulation.isValidActivity("sleep"));
        assertTrue(PetSimulation.isValidActivity("health checkup"));
        assertTrue(PetSimulation.isValidActivity("exit"));
        assertFalse(PetSimulation.isValidActivity("feeding"));
        assertFalse(PetSimulation.isValidActivity("playing"));
        assertFalse(PetSimulation.isValidActivity("training"));
        assertFalse(PetSimulation.isValidActivity("rest"));
        assertFalse(PetSimulation.isValidActivity("checkup"));
        assertFalse(PetSimulation.isValidActivity("quit"));
    }

    /**
     * Testing a random activity to ensure the activity method works.
     */
    @Test
    void performActivityWithPet_Feed_ActivityPerformed() {
        Pet pet = new Dog("Rover", "mixed");
        String activity = "feed";
        PetSimulation.performActivityWithPet(pet, activity);
        assertEquals(105, pet.getMood());
    }
}