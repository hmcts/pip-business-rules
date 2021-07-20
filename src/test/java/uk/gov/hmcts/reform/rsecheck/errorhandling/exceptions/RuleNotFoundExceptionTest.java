package uk.gov.hmcts.reform.rsecheck.errorhandling.exceptions;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.demo.errorhandling.exceptions.RuleNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleNotFoundExceptionTest {

    @Test
    public void testCreationOfRuleNotFoundException() {

        RuleNotFoundException ruleNotFoundException
            = new RuleNotFoundException("This is a test message");
        assertEquals("This is a test message", ruleNotFoundException.getMessage(),
                     "The message should match the message passed in");

    }

}
