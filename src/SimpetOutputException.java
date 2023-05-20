import java.io.IOException;

public class SimpetOutputException extends IOException {
    public SimpetOutputException(String message) {
        super("Sorry, but we ran into an issue processing your pet report card: " + message);
    }
}
