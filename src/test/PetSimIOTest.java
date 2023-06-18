package test;

import main.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
//    @Test
//    public void testLoadPetsFromFileWithInvalidFileFormat() {
//        assertThrows(SimpetInputException.class, () -> {
//            PetSimIO.loadPetsFromFile(user, textFileNameInvalid);
//        });
//    }

    /**
     * Testing pet load from a valid bin file.
     */
//    @Test
//    public void testLoadPetsFromFileWithValidFile() {
//        // Assuming you have a valid binary file containing pet objects for testing
//        ArrayList<Pet> emptySet = new ArrayList<>();
//        user.setPets(emptySet);
//        assertDoesNotThrow(() -> {
//            PetSimIO.loadPetsFromFile(user, binFileNameValid);
//        });
//
//        Assertions.assertEquals(4, user.getPets().size());
//    }

    /**
     * Testing an exception is thrown with a bad file type.
     */
    @Test
    public void testSavePetsWithInvalidFileFormat() {
        User user = new User("Test User");
        String fileName = "pets.txt";

        assertThrows(SimpetOutputException.class, () -> {
            PetSimIO.savePets(user, fileName);
        });
    }

    /**
     * Testing that objects are written correctly to bin file.
     */
    @Test
    public void testSavePetsWithValidFile() {
        assertDoesNotThrow(() -> {
            PetSimIO.savePets(user, binFileNameValid);
        });
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
        assertThrows(SimpetInputException.class, ()-> PetSimIO.saveReportCard(user, badFileName));
    }
}