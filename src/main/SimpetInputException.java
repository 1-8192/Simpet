package main;

import java.io.IOException;

public class SimpetInputException extends IOException {
    public SimpetInputException(String message) {
        super("Sorry, but we ran into an issue processing your submitted csv file: " + message);
    }
}
