package uk.gov.hmcts.reform.rsecheck.repository;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.demo.model.CourtHearings;
import uk.gov.hmcts.reform.demo.model.Hearing;
import uk.gov.hmcts.reform.demo.model.Publication;
import uk.gov.hmcts.reform.demo.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryRepositoryTest {

    InMemoryRepository inMemoryRepository = new InMemoryRepository();

    @Test
    public void getPublicationPresentTest() {
        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(1);
        assertTrue(publicationOptional.isPresent());

        Publication publication = publicationOptional.get();
        assertEquals(1, publication.getPublicationId());
        assertEquals(1, publication.getCourtHearingsList().size());

        List<CourtHearings> courtHearingsList = publication.getCourtHearingsList();
        assertEquals(1, courtHearingsList.get(0).getCourtId());

        List<Hearing> courtHearings = courtHearingsList.get(0).getHearingList();
        assertEquals(1, courtHearings.get(0).getCourtId());
        assertEquals(2, courtHearings.get(1).getCourtId());
    }

    @Test
    public void getPublicationNotPresentTest() {
        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(2);
        assertTrue(publicationOptional.isEmpty());
    }


}
