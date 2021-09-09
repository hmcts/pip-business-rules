package uk.gov.hmcts.reform.rsecheck.errorhandling;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.pip.rules.errorhandling.ExceptionResponse;
import uk.gov.hmcts.reform.pip.rules.errorhandling.GlobalExceptionHandler;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.CourtNotFoundException;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.PublicationNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {

    @Test
    @DisplayName("Test that the response entity returned from the exception handler, "
        + "contains the expected status code and body")
    public void testHandleSubscriptionNotFoundMethod() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        CourtNotFoundException courtNotFoundException
            = new CourtNotFoundException("This is a test message for court");

        ResponseEntity<ExceptionResponse> responseCourtEntity =
            globalExceptionHandler.handleCourtNotFound(courtNotFoundException);

        assertEquals(HttpStatus.NOT_FOUND, responseCourtEntity.getStatusCode(), "Status code should be not found");
        assertNotNull(responseCourtEntity.getBody(), "Response should contain a body");
        assertEquals("This is a test message for court", responseCourtEntity.getBody().getMessage(),
                     "The message should match the message passed in");
    }
}
