package test;

import main.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PetSimIOTest {

    // Declaring useful variables.
    private User user;

    private String textFileNameInvalid = "pets.txt";

    private String binFileNameValid = "petsTest.bin";

    private Dog dogOne;

    private Dog dogTwo;

    private Cat catOne;

    private Cat catTwo;

    /**
     * Setting up variables for the upcoming tests.
     */
    @BeforeEach
    public void setUp() {
        user = new User("Test User");
        dogOne = new Dog("Buddy", "boxer");
        dogTwo = new Dog("Max", "corgi");
        catOne = new Cat("Whiskers");
        catTwo = new Cat("Smokey");
        catOne.setMood(500);
        dogTwo.setAge(12);
        catTwo.setAge(18);
        user.addPet(dogOne);
        user.addPet(dogTwo);
        user.addPet(catOne);
        user.addPet(catTwo);
    }

    /**
     * Testing the method to save pet results to a report card txt file.
     */
    @Test
    public void testSaveReportCard() {
        // Initializing useful variables
        String goodFileName = "reportCardTest.txt";
        String badFileName = "text.gfd";
        User testUser = new User("Tom");

        // Testing the output exception.
        assertDoesNotThrow(()-> PetSimIO.saveReportCard(user, goodFileName));
        assertThrows(SimpetOutputException.class, ()-> PetSimIO.saveReportCard(user, badFileName));
    }
}