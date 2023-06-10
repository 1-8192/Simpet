package main;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static main.PetSimIO.*;

/**
 * Main Simulation class for the SIMPET Pet simulator program.
 */
public class PetSimulation {
    /**
     * User currently using SIMPET. Public for testing.
     */
    public static User currentUser;

    /**
     * Scanner instance we will use to get user input from the terminal.
     */
    private static final Scanner inputScanner = new Scanner(System.in);

    /**
     * The bin file where pet objects are saved.
     */
    private static final String binFileName = "savedPets.bin";

    /**
     * thread pool count for concurrent pet aging.
     */
    private static final Integer threadPoolCount = 2;

    /**
     * Function to ask the user for a name to initialize the user. Public for testing.
     */
    public static void initializeUser() {
        // postcondition: A user instance is created for the user using the provided name.

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
        // postcondition: The user has pets created and assigned to their user instance, either through an
        // input CSV file or by following CLI pompts.

        String petName = "";
        String petType = "";
        String initialPrompt;
        String continuePrompt = "y";

        System.out.println("Would you like to load existing pets from a save file? [y/n]");
        initialPrompt = inputScanner.nextLine();

        if (initialPrompt.equalsIgnoreCase("y")) {
            try {
                loadPetsFromFile(currentUser, binFileName);
                return;
            } catch (SimpetInputException e) {
                System.out.println(e.getMessage());
                System.out.println("Please adopt some pets manually by following the prompts below.");
            }
        } else {
            System.out.println("Please adopt some pets manually by following the prompts below.");
        }

        while (!continuePrompt.equalsIgnoreCase("n") &&
                !continuePrompt.equalsIgnoreCase("no")) {
            System.out.println("Would you like to adopt a pet? [y/n]");
            continuePrompt = inputScanner.nextLine();
            // If the answer is no we are breaking out.
            if (continuePrompt.equals("n") || continuePrompt.equals("no")) {
                break;
            }
            // If the answer is not "yes," loop around again and prompt.
            if (!continuePrompt.equals("y") && !continuePrompt.equals("yes")) {
                continue;
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
     * Function to interact with the user's pets. User will interact with CLI prompts to engage
     * in different activities with a pet.Public for testing.
     */
    public static void interactWithPets() {
        // postcondition: The user interacts with their pets, and the pets' mood, age, etc. are affected.

        // Create a thread pool with a fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(threadPoolCount);

        // Create a separate thread for background aging and health checks
        executorService.submit(() -> {
            while (true) {
                // Perform background aging for all pets
                for (Pet pet : currentUser.getPets()) {
                    pet.getOlder();
                }

                // Perform background health checks for all pets
                for (Pet pet : currentUser.getPets()) {
                    HealthCheck<Pet> healthCheck = new HealthCheck<>();
                    healthCheck.performCheckup(pet);
                }

                try {
                    // Sleep for a certain period between each background task
                    Thread.sleep(5000); // Adjust the sleep duration as needed
                } catch (InterruptedException e) {
                    // Handle interruption if required
                    System.out.println(e.getMessage());
                }
            }
        });

        while (true) {
            System.out.print("Which pet would you like to interact with? \n");

            // Removing deceased pets from list. If pets are deceased, leaving simulation.
            currentUser.removeDeceasedPets();
            if (currentUser.getPets().size() == 0) {
                System.out.println("Your pets have all lived their happy lives. Thanks for using SIMPET!");
                try {
                    System.out.println("Please pick a file name for your pet report card: ");
                    String fileName = inputScanner.nextLine();
                    saveReportCard(currentUser, fileName);
                } catch (SimpetOutputException e) {
                    System.out.println(e.getMessage());
                }
                System.exit(0);
            }

            // printing pet info for user to choose from.
            for (int i = 0; i < currentUser.getPets().size(); i++) {
                    System.out.println((i + 1) + ": " + currentUser.getPets().get(i));
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
                            "? (Feed/Play/Train/Sleep/Clean litter box/health checkup): ");
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
                    } else if (action.equalsIgnoreCase("health checkup")) {
                        // Use of generic class
                        HealthCheck<Pet> healthCheck = new HealthCheck<>();
                        healthCheck.performCheckup(pet);
                    } else {
                        System.out.println("Invalid input. Please enter Feed, Play, Train, Sleep, health checkup " +
                                "or Exit.");
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
     *
     * @param args Standard Java Main class args
     */
    public static void main(String[] args) {
        // Welcome message and then user and pet initialization.
        System.out.println("Hello, welcome to SIMPET. Please follow the prompts to adopt pets and interact " +
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

        // Save pets objects to bin file for later use.
        System.out.println("Saving your pet info to savedPets.bin file: ");
        try {
            savePets(currentUser, binFileName);
        } catch (SimpetOutputException e) {
            System.out.println(e.getMessage());
        }

        // Save summary in external file and exit program.
        System.out.println("Thanks for using SIMPET!");
        System.out.println("Please pick a file name for your pet report card: ");
        String fileName = inputScanner.nextLine();
        try {
            saveReportCard(currentUser, fileName);
        } catch (SimpetOutputException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }
}
