package ua.dominos.exception;

public class DominoesServiceException extends Exception {
    private static final String DEFAULT_MSG = "Attempt to user dominoes service was failed";

    public DominoesServiceException() {
        super(DEFAULT_MSG);
    }

    public DominoesServiceException(String message) {
        super(message);
    }
}
