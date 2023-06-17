import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Removing deceased pets from user's pet list and setting the user's pets to live pets.
     */
    public void removeDeceasedPets() {
        List<Pet> petList = this.pets.stream()
                .filter(pet -> !pet.getHasPassed()).collect(Collectors.toList());
        this.pets = (ArrayList<Pet>) petList;
    }

    /**
     * Setter for pets list.
     *
     * @param pets the new pet list.
     */
    public void setPets(ArrayList<Pet> pets) {
        this.pets = pets;
    }
}
