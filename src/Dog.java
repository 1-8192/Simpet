class Dog extends Pet {
    private String breed;

    public Dog(String name) {
        super(name);
        breed = "Golden Retriever";
    }

    public void feed() {
        System.out.println(name + " is eating dog food.");
        mood += 5;
    }

    public void train(String trick) {
        System.out.println(name + " is learning to " + trick + ".");
        mood += 10;
    }

    public String getBreed() {
        return breed;
    }

    public String toString() {
        return "Name: " + name + ", Breed: " + breed + ", Age: " + age + ", Health: " + health + ", Mood: " + mood;
    }
}