package main;

import java.io.Serializable;

/**
 * Class that represents a dog type pet.
 */
public class Dog extends Pet implements Serializable {
    /**
     * For object I/O.
     */
    private static final long serialVersionUID = 1113L;

    /**
     * Kind of dog.
     */
    private String breed;

    public Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    public Dog(String name, String breed, Integer mood, Integer health, Boolean hasPassed) {
        super(name);
        this.breed = breed;
        this.mood = mood;
        this. health = health;
        this.hasPassed = hasPassed;
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