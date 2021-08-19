package uk.gov.hmcts.reform.rsecheck.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.pip.rules.controllers.RootController;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.PublicationNotFoundException;
import uk.gov.hmcts.reform.pip.rules.model.api.Publication;
import uk.gov.hmcts.reform.pip.rules.rules.RulesService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RootControllerTest {

    @Mock
    RulesService rulesService;

    @InjectMocks
    RootController rootController;

    @Test
    @DisplayName("Tests that a 200 code is returned, with the correct response message")
    public void testWelcomeMessage() {
        ResponseEntity<String> welcomeResponse = rootController.welcome();
        assertEquals(HttpStatus.OK, welcomeResponse.getStatusCode(), "An OK response code is returned");
        assertEquals("Welcome to pip-business-rules", welcomeResponse.getBody(),
                     "The correct response body is returned");
    }

    @Test
    @DisplayName("Tests that a 200 code is returned, with a non null body")
    public void testGetPublicationOkResponse() {

        int publicationId = 1;

        Publication publication = new Publication();
        publication.setPublicationId(publicationId);

        when(rulesService.getPublication(publicationId)).thenReturn(publication);

        ResponseEntity<Publication> publicationResponse = rootController.getPublication(publicationId);

        assertEquals(HttpStatus.OK, publicationResponse.getStatusCode(), "An OK response code is returned");
        assertNotNull(publicationResponse.getBody(), "At least one publication is returned");
    }

    @Test
    @DisplayName("Tests that the publication that is returned, matches the publication that was mocked")
    public void testGetPublicationContainsExpectedBody() {

        int publicationId = 1;

        Publication publication = new Publication();
        publication.setPublicationId(publicationId);

        when(rulesService.getPublication(publicationId)).thenReturn(publication);

        ResponseEntity<Publication> publicationResponse = rootController.getPublication(publicationId);

        Publication returnedPublication = publicationResponse.getBody();
        assertEquals(returnedPublication, publication, "The expected publication is returned");
    }

    @Test
    @DisplayName("Test that if the service throws an exception due to it not being found,"
        + " that it correctly gets passed through the controller (upwards to the exception handler)")
    public void testPublicationNotFoundException() {

        int publicationId = 2;
        String exceptionMessage = "Publication with ID " + publicationId + " has not been found";

        when(rulesService.getPublication(publicationId))
            .thenThrow(new PublicationNotFoundException(exceptionMessage));

        PublicationNotFoundException publicationNotFoundException =
            assertThrows(PublicationNotFoundException.class, () -> rootController.getPublication(publicationId),
                         "Test that the exception thrown is returned by the controller, "
                             + "and therefore sent up to the global exception handler");

        assertEquals(exceptionMessage, publicationNotFoundException.getMessage(), "Check that the message "
            + "in the exception is sent through");
    }

}
