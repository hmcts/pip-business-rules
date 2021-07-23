package uk.gov.hmcts.reform.rsecheck.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.pip.rules.model.CourtHearings;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
import uk.gov.hmcts.reform.pip.rules.model.Publication;
import uk.gov.hmcts.reform.pip.rules.repository.InMemoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryRepositoryTest {

    InMemoryRepository inMemoryRepository = new InMemoryRepository();

    @Test
    @DisplayName("Tests that a publication is returned with the correct ID and list")
    public void publicationThatExistsPublicationReturnedOk() {
        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(1);
        assertTrue(publicationOptional.isPresent(), "Check that a publication has been returned");

        final int publicationId = 1;
        Publication publication = publicationOptional.get();
        assertEquals(publicationId, publication.getPublicationId(),
                     "Check that the publication ID is " + publicationId);
        assertEquals(1, publication.getCourtHearingsList().size(),
                     "Check that a single court hearing list is returned");
    }

    @Test
    @DisplayName("Tests that the court list within the response, contains a single court")
    public void publicationThatExistsHearingsListReturned() {
        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(1);
        assertTrue(publicationOptional.isPresent(), "Check that a publication has been returned");

        Publication publication = publicationOptional.get();

        List<CourtHearings> courtHearingsList = publication.getCourtHearingsList();
        assertThat(courtHearingsList.get(0).getCourtId()).as("Get first court id").isEqualTo(1);
    }

    @Test
    @DisplayName("Tests that the hearings list within the response, contains two hearings")
    public void publicationThatExistsHearingsReturnedOk() {
        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(1);
        assertTrue(publicationOptional.isPresent(), "Check that a publication has been returned");

        Publication publication = publicationOptional.get();

        List<CourtHearings> courtHearingsList = publication.getCourtHearingsList();

        List<Hearing> courtHearings = courtHearingsList.get(0).getHearingList();
        assertThat(courtHearings.get(0).getHearingId()).as("Get first hearing id").isEqualTo(1);
        assertThat(courtHearings.get(1).getHearingId()).as("Get second hearing id").isEqualTo(2);
    }

    @Test
    @DisplayName("Tests that when a publication does not exist, no publication is returned")
    public void publicationDoesNotExistTest() {
        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(2);
        assertTrue(publicationOptional.isEmpty(), "Check that no publication is returned");
    }


}
