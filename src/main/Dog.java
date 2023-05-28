package main;

/**
 * Class that represents a dog type pet.
 */
public class Dog extends Pet {
    private String breed;

    public Dog(String name, String breed) {
        super(name);
        this.breed = breed;
    }

    /**
     * Getter method for the breed variable
     * @return the dog's breed.
     */
    public String getBreed() {
        return breed;
    }

    public void feed() {
        // postcondition: The dog's mood is improved.
        System.out.println(name + " is eating dog food.");
        mood += 5;
    }

    /**
     * Activity specific to dog pets.
     * Improves pet mood and prints a message to screen.
     *
     * @param trick the type of trick the user wants to train
     */
    public void train(String trick) {
        // postcondition: The dog's mood is improved.
        System.out.println(name + " is learning to " + trick + ".");
        mood += 10;
    }

    public String toString() {
        return "Name: " + name + ", Breed: " + breed + ", Age: " + age + ", Health: " + health + ", Mood: " + mood;
    }
}