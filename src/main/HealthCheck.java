package main;
public class HealthCheck<T extends Pet> {
    public void performCheckup(T pet) {
        // Perform specific health checks for the given pet type
        if (pet instanceof Dog) {
            // Health checks for dogs
            Dog dog = (Dog) pet;
            // Perform dog-specific health checks
            // ...
        } else if (pet instanceof Cat) {
            // Health checks for cats
            Cat cat = (Cat) pet;
            // Perform cat-specific health checks
            // ...
        } else {
            // Unsupported pet type
            throw new IllegalArgumentException("Unsupported pet type for health check.");
        }
    }
}
