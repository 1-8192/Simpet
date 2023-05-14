/**
 * The below is starter code generated by ChatGPT for the PetSim pet simulation. I have moved the classes over into
 * their own separate files for readability.
 * The prompt was: "Please code an implementation for a pet simulation system that allows a user to create a pet
 * and take care of it throughout its simulated lifetime. using Java that implements inheritance,
 * downcasting, and polymorphism. Make sure the Pet class is abstract."

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

class Dog extends Pet {
    public Dog(String name) {
        super(name);
    }

    public void feed() {
        System.out.println(name + " is eating dog food.");
        mood += 5;
    }

    public void train(String trick) {
        System.out.println(name + " is learning to " + trick + ".");
        mood += 10;
    }
}

class Cat extends Pet {
    public Cat(String name) {
        super(name);
    }

    public void feed() {
        System.out.println(name + " is eating cat food.");
        mood += 5;
    }

    public void train(String trick) {
        System.out.println(name + " is ignoring you.");
        mood -= 5;
    }
}

public class Main {
    public static void main(String[] args) {
        Pet fido = new Dog("Fido");
        System.out.println("You have created a new dog named " + fido.name + ".");
        fido.feed();
        fido.play();
        fido.train("roll over");
        fido.sleep();
        fido.getOlder();
        System.out.println(fido.toString());

        Pet mittens = new Cat("Mittens");
        System.out.println("You have created a new cat named " + mittens.name + ".");
        mittens.feed();
        mittens.play();
        mittens.train("jump through hoop");
        mittens.sleep();
        mittens.getOlder();
        System.out.println(mittens.toString());

        // Downcasting example
        if (fido instanceof Dog) {
            Dog dogFido = (Dog) fido;
            dogFido.train("shake");
        }

        // Polymorphism example
        Pet[] pets = {fido, mittens};
        for (Pet pet : pets) {
            pet.feed();
            pet.play();
            pet.train("fetch");
            pet.sleep();
        }
    }
}
*/