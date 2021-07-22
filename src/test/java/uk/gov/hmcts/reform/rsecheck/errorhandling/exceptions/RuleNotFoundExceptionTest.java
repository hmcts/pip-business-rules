package uk.gov.hmcts.reform.rsecheck.errorhandling.exceptions;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.PublicationNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleNotFoundExceptionTest {

    @Test
    public void testCreationOfRuleNotFoundException() {

        PublicationNotFoundException publicationNotFoundException
            = new PublicationNotFoundException("This is a test message");
        assertEquals("This is a test message", publicationNotFoundException.getMessage(),
                     "The message should match the message passed in");

    }

}
