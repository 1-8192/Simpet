package main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Static helper class to handle I/O functionality for the SimPet Pet Simulation program.
 */
public class PetSimIO {

    /**
     * Loads previously saved Pets from the database.
     *
     * @param currentUser the current user.
     */
    public static void loadPetsFromDatabase(User currentUser) throws SimpetInputException {
        // precondition: user passes in a file name that is a binary file of pet objects.
        // postcondition: if the file is valid and contains pet objects, pets are created.
        // Otherwise, a SimpetInputException is thrown.
        try {
            ResultSet results = PetDAO.loadUserPetsFromDB(currentUser.getUserName());
            while (results.next()) {
                String petName = results.getString(2);
                Integer mood = results.getInt(3);
                Integer health = results.getInt(4);
                Boolean hasPassed = results.getBoolean(5);
                String petType = results.getString(6);
                if (petType.equals("dog")) {
                    String breed = results.getString(7);
                    Pet newPet = new Dog(petName, breed, mood, health, hasPassed);
                    currentUser.addPet(newPet);
                } else {
                    Pet newPet = new Cat(petName, mood, health, hasPassed);
                    currentUser.addPet(newPet);
                }
            }
        } catch (SQLException e) {
            throw new SimpetInputException(e.getMessage());
        }
    }

    /**
     * Method to save the user's pets to a binary file for later.
     *
     * @param fileName the name of the report card file.
     */
    public static void savePets(User currentUser, String fileName) throws SimpetOutputException {
        // precondition: user passes in a file name that is a binary file
        // postcondition: if the file name is valid binary format, the pet report card is written.
        // Otherwise, a SimpetOutputException is thrown.
        if (!fileName.endsWith(".bin")) {
            throw new SimpetOutputException("Invalid file format. Only .bin files are supported.");
        }

        try {
            // For this case we are saving at the end of a session for the following session, so we always want
            // to overwrite the file even if it already exists.
            FileOutputStream fileOutputStream = new FileOutputStream(fileName, false);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Pet pet : currentUser.getPets()) {
                objectOutputStream.writeObject(pet);
                System.out.println(pet);
            }

            objectOutputStream.close();
            fileOutputStream.close();
            System.out.println("Pet information has been saved to " + fileName);
        } catch (IOException e) {
            throw new SimpetOutputException(e.getMessage());
        }
    }

    /**
     * Method to print results of SIMPET session to a report card external file. Public for testing.
     *
     * @param fileName the name of the report card file.
     */
    public static void saveReportCard(User currentUser, String fileName) throws SimpetOutputException {
        // precondition: user passes in a file name that is txt file
        // postcondition: if the file name is valid txt format, the pet report card is written.
        // Otherwise, a SimpetOutputException is thrown.
        if (!fileName.endsWith(".txt")) {
            throw new SimpetOutputException("Invalid file format. Only .txt files are supported.");
        }

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // Saving pet summary to file
            printWriter.println(currentUser.getUserName() + " today you interacted with: ");
            for (Pet pet : currentUser.getPets()) {
                // Polymorphism example
                printWriter.println(pet.toString());
            }

            // Saving pet statistics to file *Generics example
            printWriter.println("Here are some general statistics for your pets:");
            PetStatistics<Pet> petStats = new PetStatistics<>(currentUser.getPets());
            printWriter.println("The average pet age was: " + petStats.getAverageAge());
            printWriter.println("Your oldest pet(s): " + petStats.getOldestPet());
            printWriter.println("Your happiest pet(s): " + petStats.getHappiestPet());
            printWriter.println("Your most common pet type: " + petStats.getMostCommonType());

            printWriter.close();
            System.out.println("Pet report card has been saved to " + fileName);
        } catch (Exception e) {
            throw new SimpetOutputException(e.getMessage());
        }
    }
}
