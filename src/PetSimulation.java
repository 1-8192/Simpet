import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Main Simulation class for the SIMPET Pet simulator program.
 */
public class PetSimulation {
    /**
     * User currently using SIMPET.
     */
    private static User currentUser;

    /**
     * Scanner instance we will use to get user input from the terminal.
     */
    private static final Scanner inputScanner = new Scanner(System.in);

    /**
     * Function to ask the user for a name to initialize the user.
     */
    private static void initializeUser() {
        String userName = "";

        while (userName.equals("")) {
            System.out.println("Please enter your name: ");
            userName = inputScanner.nextLine();
            currentUser = new User(userName);
        }

        System.out.println("Welcome " + currentUser.getUserName());
    }

    /**
     * Function to ask the user for pets they want to adopt.
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
                System.out.println("Which breed of dog would you like to adopt?");
                String dogBreed = inputScanner.nextLine();
                // Upcasting Example
                Pet newPet = new Dog(petName, dogBreed);
                currentUser.addPet(newPet);
            } else if (petType.equals("cat")) {
                // Upcasting Example
                Pet newPet = new Cat(petName);
                currentUser.addPet(newPet);
            } else {
                System.out.println("I'm sorry, you can only adopt a dog or a cat for now.");
            }
        }
    }

    /**
     * Method to print results of SIMPET session to a report card external file.
     */
    private static void saveReportCard() throws SimpetIOException {
        try {
            FileWriter fileWriter = new FileWriter("petReportCard.txt");
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(currentUser.getUserName() + " today you interacted with: ");
            for (Pet pet : currentUser.getPets()) {
                // Polymorphism example
                printWriter.println(pet.toString());
            }

            printWriter.close();
            System.out.println("Pet report card has been saved to petReportCard.txt");
        } catch(IOException e) {
            throw new SimpetIOException(e.getMessage());
        }
        System.exit(0);
    }

    /**
     * Function to interact with the user's pets. User will interact with CLI prompts to engage
     * in different activities with a pet.
     */
    private static void interactWithPets() {
        while(true) {
            System.out.print("Which pet would you like to interact with? \n");

            // Getting options for the user. If the user does not have any pets, or they have passed away,
            // the simulation ends.
            int count =0;
            for (int i =0; i < currentUser.getPets().size(); i++) {
                if (!currentUser.getPets().get(i).hasPassed) {
                    count ++;
                    System.out.println((i+1) + ": " + currentUser.getPets().get(i));
                }
            }
            if (count == 0) {
                System.out.println("Your pets have all lived their happy lives. Thanks for using SIMPET!");
                try {
                    saveReportCard();
                } catch (SimpetIOException e) {
                    System.out.println(e.getMessage());
                }
                System.exit(0);
            }
            System.out.println("Enter pet number or type Exit: ");
            String input = inputScanner.nextLine();

            // Main prompt loop.
            if (input.equalsIgnoreCase("exit")) {
                break;
            } else {
                try {
                    int petIndex = Integer.parseInt(input) - 1;
                    if (petIndex < 0 || petIndex >= currentUser.getPets().size()) {
                        System.out.println("Invalid input. Please enter a valid pet number or Exit.");
                        continue;
                    }

                    Pet pet = currentUser.getPets().get(petIndex);
                    System.out.print("How would you like to interact with " + pet.getName() +
                            "? (Feed/Play/Train/Sleep/Clean litter box): ");
                    String action = inputScanner.nextLine();

                    if (action.equalsIgnoreCase("feed")) {
                        // Polymorphism example
                        pet.feed();
                    } else if (action.equalsIgnoreCase("play")) {
                        pet.play();
                    } else if (action.equalsIgnoreCase("train")) {
                        System.out.print("What trick would you like to train " + pet.getName() + " to do? ");
                        String trick = inputScanner.nextLine();
                        // Downcasting example
                        if (pet instanceof Dog) {
                            Dog dogPet = (Dog) pet;
                            dogPet.train(trick);
                        } else {
                            System.out.println("You try to train " + pet.getName() + ", but they don't listen.");
                        }
                    } else if (action.equalsIgnoreCase("clean litter box")) {
                        // Downcasting example
                        if (pet instanceof Cat) {
                            Cat catPet = (Cat) pet;
                            catPet.cleanLitterBox();
                        } else {
                            System.out.println(pet.getName() + " does not have a litter box. " +
                                    "Try taking them outside.");
                        }
                    } else if (action.equalsIgnoreCase("sleep")) {
                        // Polymorphism example
                        pet.sleep();
                    } else {
                        System.out.println("Invalid input. Please enter Feed, Play, Train, Sleep, or Exit.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid pet number or Exit.");
                }
            }

            for (Pet pet : currentUser.getPets()) {
                // Polymorphism example
                pet.getOlder();
            }
        }
    }

    /**
     * Main function for the SIMPET Pet Simulator Program
     * @param args Standard Java Main class args
     */
    public static void main(String[] args) {

        // Welcome message and then user and pet initialization.
        System.out.println("Hello User, welcome to SIMPET. Please follow the prompts to adopt pets and interact " +
                "with them.");
        initializeUser();
        initializePets();

        // Printing a summary of adopted pets.
        System.out.println("Your Pets are:");

        for (Pet pet : currentUser.getPets()) {
            System.out.println(pet);
        }

        // Interactions with pets.
        System.out.println("Let's spend some time with your pets");
        interactWithPets();

        // Save summary in external file and exit program.
        System.out.println("Thanks for using SIMPET!");
        try {
            saveReportCard();
        } catch (SimpetIOException e) {
            System.out.println(e.getMessage());
        }
    }
}
