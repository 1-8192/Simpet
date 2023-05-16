class Cat extends Pet {
    public Cat(String name) {
        super(name);
    }

    public void feed() {
        System.out.println(name + " is eating cat food.");
        mood += 5;
    }

    public void cleanLitterBox() {
        System.out.println("You cleaned " + name + "'s litter box. No gratitude was shown.");
        mood += 5;
    }
}