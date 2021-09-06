package uk.gov.hmcts.reform.rsecheck.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.pip.rules.model.CourtHearings;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
import uk.gov.hmcts.reform.pip.rules.model.Publication;
import uk.gov.hmcts.reform.pip.rules.repository.InMemoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryRepositoryTest {

    InMemoryRepository inMemoryRepository = new InMemoryRepository();

    public InMemoryRepositoryTest() throws IOException {
    }

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

    @Test
    @DisplayName("Tests that a court is returned with the correct ID and list of hearings")
    public void courtThatExistsCourtReturnedOk() {
        Optional<CourtHearings> courtOptional = inMemoryRepository.getCourtHearings(1);
        assertTrue(courtOptional.isPresent(), "Check that a court has been returned");

        final int courtId = 1;
        CourtHearings court = courtOptional.get();
        assertEquals(courtId, court.getCourtId(),
                     "Check that the court ID is " + courtId);
        assertEquals(13, court.getHearingList().size(),
                     "Check that a single court hearing list is returned");
    }

    @Test
    @DisplayName("Tests that a hearings list within the response, contains a single court")
    public void courtThatExistsHearingsListReturned() {
        Optional<CourtHearings> courtOptional = inMemoryRepository.getCourtHearings(1);
        assertTrue(courtOptional.isPresent(), "Check that a court has been returned");


        CourtHearings court = courtOptional.get();
        List<Hearing> courtHearingsList = court.getHearingList();
        assertThat(courtHearingsList.get(0).getCourtId()).as("Get first court id").isEqualTo(1);
    }

    @Test
    @DisplayName("Tests that a hearings list within the response, contains two hearings")
    public void courtThatExistsHearingsReturnedOk() {
        Optional<CourtHearings> courtOptional = inMemoryRepository.getCourtHearings(1);
        assertTrue(courtOptional.isPresent(), "Check that a court has been returned");


        CourtHearings court = courtOptional.get();

        List<Hearing> courtHearingsList = court.getHearingList();


        assertThat(courtHearingsList.get(0).getHearingId()).as("Get first hearing id").isEqualTo(28);
        assertThat(courtHearingsList.get(1).getHearingId()).as("Get second hearing id").isEqualTo(295);

    }

    @Test
    @DisplayName("Tests that when a court does not exist, no court is returned")
    public void courtDoesNotExistTest() {
        Optional<CourtHearings> courtOptional = inMemoryRepository.getCourtHearings(1232);
        assertTrue(courtOptional.isEmpty(), "Check that no publication is returned");
    }


}
