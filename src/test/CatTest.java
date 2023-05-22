package test;

import main.Cat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Cat class that extends the Pet abstract class.
 */
public class CatTest {
    private Cat cat;

    @BeforeEach
    public void setUp() {
        cat = new Cat("Mittens");
    }

    @Test
    public void testGetName() {
        assertEquals("Mittens", cat.getName());
    }

    @Test
    public void testFeed() {
        cat.feed();
        assertEquals(105, cat.getMood());
    }

    @Test
    public void testCleanLitterBox() {
        cat.cleanLitterBox();
        assertEquals(105, cat.getMood());
    }
}
