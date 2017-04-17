package ua.dominos.service.exception;

/**
 * Signals that dominoes service is unavailable.
 */
public class DominoesServiceException extends Exception {
    private static final String DEFAULT_MSG = "Attempt to use dominoes service was failed";

    public DominoesServiceException() {
        super(DEFAULT_MSG);
    }

    public DominoesServiceException(String message) {
        super(message);
    }
}
