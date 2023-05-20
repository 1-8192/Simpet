import java.io.IOException;

public class SimpetIOException extends IOException {
    public SimpetIOException(String message) {
        super("Sorry, but we ran into an issue processing your I/O request: " + message);
    }
}
