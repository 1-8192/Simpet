import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void testRemoveDeceasedPets() {
        Dog dogOne = new Dog("Buddy", "boxer");
        Dog dogTwo = new Dog("Max", "corgi");

        User testUser = new User("Tom");
        testUser.addPet(dogTwo);
        testUser.addPet(dogOne);

        Assertions.assertEquals(2, testUser.getPets().size());
        dogTwo.setHasPassed(true);
        testUser.removeDeceasedPets();
        Assertions.assertEquals(1, testUser.getPets().size());

    }
}
