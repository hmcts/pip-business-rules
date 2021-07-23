package uk.gov.hmcts.reform.rsecheck.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.pip.rules.controllers.RootController;
import uk.gov.hmcts.reform.pip.rules.model.Publication;
import uk.gov.hmcts.reform.pip.rules.rules.RulesService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RootControllerTest {

    @Mock
    RulesService rulesService;

    @InjectMocks
    RootController rootController;

    @Test
    public void testWelcomeMessage() {
        ResponseEntity<String> welcomeResponse = rootController.welcome();
        assertEquals(HttpStatus.OK, welcomeResponse.getStatusCode(), "An OK response code is returned");
        assertEquals("Welcome to pip-business-rules", welcomeResponse.getBody(),
                     "The correct response body is returned");
    }

    @Test
    public void testGetPublication() {

        int publicationId = 1;

        Publication publication = new Publication();
        publication.setPublicationId(publicationId);

        when(rulesService.getPublication(publicationId)).thenReturn(publication);

        ResponseEntity<Publication> publicationResponse = rootController.getPublication(publicationId);

        assertEquals(HttpStatus.OK, publicationResponse.getStatusCode(), "An OK response code is returned");
        assertNotNull(publicationResponse.getBody(), "At least one publication is returned");

        Publication returnedPublication = publicationResponse.getBody();
        assertEquals(returnedPublication, publication, "The expected publication is returned");
    }

}
