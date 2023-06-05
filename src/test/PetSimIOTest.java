package test;

import main.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertThrows(SimpetInputException.class, () -> {
            PetSimIO.loadPetsFromFile(user, textFileNameInvalid);
        });
    }

    /**
     * Testing pet load from a valid bin file.
     */
    @Test
    public void testLoadPetsFromFileWithValidFile() {
        // Assuming you have a valid binary file containing pet objects for testing

        Assertions.assertDoesNotThrow(() -> {
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

        Assertions.assertThrows(SimpetOutputException.class, () -> {
            PetSimIO.savePets(user, fileName);
        });
    }

    @Test
    public void testSavePetsWithValidFile() {
        Assertions.assertDoesNotThrow(() -> {
            PetSimIO.savePets(user, binFileNameValid);
        });

        // Assert some conditions based on the saved pets
    }

    @Test
    public void testSaveReportCardWithInvalidFileFormat() {
        User user = new User("Test User");
        String fileName = "report.doc";

        Assertions.assertThrows(SimpetOutputException.class, () -> {
            PetSimIO.saveReportCard(user, fileName);
        });
    }

    @Test
    public void testSaveReportCardWithValidFile() {
        User user = new User("Test User");
        String fileName = "reportTest.txt";

        Assertions.assertDoesNotThrow(() -> {
            PetSimIO.saveReportCard(user, fileName);
        });

        // Assert some conditions based on the saved report card
    }
}