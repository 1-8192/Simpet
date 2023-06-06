package test;

import main.*;
import org.junit.jupiter.api.Assertions;
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
     * Testing use case where invalid file format is used.
     */
    @Test
    public void testLoadPetsFromFileWithInvalidFileFormat() {
        assertThrows(SimpetInputException.class, () -> {
            PetSimIO.loadPetsFromFile(user, textFileNameInvalid);
        });
    }

    /**
     * Testing pet load from a valid bin file.
     */
    @Test
    public void testLoadPetsFromFileWithValidFile() {
        // Assuming you have a valid binary file containing pet objects for testing

        assertDoesNotThrow(() -> {
            PetSimIO.loadPetsFromFile(user, binFileNameValid);
        });

        // Assert some conditions based on the loaded pets
        // Assertions.assertEquals(expectedPetCount, user.getPets().size());
        // Assertions.assertEquals(expectedPetName, user.getPets().get(0).getName());
        // ...
    }

    @Test
    public void testSavePetsWithInvalidFileFormat() {
        User user = new User("Test User");
        String fileName = "pets.txt";

        assertThrows(SimpetOutputException.class, () -> {
            PetSimIO.savePets(user, fileName);
        });
    }

    @Test
    public void testSavePetsWithValidFile() {
        assertDoesNotThrow(() -> {
            PetSimIO.savePets(user, binFileNameValid);
        });

        // Assert some conditions based on the saved pets
    }

    @Test
    public void testSaveReportCardWithInvalidFileFormat() {
        User user = new User("Test User");
        String fileName = "report.doc";

        assertThrows(SimpetOutputException.class, () -> {
            PetSimIO.saveReportCard(user, fileName);
        });
    }

    @Test
    public void testSaveReportCardWithValidFile() {
        User user = new User("Test User");
        String fileName = "reportTest.txt";

        assertDoesNotThrow(() -> {
            PetSimIO.saveReportCard(user, fileName);
        });

        // Assert some conditions based on the saved report card
    }

    /**
     * Testing the method to save pet results to a report card txt file.
     */
    @Test
    public void testSaveReportCard() {
        // Initializing useful variables
        String goodFileName = "reportcard.txt";
        String badFileName = "text.gfd";
        User testUser = new User("Tom");

        // Testing the output exception.
        assertDoesNotThrow(()-> PetSimIO.saveReportCard(user, goodFileName));
        assertThrows(SimpetInputException.class, ()-> PetSimIO.saveReportCard(user, badFileName));
    }
}