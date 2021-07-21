package uk.gov.hmcts.reform.rsecheck.rules;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.demo.errorhandling.exceptions.PublicationNotFoundException;
import uk.gov.hmcts.reform.demo.model.Publication;
import uk.gov.hmcts.reform.demo.repository.InMemoryRepository;
import uk.gov.hmcts.reform.demo.rules.RulesService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RulesServiceTest {

    @Mock
    private InMemoryRepository inMemoryRepository;

    @InjectMocks
    private RulesService rulesService;

    @Test
    public void testSuccessfulPublicationGet() {

        Publication publication = new Publication();
        publication.setPublicationId(1);

        when(inMemoryRepository.getPublication(1)).thenReturn(Optional.of(publication));

        Publication returnedPublication = rulesService.getPublication(1);

        assertEquals(publication, returnedPublication);
    }

    @Test
    public void testUnsuccessfulPublicationGet() {

        when (inMemoryRepository.getPublication(1)).thenReturn(Optional.empty());

        assertThrows(PublicationNotFoundException.class, () -> rulesService.getPublication(1));
    }

 }
