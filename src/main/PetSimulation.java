package main;

import java.io.*;
import java.util.Scanner;

/**
 * Main Simulation class for the SIMPET main.Pet simulator program.
 */
public class PetSimulation {
    /**
     * main.User currently using SIMPET. Public for testing.
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

        System.out.println("Would you like to enter pet information from a csv file? [y/n]");
        initialPrompt = inputScanner.nextLine();

        if (initialPrompt.equalsIgnoreCase("y")) {
            System.out.println("Please enter the file name: ");
            String fileName = inputScanner.nextLine();
            try {
                initializePetsFromFile(fileName);
                return;
            } catch (SimpetInputException e) {
                System.out.println(e.getMessage());
                System.out.println("Please adopt some pets manually by following the prompts below.");
            }
        } else {
            System.out.println("Please adopt some pets manually by following the prompts below.");
        }

        while (!continuePrompt.equalsIgnoreCase("n") ||
                !continuePrompt.equalsIgnoreCase("no")) {
            System.out.println("Would you like to adopt a pet? [y/n]");
            continuePrompt = inputScanner.nextLine();
            // If the answer is no we are breaking out.
            if (continuePrompt.equals("n") || continuePrompt.equals("no")) {
                break;
            }
            // If the answer is not "yes," loop around again and prompt.
            if (!continuePrompt.equals("y") || !continuePrompt.equals("yes")) {
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
     * Initializes Pets from CSV file. Public for testing.
     *
     * @param fileName the name of the input file.
     */
    public static void initializePetsFromFile(String fileName) throws SimpetInputException {
        // precondition: user passes in a file name that is a csv file of pet info.
        // postcondition: if the file is valid csv format and contains pet information, pets are created.
        // Otherwise, a main.SimpetInputException is thrown.
        if (!fileName.endsWith(".csv")) {
            throw new SimpetInputException("Invalid file format. Only CSV files are supported.");
        }

        String currLine;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while ((currLine = br.readLine()) != null) {
                String[] data = currLine.split(",");

                Pet newPet;
                if (data.length == 3) {
                    String name = data[1];
                    String breed = data[2];
                    newPet = new Dog(name, breed);
                } else {
                    String name = data[1];
                    newPet = new Cat(name);
                }

                currentUser.addPet(newPet);
            }
        } catch (Exception e) {
            throw new SimpetInputException(e.getMessage());
        }
    }

    /**
     * Function to interact with the user's pets. main.User will interact with CLI prompts to engage
     * in different activities with a pet.
     */
    private static void interactWithPets() {
        // postcondition: The user interacts with their pets, and the pets' mood, age, etc. are affected.

        while (true) {
            System.out.print("Which pet would you like to interact with? \n");

            // Getting options for the user. If the user does not have any pets, or they have passed away,
            // the simulation ends.
            int count = 0;
            for (int i = 0; i < currentUser.getPets().size(); i++) {
                if (!currentUser.getPets().get(i).hasPassed) {
                    count++;
                    System.out.println((i + 1) + ": " + currentUser.getPets().get(i));
                }
            }
            if (count == 0) {
                System.out.println("Your pets have all lived their happy lives. Thanks for using SIMPET!");
                try {
                    System.out.println("Please pick a file name for your pet report card: ");
                    String fileName = inputScanner.nextLine();
                    saveReportCard(fileName);
                } catch (SimpetOutputException e) {
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
     * Main function for the SIMPET main.Pet Simulator Program
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

        // Save summary in external file and exit program.
        System.out.println("Thanks for using SIMPET!");
        System.out.println("Please pick a file name for your pet report card: ");
        String fileName = inputScanner.nextLine();
        try {
            saveReportCard(fileName);
        } catch (SimpetOutputException e) {
            System.out.println(e.getMessage());
        }
        PetStatistics petStats = new PetStatistics(currentUser.getPets());
        System.out.println("average age: " + petStats.getAverageAge());
        System.out.println("oldest pet: " + petStats.getOldestPet());
        System.out.println("most common pet type: " + petStats.getMostCommonType());
        System.exit(0);
    }

    /**
     * Method to print results of SIMPET session to a report card external file. Public for testing.
     *
     * @param fileName the name of the report card file.
     */
    public static void saveReportCard(String fileName) throws SimpetOutputException {
        // precondition: user passes in a file name that is txt file
        // postcondition: if the file name is valid txt format, the pet report card is written.
        // Otherwise, a main.SimpetOutputException is thrown.
        if (!fileName.endsWith(".txt")) {
            throw new SimpetOutputException("Invalid file format. Only .txt files are supported.");
        }

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            printWriter.println(currentUser.getUserName() + " today you interacted with: ");
            for (Pet pet : currentUser.getPets()) {
                // Polymorphism example
                printWriter.println(pet.toString());
            }

            printWriter.close();
            System.out.println("Pet report card has been saved to " + fileName);
        } catch (Exception e) {
            throw new SimpetOutputException(e.getMessage());
        }
    }

    /**
     * Setter method for the current User. Only used for unit testing.
     *
     * @param user the user instance we want to set.
     */
    public void setCurrentUser(User user) {
        currentUser = user;
    }
}