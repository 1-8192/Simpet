package test;

import main.PetSimIO;
import main.SimpetInputException;
import main.SimpetOutputException;
import main.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PetSimIOTest {

    @Test
    public void testLoadPetsFromFileWithInvalidFileFormat() {
        User user = new User("Test User");
        String fileName = "pets.txt";

        Assertions.assertThrows(SimpetInputException.class, () -> {
            PetSimIO.loadPetsFromFile(user, fileName);
        });
    }

    @Test
    public void testLoadPetsFromFileWithValidFile() {
        User user = new User("Test User");
        String fileName = "pets.bin";

        // Assuming you have a valid binary file containing pet objects for testing

        Assertions.assertDoesNotThrow(() -> {
            PetSimIO.loadPetsFromFile(user, fileName);
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
        User user = new User("Test User");
        String fileName = "pets.bin";

        Assertions.assertDoesNotThrow(() -> {
            PetSimIO.savePets(user, fileName);
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
        String fileName = "report.txt";

        Assertions.assertDoesNotThrow(() -> {
            PetSimIO.saveReportCard(user, fileName);
        });

        // Assert some conditions based on the saved report card
    }
}