package hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Cat extends Pet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Cat(String name) {
        super(name);
    }

    public Cat() {
        
    }

    /**
     * Feeding activity.
     */
    public void feed() {
        // postcondition: The cat's mood is improved.
        System.out.println(name + " is eating cat food.");
        mood += 5;
    }

    /**
     * Activity specific to the cat pet type.
     * Improves mood and prints message to screen.
     */
    public void cleanLitterBox() {
        // postcondition: the cat's mood is improved.
        System.out.println("You cleaned " + name + "'s litter box. No gratitude was shown.");
        mood += 5;
    }
}
