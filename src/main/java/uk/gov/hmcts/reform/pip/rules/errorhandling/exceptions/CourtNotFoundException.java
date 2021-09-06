package uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions;

/**
 * Exception that captures the message when a court is not found.
 */
public class CourtNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4098356982014443120L;

    /**
     * Constructor for the Exception.
     * @param message The message to return to the end user
     */
    public CourtNotFoundException(String message) {
        super(message);
    }

}
