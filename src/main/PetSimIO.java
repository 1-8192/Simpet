package main;

import java.io.*;

/**
 * Static helper class to handle I/O functionality for the SimPet Pet Simulation program.
 */
public class PetSimIO {
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
