/**
 * Abstract Pet class designed to be extended by actual pet types.
 */
abstract class Pet {
    protected String name;
    protected int age;
    protected int health;
    protected int mood;

    public Pet(String name) {
        this.name = name;
        this.age = 0;
        this.health = 100;
        this.mood = 100;
    }

    public String getName() {
        return name;
    }

    public void play() {
        System.out.println(name + " is playing!");
        mood += 10;
    }

    public abstract void feed();

    public abstract void train(String trick);

    public void sleep() {
        System.out.println(name + " is sleeping!");
        mood += 20;
    }

    public void getOlder() {
        age += 1;
        health -= 10;
        if (health <= 0) {
            System.out.println(name + " has died!");
            System.exit(0);
        }
    }

    public String toString() {
        return "Name: " + name + ", Age: " + age + ", Health: " + health + ", Mood: " + mood;
    }
}
