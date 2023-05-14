public class PetSimulation {
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
