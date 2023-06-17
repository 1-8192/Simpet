import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Generic Class to process statistics on a user's group of pets. Current version has been refactored
 * to feature streams and lambdas.
 *
 * @param <T> Generic type that extends the Pet abstract class.
 */
public class PetStatistics<T extends Pet> {
    /**
     * List of Pets we want statistics for.
     */
    private final List<T> petList;
    public PetStatistics(List<T> petList) {
        this.petList = petList;
    }

    /**
     * Gets the average of all pets owned by the user. Uses a stream and the average() method rather than
     * a standard loop.
     *
     * @return the average age of pets.
     */
    public double getAverageAge() {
        // pre-condition: a list of pets
        // post-condition: list is unchanged, the average age of pets in the list is returned.

        return petList.stream()
                .mapToDouble(Pet::getAge)
                .average()
                .orElse(0.0);
    }

    /**
     * Gets the most common type of pet in the user's group of pets using streams.
     *
     * @return 'dog' or 'cat' or the most common type.
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
     * Method to find the oldest pet in a list of pets. Using streams.
     *
     * @return the name of the oldest pet or pets.
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
     * Method to find the happiest pet in a list of pets using streams.
     *
     * @return the name of the pet or pets with the highest mood.
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