package main;

import java.io.IOException;

public class SimpetOutputException extends IOException {
    public SimpetOutputException(String message) {
        super("Sorry, but we ran into an issue writing data to file: " + message);
    }
}
