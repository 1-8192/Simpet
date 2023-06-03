package main;

import java.io.IOException;

/**
 * Custom exception class to handle issues with loading pet objects from a file.
 */
public class SimpetInputException extends IOException {
    /**
     * Prepending an input file specific error before showing the actual error message.
     *
     * @param message the error message.
     */
    public SimpetInputException(String message) {
        super("Sorry, but we ran into an issue processing the object bin file: " + message);
    }
}
