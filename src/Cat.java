/**
 * Class to represent a cat type pet.
 */
class Cat extends Pet {
    public Cat(String name) {
        super(name);
    }

    public void feed() {
        System.out.println(name + " is eating cat food.");
        mood += 5;
    }

    /**
     * Activity specific to the cat pet type.
     * Improves mood and prints message to screen.
     */
    public void cleanLitterBox() {
        System.out.println("You cleaned " + name + "'s litter box. No gratitude was shown.");
        mood += 5;
    }
}