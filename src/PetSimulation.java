import java.util.Scanner;

/**
 * Main Simulation class for the SIMPET Pet simulator program.
 */
public class PetSimulation {
    private static User currentUser;
    private static final Scanner inputScanner = new Scanner(System.in);

    /**
     * Function to ask the user for a name to initialize the user.
     */
    private static void initializeUser() {
        String userName = "";

        while (userName == "") {
            System.out.println("Please enter your name: ");
            userName = inputScanner.nextLine();
            currentUser = new User(userName);
        }

        System.out.println("Welcome " + currentUser.getUserName());
    }

    /**
     * Function to ask the user for pets they want to make.
     */
    private static void initializePets() {
        String petName = "";
        String petType = "";
        String continuePrompt = "y";

        while (!continuePrompt.equals("n")) {
            System.out.println("Would you like to adopt a pet? [y/n]");
            continuePrompt = inputScanner.nextLine();
            if (continuePrompt.equals("n")) {
                break;
            }
            System.out.println("Would you like to adopt a dog or a cat?");
            petType = inputScanner.nextLine();
            System.out.println("What would you like to name your pet?");
            petName = inputScanner.nextLine();
            if (petType.equals("dog")) {
                Pet newPet = new Dog(petName);
                currentUser.addPet(newPet);
            } else if (petType.equals("cat")) {
                Pet newPet = new Cat(petName);
                currentUser.addPet(newPet);
            } else {
                System.out.println("I'm sorry, you can only adopt a dog or a cat for now.");
            }
        }
    }

    /**
     * Main function for the SIMPET Pet Simulator Program
     * @param args Standard Java Main class args
     */
    public static void main(String[] args) {
        System.out.println("Hello User, welcome to PETSIM. We will now create 2 pets for you, a dog and a cat.");
        initializeUser();
        initializePets();

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
        System.out.println("Let's spend some time with your pets.");
        // Polymorphism example
        for (Pet pet : currentUser.getPets()) {
            pet.feed();
            pet.play();
            pet.train("fetch");
            pet.sleep();
        }
    }
}
