package main;

import java.io.*;

/**
 * Static helper class to handle I/O functionality for the SimPet Pet Simulation program.
 */
public class PetSimIO {

    /**
     * Loads previously saved Pets from a bin file.
     *
     * @param fileName the name of the input file.
     */
    public static void loadPetsFromFile(User currentUser, String fileName) throws SimpetInputException {
        // precondition: user passes in a file name that is a binary file of pet objects.
        // postcondition: if the file is valid and contains pet objects, pets are created.
        // Otherwise, a SimpetInputException is thrown.
        if (!fileName.endsWith(".bin")) {
            throw new SimpetInputException("Invalid file format. Only binary files are supported.");
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            while (true) {
                try {
                    Pet pet = (Pet) ois.readObject();
                    currentUser.addPet(pet);
                } catch (ClassNotFoundException e) {
                    throw new SimpetInputException("Error reading pet objects from file: " + e.getMessage());
                } catch (EOFException e) {
                    // End of file reached
                    break;
                }
            }
        } catch (Exception e) {
            throw new SimpetInputException("Error reading pet objects from bin file: " + e.getMessage());
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
            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Pet pet : currentUser.getPets()) {
                objectOutputStream.writeObject(pet);
            }

            objectOutputStream.close();
            System.out.println("Pet information has been saved to " + fileName);
        } catch (Exception e) {
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
