package uk.gov.hmcts.reform.demo.errorhandling.exceptions;

/**
 * Exception that captures the message when a subscription is not found.
 */
public class RuleNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -4098356982014443120L;

    /**
     * Constructor for the Exception.
     * @param message The message to return to the end user
     */
    public RuleNotFoundException(String message) {
        super(message);
    }

}
