package test;

import main.Cat;
import main.Dog;
import main.HealthCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Test class for the HealthCheck class.
 */
public class HealthCheckTest {

    Dog testDog;
    Cat testCat;

    @BeforeEach
    public void setUp() {
        testDog = new Dog("Buddy", "boxer");
        testCat = new Cat("Whiskers");
    }

    /**
     * Test to check that output for a young, healthy pet matches expectations.
     */
    @Test
    public void testPerformCheckupYoung() {
        // Testing on a dog
        HealthCheck<Dog> healthCheckDog = new HealthCheck<>();

        healthCheckDog.performCheckup(testDog);

        assertEquals("Your dog Buddy is in the prime of his life. ", healthCheckDog.getHealthMessage());
        assertEquals("Your dog Buddy seems very happy and fulfilled.", healthCheckDog.getMoodMessage());

        //Testing on a cat
        HealthCheck<Cat> healthCheckCat = new HealthCheck<>();

        healthCheckCat.performCheckup(testCat);

        assertEquals("Your cat Whiskers is in the prime of their life. ", healthCheckCat.getHealthMessage());
        assertEquals("Your cat Whiskers seems very happy and fulfilled.", healthCheckCat.getMoodMessage());
    }

    /**
     * Test to check that output for an older pet with a lower mood matches expectations.
     */
    @Test
    public void testPerformCheckupOlderLowerMood() {
        // Testing on a dog
        HealthCheck<Dog> healthCheckDog = new HealthCheck<>();

        testDog.setAge(10);
        testDog.setMood(40);

        healthCheckDog.performCheckup(testDog);


        assertEquals("Your dog Buddy is generally healthy, but is getting older. ",
                healthCheckDog.getHealthMessage());
        assertEquals("Your dog needs more stimulation. Consider longer play times.",
                healthCheckDog.getMoodMessage());

        // Testing on a cat
        HealthCheck<Cat> healthCheckCat = new HealthCheck<>();

        testCat.setAge(15);
        testCat.setMood(20);

        healthCheckCat.performCheckup(testCat);


        assertEquals("Your cat Whiskers is generally healthy, but is getting older. ",
                healthCheckCat.getHealthMessage());
        assertEquals("Your cat Whiskers needs more stimulation. Consider buying some toys.",
                healthCheckCat.getMoodMessage());
    }
}

