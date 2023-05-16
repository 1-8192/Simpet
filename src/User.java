import java.util.ArrayList;

/**
 * Class Representing a user of the SIMPET pet simulation.
 */
public class User {
    /**
     * UserName for the account
     */
    private String userName;

    /**
     * Pets currently owned by the user.
     */
    private ArrayList<Pet> pets;

    public User(String userName) {
        this.userName = userName;
        this.pets = new ArrayList<>();
    }

    /**
     * Username getter
     * @return the userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * pets Array getter
     * @return arraylist of pets owned by the user.
     */
    public ArrayList<Pet> getPets() {
        return this.pets;
    }

    /**
     * Adds a new pet to the arraylist of pets for the user.
     *
     * @param newPet the new pet to add.
     */
    public void addPet(Pet newPet) {
        this.pets.add(newPet);
    }
}
