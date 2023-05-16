class Cat extends Pet {
    public Cat(String name) {
        super(name);
    }

    public void feed() {
        System.out.println(name + " is eating cat food.");
        mood += 5;
    }
}