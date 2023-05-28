package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PetStatistics<T extends Pet> {
    private List<T> petList;

    public PetStatistics(List<T> petList) {
        this.petList = petList;
    }

    public double getAverageAge() {
        double sumAge = 0;
        for (T pet : petList) {
            sumAge += pet.getAge();
        }
        return sumAge / petList.size();
    }

    public String getMostCommonType() {
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

    public T getOldestPet() {
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
