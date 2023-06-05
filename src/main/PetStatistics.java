package main;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generic Class to process statistics on a user's group of pets.
 *
 * @param <T> Generic type that extends the Pet abstract class.
 */
//public class PetStatistics<T extends Pet> {
//    /**
//     * List of Pets we want statistics for.
//     */
//    private List<T> petList;
//
//    public PetStatistics(List<T> petList) {
//        this.petList = petList;
//    }
//
//    /**
//     * Gets the average of all pets owned by the user.
//     *
//     * @return the average age of pets.
//     */
//    public double getAverageAge() {
//        // pre-condition: a list of pets.
//        // post-condition: the average age of pets is returned.
//
//        double sumAge = 0;
//        for (T pet : petList) {
//            sumAge += pet.getAge();
//        }
//        return sumAge / petList.size();
//    }
//
//    /**
//     * Gets the most common type of pet in the user's group of pets.
//     *
//     * @return 'dog' or 'cat' or the most common type.
//     */
//    public String getMostCommonType() {
//        // pre-condition: a list of pets.
//        // post-condition: the most common pet type is returned as a string.
//
//        Map<String, Integer> typeCountMap = new HashMap<>();
//        for (T pet : petList) {
//            String type = pet.getClass().getSimpleName();
//            typeCountMap.put(type, typeCountMap.getOrDefault(type, 0) + 1);
//        }
//        int maxCount = 0;
//        String mostCommonType = "";
//        for (Map.Entry<String, Integer> entry : typeCountMap.entrySet()) {
//            if (entry.getValue() > maxCount) {
//                maxCount = entry.getValue();
//                mostCommonType = entry.getKey();
//            }
//        }
//        return mostCommonType;
//    }
//
//    /**
//     * Method to find the oldest pet in a list of pets.
//     *
//     * @return the name of the oldest pet or pets.
//     */
//    public String getOldestPet() {
//        // pre-condition: a list of pet types.
//        // post-condition: the name of the pet or pets with the oldest age is returned.
//
//        String oldestPet = "";
//        int maxAge = 0;
//        for (T pet : petList) {
//            if (pet.getAge() == maxAge && maxAge != 0) {
//                oldestPet += " and " + pet.getName();
//            }
//            if (pet.getAge() > maxAge) {
//                maxAge = pet.getAge();
//                oldestPet = pet.getName();
//            }
//        }
//        return oldestPet;
//    }
//
//    /**
//     * Method to find the happiest pet in a list of pets.
//     *
//     * @return the instance of the happiest pet.
//     */
//    public String getHappiestPet() {
//        // pre-condition: a list of pet types.
//        // post-condition: the name of the pet or pets with the highest mood is returned.
//
//        String happiestPet = "";
//        int maxMood = 0;
//        for (T pet : petList) {
//            if (pet.getMood() == maxMood && maxMood != 0) {
//                happiestPet += " and " + pet.getName();
//            }
//            if (pet.getMood() > maxMood) {
//                maxMood = pet.getMood();
//                happiestPet = pet.getName();
//            }
//        }
//        return happiestPet;
//    }
//}

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
     * @return the average age of pets.
     */
    public double getAverageAge() {
        return petList.stream()
                .mapToDouble(Pet::getAge)
                .average()
                .orElse(0.0);
    }
    /**
     Gets the most common type of pet in the user's group of pets.

     @return 'dog' or 'cat' or the most common type.
     */
    public String getMostCommonType() {
        Map<String, Long> typeCountMap = petList.stream()
                .collect(Collectors.groupingBy(pet -> pet.getClass().getSimpleName(), Collectors.counting()));

        return typeCountMap.entrySet().stream()
                .max(Comparator.comparingLong(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("");
    }

    /**

     Method to find the oldest pet in a list of pets.

     @return the name of the oldest pet or pets.
     */
    public String getOldestPet() {
        int maxAge = petList.stream()
                .mapToInt(Pet::getAge)
                .max()
                .orElse(0);

        return petList.stream()
                .filter(pet -> pet.getAge() == maxAge)
                .map(Pet::getName)
                .collect(Collectors.joining(" and "));
    }

    /**

     Method to find the happiest pet in a list of pets.

     @return the name of the pet or pets with the highest mood.
     */
    public String getHappiestPet() {
        int maxMood = petList.stream()
                .mapToInt(Pet::getMood)
                .max()
                .orElse(0);

        return petList.stream()
                .filter(pet -> pet.getMood() == maxMood)
                .map(Pet::getName)
                .collect(Collectors.joining(" and "));
    }
}