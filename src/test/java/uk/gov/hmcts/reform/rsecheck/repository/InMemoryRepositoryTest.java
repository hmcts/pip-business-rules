package uk.gov.hmcts.reform.rsecheck.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.pip.rules.model.Court;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
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
    @DisplayName("Tests that a court is returned with the correct ID and list of hearings")
    protected void courtThatExistsCourtReturnedOk() {
        Optional<Court> courtOptional = inMemoryRepository.getCourtHearings(1);
        assertTrue(courtOptional.isPresent(), "Check that a court has been returned");

        final int courtId = 1;
        Court court = courtOptional.get();
        assertEquals(courtId, court.getCourtId(),
                     "Check that the court ID is " + courtId);
        assertEquals(13, court.getHearingList().size(),
                     "Check that a single court hearing list is returned");
    }

    @Test
    @DisplayName("Tests that a hearings list within the response, contains a single court")
    protected void courtThatExistsHearingsListReturned() {
        Optional<Court> courtOptional = inMemoryRepository.getCourtHearings(1);
        assertTrue(courtOptional.isPresent(), "Check that a court has been returned");


        Court court = courtOptional.get();
        List<Hearing> courtHearingsList = court.getHearingList();
        assertThat(courtHearingsList.get(0).getCourtId()).as("Get first court id").isEqualTo(1);
    }

    @Test
    @DisplayName("Tests that a hearings list within the response, contains two hearings")
    protected void courtThatExistsHearingsReturnedOk() {
        Optional<Court> courtOptional = inMemoryRepository.getCourtHearings(1);
        assertTrue(courtOptional.isPresent(), "Check that a court has been returned");


        Court court = courtOptional.get();

        List<Hearing> courtHearingsList = court.getHearingList();


        assertThat(courtHearingsList.get(0).getHearingId()).as("Get first hearing id").isEqualTo(28);
        assertThat(courtHearingsList.get(1).getHearingId()).as("Get second hearing id").isEqualTo(295);

    }

    @Test
    @DisplayName("Tests that when a court does not exist, no court is returned")
    protected void courtDoesNotExistTest() {
        Optional<Court> courtOptional = inMemoryRepository.getCourtHearings(1232);
        assertTrue(courtOptional.isEmpty(), "Check that no publication is returned");
    }


}
