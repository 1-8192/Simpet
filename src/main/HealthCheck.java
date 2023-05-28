package main;
public class HealthCheck<T extends Pet> {
    private String healthMessage;
    private String moodMessage;

    /**
     * Getter method for health Message.
     * @return the pet's health message.
     */
    public String getHealthMessage() {
        return healthMessage;
    }

    public String getMoodMessage() {
        return moodMessage;
    }

    public void performCheckup(T pet) {
        // Perform specific health checks for the given pet type
        if (pet instanceof Dog) {
            // Health checks for dogs
            Dog dog = (Dog) pet;
            if (dog.getAge() > 8) {
                healthMessage = "Your dog " + dog.getName() + " is generally healthy, but is getting older. ";
            } else {
                healthMessage = "Your dog " + dog.getName() + " is in the prime of his life. ";
            }
            if (dog.getMood() < 50) {
                moodMessage = "Your dog " + dog.getName() + " needs more stimulation. Consider longer play times.";
            } else {
                moodMessage = "Your dog " + dog.getName() + " seems very happy and fulfilled.";
            }
        } else if (pet instanceof Cat) {
            // Health checks for cats
            Cat cat = (Cat) pet;
            if (cat.getAge() > 12) {
                healthMessage = "Your cat " + cat.getName() + " is generally healthy, but is getting older. ";
            } else {
                healthMessage = "Your cat " + cat.getName() + " is in the prime of their life. ";
            }
            if (cat.getMood() < 30) {
                moodMessage = "Your cat " + cat.getName() + " needs more stimulation. Consider buying some toys.";
            } else {
                moodMessage = "Your cat " + cat.getName() + " seems very happy and fulfilled.";
            }
        } else {
            // Unsupported pet type
            throw new IllegalArgumentException("Unsupported pet type for health check.");
        }
        System.out.println(healthMessage + moodMessage);
    }
}
