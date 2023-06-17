import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Dog class that extends the Pet Abstract class.
 */
public class DogTest {
    private Dog dog;

    @BeforeEach
    public void setUp() {
        dog = new Dog("Buddy", "Golden Retriever");
    }

    @Test
    public void testGetName() {
        assertEquals("Buddy", dog.getName());
    }

    @Test
    public void testFeed() {
        dog.feed();
        assertEquals(105, dog.getMood());
    }

    @Test
    public void testTrain() {
        dog.train();
        assertEquals(110, dog.getMood());
    }

    @Test
    public void testGetBreed() {
        assertEquals("Golden Retriever", dog.getBreed());
    }
}

