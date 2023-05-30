package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Generic Class to process statistics on a user's group of pets.
 *
 * @param <T> Generic type that extends the Pet abstract class.
 */
public class PetStatistics<T extends Pet> {
    /**
     * List of Pets we want statistics for.
     */
    private List<T> petList;

    public PetStatistics(List<T> petList) {
        this.petList = petList;
    }

    /**
     * Gets the average of all pets owned by the user.
     *
     * @return the average age of pets.
     */
    public double getAverageAge() {
        // pre-condition: a list of pets.
        // post-condition: the average age of pets is returned.

        double sumAge = 0;
        for (T pet : petList) {
            sumAge += pet.getAge();
        }
        return sumAge / petList.size();
    }

    /**
     * Gets the most common type of pet in the user's group of pets.
     *
     * @return 'dog' or 'cat' or the most common type.
     */
    public String getMostCommonType() {
        // pre-condition: a list of pets.
        // post-condition: the most common pet type is returned as a string.

        Map<String, Integer> typeCountMap = new HashMap<>();
        for (T pet : petList) {
            String type = pet.getClass().getSimpleName();
            typeCountMap.put(type, typeCountMap.getOrDefault(type, 0) + 1);
        }
        int maxCount = 0;
        String mostCommonType = "";
        for (Map.Entry<String, Integer> entry : typeCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonType = entry.getKey();
            }
        }
        return mostCommonType;
    }

    /**
     * Method to find the oldest pet in a list of pets.
     *
     * @return the instance of the oldest pet.
     */
    public T getOldestPet() {
        // pre-condition: a list of pet types.
        // post-condition: the instance of the pet with the oldest age is returned.

        T oldestPet = null;
        int maxAge = 0;
        for (T pet : petList) {
            if (pet.getAge() > maxAge) {
                maxAge = pet.getAge();
                oldestPet = pet;
            }
        }
        return oldestPet;
    }
}
