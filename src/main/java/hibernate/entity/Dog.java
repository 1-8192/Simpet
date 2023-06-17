package hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Dog extends Pet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Kind of dog.
     */
    private String breed;

    public Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    public Dog() {
        super();
    }

    /**
     * Getter method for the breed variable
     * @return the dog's breed.
     */
    public String getBreed() {
        return breed;
    }

    /**
     * Feed activity
     */
    public void feed() {
        // postcondition: The dog's mood is improved.
        System.out.println(name + " is eating dog food.");
        mood += 5;
    }

    /**
     * Activity specific to dog pets.
     * Improves pet mood and prints a message to screen.
     *
     */
    public void train() {
        // postcondition: The dog's mood is improved.
        System.out.println(name + " is learning a new trick.");
        mood += 10;
    }

    public String toString() {
        return "Name: " + name + ", Breed: " + breed + ", Age: " + age + ", Health: " + health + ", Mood: " + mood;
    }
}
