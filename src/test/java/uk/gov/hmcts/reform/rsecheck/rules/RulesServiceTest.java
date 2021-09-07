package uk.gov.hmcts.reform.rsecheck.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.CourtNotFoundException;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.PublicationNotFoundException;
import uk.gov.hmcts.reform.pip.rules.model.Court;
import uk.gov.hmcts.reform.pip.rules.model.Publication;
import uk.gov.hmcts.reform.pip.rules.repository.InMemoryRepository;
import uk.gov.hmcts.reform.pip.rules.rules.RulesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RulesServiceTest {

    @Mock
    private InMemoryRepository inMemoryRepository;

    @InjectMocks
    private RulesService rulesService;

    @Test
    @DisplayName("Tests that a publication is correct passed through from the rules service")
    public void testSuccessfulPublicationGet() {

        Publication publication = new Publication();
        publication.setPublicationId(1);

        when(inMemoryRepository.getPublication(1)).thenReturn(Optional.of(publication));

        Publication returnedPublication = rulesService.getPublication(1);

        assertEquals(publication, returnedPublication,
                     "Check that the returned publication matches the created publication");
    }

    @Test
    @DisplayName("Tests that when a publication is not found via the rules service, then an exception is thrown")
    public void testUnsuccessfulPublicationGet() {

        when(inMemoryRepository.getPublication(1)).thenReturn(Optional.empty());

        assertThrows(PublicationNotFoundException.class, () -> rulesService.getPublication(1),
                     "Check that an exception is thrown if the publication isn't found");
    }

    @Test
    @DisplayName("Tests that a court is correct passed through from the rules service")
    public void testSuccessfulCourtGet() {

        Court court = new Court();
        court.setCourtId(1);

        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.of(court));

        Court returnedCourt = rulesService.getCourt(1);

        assertEquals(court, returnedCourt,
                     "Check that the returned publication matches the created publication");
    }

    @Test
    @DisplayName("Tests that when a court is not found via the rules service, then an exception is thrown")
    public void testUnsuccessfulCourtGet() {

        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.empty());

        assertThrows(CourtNotFoundException.class, () -> rulesService.getCourt(1),
                     "Check that an exception is thrown if the publication isn't found");
    }

    @Test
    @DisplayName("Tests that a list of courts with hearings for each court is correct passed through from the rules service and from the given input search")
    public void testSuccessfulCourtListWithInputGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("anytext");
        court.setCourtId(1);

        courts.add(court);

        when(inMemoryRepository.getListCourts()).thenReturn(courts);
        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.of(court));

        List<Court> returnedCourt = rulesService.getCourtList("anytext");

        assertEquals(courts, returnedCourt,
                     "Check that the returned publication matches the created publication");
    }

    @Test
    @DisplayName("Tests that when a court is not found via the rules service, then an exception is thrown")
    public void testUnsuccessfulCourtListGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("anyothertext");
        court.setCourtId(2);
        courts.add(court);

        when(inMemoryRepository.getCourtHearings(2)).thenReturn(Optional.empty());
        when(inMemoryRepository.getListCourts()).thenReturn(courts);

        assertThrows(CourtNotFoundException.class, () -> rulesService.getCourtList("anytext"),
                     "Check that an exception is thrown if the court isn't found");
    }

    @Test
    @DisplayName("Tests that a list of courts with hearings for each court is correct passed through from the rules service and from the given input search empty")
    public void testSuccessfulCourtListAllWithInputEmptyGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("anytext");
        court.setCourtId(1);

        courts.add(court);

        when(inMemoryRepository.getListCourts()).thenReturn(courts);


        List<Court> returnedCourt = rulesService.getCourtList("");

        assertEquals(courts, returnedCourt,
                     "Check that the returned courts matches the created courts");
        verify(inMemoryRepository, never()).getCourtHearings(1);
    }

}

