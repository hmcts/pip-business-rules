package uk.gov.hmcts.reform.demo.errorhandling.exceptions;

/**
 * Exception that captures the message when a publication is not found.
 */
public class PublicationNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4098356982014443120L;

    /**
     * Constructor for the Exception.
     * @param message The message to return to the end user
     */
    public PublicationNotFoundException(String message) {
        super(message);
    }

}
