import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PetStatisticsTest {

    // Declaring variables we will set up in the setUp() method.
    private Dog dogOne;

    private Dog dogTwo;

    private Cat catOne;

    private Cat catTwo;

    private List<Pet> testPets;

    private PetStatistics<Pet> testPetStatistics;

    @BeforeEach
    public void setUp() {
        dogOne = new Dog("Buddy", "boxer");
        dogTwo = new Dog("Max", "corgi");
        catOne = new Cat("Whiskers");
        catTwo = new Cat("Smokey");
        catOne.setMood(500);
        dogTwo.setAge(12);
        catTwo.setAge(18);
        testPets = Arrays.asList(dogOne, dogTwo, catOne, catTwo);
        testPetStatistics = new PetStatistics<>(testPets);
    }

    /**
     * Tests the getAverageAge method.
     */
    @Test
    public void testGetAverageAge() {
        double averageAge = testPetStatistics.getAverageAge();
        assertEquals(7.5, averageAge);
    }

    /**
     * Tests the getMostCommonType method.
     */
    @Test
    public void testGetMostCommonType() {
        String mostCommonType = testPetStatistics.getMostCommonType();
        assertEquals("Cat", mostCommonType);
    }

    /**
     * Tests the getOldestPet method.
     */
    @Test
    public void testGetOldestPet() {
        // One pet case
        String oldestPet = testPetStatistics.getOldestPet();
        assertEquals(catTwo.getName(), oldestPet);

        // Case where multiple pets have the same age
        dogTwo.setAge(18);
        String oldestPetMulti = testPetStatistics.getOldestPet();
        String expected = "Max and Smokey";
        assertEquals(expected, oldestPetMulti);
    }

    /**
     * Tests the getHappiestPet method.
     */
    @Test
    public void testGetHappiestPet() {
        // One pet case
        String happiestPet = testPetStatistics.getHappiestPet();
        assertEquals(catOne.getName(), happiestPet);

        // Case where multiple pets have the same mood
        dogOne.setMood(500);
        String happiestPetMulti = testPetStatistics.getHappiestPet();
        String expected = "Buddy and Whiskers";
        assertEquals(expected, happiestPetMulti);
    }
}
