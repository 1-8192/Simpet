/**
 * Abstract Pet class designed to be extended by actual pet types.
 */
abstract class Pet {
    /**
     * Pet's name
     */
    protected String name;

    /**
     * The pet's age to track lifetime.
     */
    protected int age;

    /**
     * The pet's health, which also affects lifespan.
     */
    protected int health;

    /**
     * The pet's mood.
     */
    protected int mood;

    /**
     * Whether the pet has lived a full life.
     */
    protected boolean hasPassed = false;

    public Pet(String name) {
        this.name = name;
        this.age = 0;
        this.health = 100;
        this.mood = 100;
    }

    /**
     * name getter
     * @return the pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * mood getter
     * @return the pet's mood
     */
    public int getMood() {
        return mood;
    }

    /**
     * Play time simulation. The pet's mood is improved, and an informational message
     * prints to screen.
     */
    public void play() {
        System.out.println(name + " is playing!");
        mood += 10;
    }

    /**
     * Feeding simulation.
     */
    public abstract void feed();

    /**
     * Sleep simulation. Mood improves and a message prints to screen.
     */
    public void sleep() {
        System.out.println(name + " is sleeping!");
        mood += 20;
    }

    /**
     * Aging the pet. If health reaches 0 the pet sadly passes away.
     */
    public void getOlder() {
        age += 1;
        health -= 10;
        if (health <= 0) {
            hasPassed = true;
            System.out.println(name + " has lived a full, meaningful life and has moved on to greener pastures.");
        }
    }

    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Health: " + health + ", Mood: " + mood;
    }
}
