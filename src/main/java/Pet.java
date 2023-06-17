import java.io.Serializable;

/**
 * Abstract main.Pet class designed to be extended by actual pet types.
 */
public abstract class Pet implements Serializable {
    /**
     * For object I/O.
     */
    private static final long serialVersionUID = 1111L;
    /**
     * main.Pet's name
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
     * Feeding simulation.
     */
    public abstract void feed();

    /**
     * age getter
     * @return the pet's age
     */
    public int getAge() {
        return age;
    }

    /**
     * hasPassed getter.
     *
     * @return whether the pet is deceased.
     */
    public boolean getHasPassed() {
        return this.hasPassed;
    }

    /**
     * health getter
     * @return the pet's health
     */
    public int getHealth() {
        return health;
    }

    /**
     * mood getter
     * @return the pet's mood
     */
    public int getMood() {
        return mood;
    }

    /**
     * name getter
     * @return the pet's name
     */
    public String getName() {
        return name;
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

    /**
     * Play time simulation. The pet's mood is improved, and an informational message
     * prints to screen.
     */
    public void play() {
        System.out.println(name + " is playing!");
        health += 1;
        mood += 10;
    }

    /**
     * Setter method for the age variable. Used in unit testing.
     *
     * @return void
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Setter method for hasPassed.
     *
     * @return void
     */
    public void setHasPassed(boolean newHasPassed) {
        this.hasPassed = newHasPassed;
    }

    /**
     * Setter method for the health variable.
     *
     * @return void
     */
    public void setHealth(Integer health) {
        this.health = health;
    }

    /**
     * Setter method for the mood variable. Used in unit testing.
     *
     * @return void
     */
    public void setMood(Integer mood) {
        this.mood = mood;
    }

    /**
     * Sleep simulation. Mood improves and a message prints to screen.
     */
    public void sleep() {
        System.out.println(name + " is sleeping!");
        health += 1;
        mood += 20;
    }

    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Health: " + health + ", Mood: " + mood;
    }
}
