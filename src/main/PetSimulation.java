package main;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
     * Threadpool used for concurrency.
     */
    private static ExecutorService threadPool;

    /**
     * Method to end the simulation.
     */
    private static void endSimulation() {
        //post-condition: the pet report card is saved and the simulation is over.

        // Just in case, we are forcefully shutting down the threadpool here.
        if (threadPool != null) {
            threadPool.shutdownNow();
        }

        System.out.println("Thanks for using SIMPET!");
        try {
            System.out.println("Please pick a file name for your pet report card: ");
            String fileName = inputScanner.nextLine();
            saveReportCard(currentUser, fileName);
        } catch (SimpetOutputException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    /**
     * Helper method to get a desired activity from the user.
     *
     * @param pet the pwt being interacted with.
     *
     * @return string for the activity.
     */
    private static String getUserActivity(Pet pet) {
        // precondition: pet provided.
        // post condition: the string name for the activity is confirmed and returned.

        System.out.println("Enter the activity you want to do with Pet " + pet.getName());
        System.out.println("Activities: Feed, Play, Train, Sleep, Health Checkup, Exit");
        String activity = inputScanner.nextLine();
        while (!isValidActivity(activity)) {
            System.out.println("Invalid activity. Please enter a valid activity or Exit: ");
            activity = inputScanner.nextLine();
        }
        return activity.toLowerCase();
    }

    /**
     * Function to ask the user for a name to initialize the user. Public for testing.
     */
    public static void initializeUser() {
        // postcondition: A user instance is created for the user using the provided name. If the user is new, their
        // info is saved to the DB.

        String userName = "";

        while (userName.equals("")) {
            System.out.println("Please enter your name: ");
            userName = inputScanner.nextLine();
            currentUser = new User(userName);

            if (currentUser.checkIfUserExists()) {
                System.out.println("Welcome back, " + currentUser.getUserName() + "!");
            } else {
                currentUser.saveUserInfo();
                System.out.println("Great to meet you,  " + currentUser.getUserName() + ". We have saved your info" +
                        " for future sessions.");
            }
        }
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

        System.out.println("Would you like to load existing pets from the database? [y/n]");
        initialPrompt = inputScanner.nextLine();

        if (initialPrompt.equalsIgnoreCase("y")) {
            try {
                Integer petCount = PetDAO.checkUserPetsInDatabase(currentUser.getUserName());
                if (petCount > 0) {
                    System.out.println("You have " + petCount + " pets. Loading now...");
                    loadPetsFromDatabase(currentUser);
                    return;
                } else {
                    System.out.println("You do not have any pets saved." +
                            "Please adopt some pets manually by following the prompts below.");
                }
            } catch (SimpetInputException e) {
                System.out.println(e.getMessage());
                System.out.println("Please adopt some pets manually by following the prompts below.");
            } catch (SQLException e) {
                throw new RuntimeException(e);
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
                PetDAO.savePetInfo(newPet, currentUser.getUserName());
            } else if (petType.equals("cat")) {
                // Upcasting Example
                Pet newPet = new Cat(petName);
                currentUser.addPet(newPet);
                PetDAO.savePetInfo(newPet, currentUser.getUserName());
            } else {
                System.out.println("I'm sorry, you can only adopt a dog or a cat for now.");
            }
        }
    }

    /**
     * Function to interact with the user's pets. User will interact with CLI prompts to engage
     * in different activities with a pet.Public for testing.
     */
    public static void interactWithPets() throws RuntimeException {
        // postcondition: The user interacts with their pets, and the pets' mood, age, etc. are affected.

        // Create a thread pool with a fixed number of threads based on the number of pets + an extra stream
        // for aging pets.
        threadPool = Executors.newFixedThreadPool(currentUser.getPets().size() + 1);
        // Separate threads we will need.
        List<Thread> threads = new ArrayList<>();

        // Create a separate thread for background aging, which will take place async from pet interactions.
        Thread getOlderThread =  new Thread(() -> {
            while (currentUser.getPets().size() != 0) {
                // Perform background aging for all pets
                for (Pet pet : currentUser.getPets()) {
                    pet.getOlder();
                }

                // Removing deceased pets from list. If pets are deceased, leaving simulation.
                currentUser.removeDeceasedPets();

                // Sleep for a certain period between each background task
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // If we hit this point, there are no more pets, and we can end the simulation.
            endSimulation();
        });
        threadPool.submit(getOlderThread);

        // Main interaction loop where we build threads based on activities for each pet.
        while (currentUser.getPets().size() != 0) {
            // Removing deceased pets from list. If pets are deceased, leaving simulation.
            currentUser.removeDeceasedPets();
            if (currentUser.getPets().size() == 0) {
                // Shut down the executor service gracefully
                threadPool.shutdown();
                break;
            }
            // printing pet info for user to choose from.
            for (int i = 0; i < currentUser.getPets().size(); i++) {
                    System.out.println((i + 1) + ": " + currentUser.getPets().get(i));
            }
            for (Pet pet : currentUser.getPets()) {
                String activity = getUserActivity(pet);
                if (activity.equalsIgnoreCase("exit")) {
                    threadPool.shutdownNow();
                    return;
                }
                Thread thread = new Thread(() -> {
                    performActivityWithPet(pet, activity);
                });
                threads.add(thread);
            }

            // Adding all the pet activity threads to the threadpool to execute.
            for (Thread currThread : threads) {
                threadPool.submit(currThread);
            }

            // Waiting for next loop iteration to give threads a chance to happen.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // clear threads before the next loop.
            threads.clear();
        }

        // Shut down the executor service gracefully
        threadPool.shutdown();
    }

    /**
     * Helper method to check if pet activity is valid. Public for testing.
     *
     * @param activity string input.
     *
     * @return boolean check whether activity is valid.
     */
    public static boolean isValidActivity(String activity) {
        // post-condition: if activity is valid true is returned.

        return activity.equalsIgnoreCase("feed") ||
                activity.equalsIgnoreCase("play") ||
                activity.equalsIgnoreCase("train") ||
                activity.equalsIgnoreCase("sleep") ||
                activity.equalsIgnoreCase("health checkup") ||
                activity.equalsIgnoreCase("exit");
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
        try {
            interactWithPets();
        } catch (RuntimeException e) {
            // we would only be catching thread sleep interruptions here, so just ending the program if that happens.
            endSimulation();
        }

        // Save pets objects to bin file for later use if the user still has some live pets.
        System.out.println("Saving your pet info to savedPets.bin file: ");
        try {
            savePets(currentUser, binFileName);
        } catch (SimpetOutputException e) {
            System.out.println(e.getMessage());
        }

        // Save summary in external file and exit program.
        endSimulation();
    }

    /**
     * Perm an activity with a pet. Public for testing.
     *
     * @param pet      the pet the user will spend time with.
     * @param activity the activity to do.
     */
    public static void performActivityWithPet(Pet pet, String activity) {
        //post-condition: the activity is performed, and the pet's mood and health change.

        if (activity.equalsIgnoreCase("feed")) {
            pet.feed();
        } else if (activity.equalsIgnoreCase("play")) {
            pet.play();
        } else if (activity.equalsIgnoreCase("train")) {
            if (pet instanceof Dog) {
                Dog dogPet = (Dog) pet;
                dogPet.train();
            } else {
                System.out.println("You try to train " + pet.getName() + ", but they don't listen.");
            }
        } else if (activity.equalsIgnoreCase("sleep")) {
            pet.sleep();
        } else if (activity.equalsIgnoreCase("health checkup")) {
            HealthCheck<Pet> healthCheck = new HealthCheck<>();
            healthCheck.performCheckup(pet);
        }
    }
}
