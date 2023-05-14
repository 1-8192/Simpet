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
    }

    public String getUserName() {
        return this.userName;
    }

    public ArrayList<Pet> getPets() {
        return this.pets;
    }

    public void setUserName(String newName) {
        this.userName = newName;
    }

    public void addPet(Pet newPet) {
        this.pets.add(newPet);
    }

    public void removePet(Pet pet) {
        this.pets.remove(pet);
    }
}
