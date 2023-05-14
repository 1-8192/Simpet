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