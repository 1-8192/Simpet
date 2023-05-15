import java.util.Scanner;

public class PetSimulation {
    private static User currentUser;

    private static void initializeUser() {
        String userName = "";
        Scanner inputScanner = new Scanner(System.in);

        while (userName == "") {
            System.out.println("Please enter your name: ");
            userName = inputScanner.nextLine();
            currentUser = new User(userName);
        }

        System.out.println("Welcome " + currentUser.getUserName());
    }

    public static void main(String[] args) {
        System.out.println("Hello User, welcome to PETSIM. We will now create 2 pets for you, a dog and a cat.");
        initializeUser();
        Pet fido = new Dog("Fido");

        Pet mittens = new Cat("Mittens");

        currentUser.addPet(fido);
        currentUser.addPet(mittens);

        System.out.println("Your Pets are:");

        for (Pet pet : currentUser.getPets()) {
            System.out.println(pet);
        }

//        System.out.println("You have created a new dog named " + fido.name + ".");
//        fido.feed();
//        fido.play();
//        fido.train("roll over");
//        fido.sleep();
//        fido.getOlder();
//        System.out.println(fido.toString());
//
//        System.out.println("You have created a new cat named " + mittens.name + ".");
//        mittens.feed();
//        mittens.play();
//        mittens.train("jump through hoop");
//        mittens.sleep();
//        mittens.getOlder();
//        System.out.println(mittens.toString());
//
//        // Downcasting example
//        if (fido instanceof Dog) {
//            Dog dogFido = (Dog) fido;
//            dogFido.train("shake");
//        }
//
//        // Polymorphism example
//        Pet[] pets = {fido, mittens};
//        for (Pet pet : pets) {
//            pet.feed();
//            pet.play();
//            pet.train("fetch");
//            pet.sleep();
//        }
    }
}
