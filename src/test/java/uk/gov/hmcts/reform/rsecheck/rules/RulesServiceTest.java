package uk.gov.hmcts.reform.rsecheck.rules;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.CourtNotFoundException;
import uk.gov.hmcts.reform.pip.rules.model.Court;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
import uk.gov.hmcts.reform.pip.rules.repository.InMemoryRepository;
import uk.gov.hmcts.reform.pip.rules.rules.RulesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    @DisplayName("Tests that a court is correct passed through from the rules service")
    protected void testSuccessfulCourtGet() {

        Court court = new Court();
        court.setCourtId(1);

        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.of(court));

        Court returnedCourt = rulesService.getCourt(1);

        assertEquals(court, returnedCourt,
                     "Check that the returned court matches the created court");
    }

    @Test
    @DisplayName("Tests that when a court is not found via the rules service, then an exception is thrown")
    protected void testUnsuccessfulCourtGet() {

        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.empty());

        assertThrows(CourtNotFoundException.class, () -> rulesService.getCourt(1),
                     "Check that an exception is thrown if the publication isn't found");
    }

    @Test
    @DisplayName("Tests that a list of courts with hearings "
        + "for each court is correct passed through from the rules service and from the given input search")
    protected void testSuccessfulCourtListWithInputGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("anytext");
        court.setCourtId(1);

        courts.add(court);

        when(inMemoryRepository.getListCourts()).thenReturn(courts);
        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.of(court));

        List<Court> returnedCourt = rulesService.getCourtList("anytext");

        assertEquals(courts, returnedCourt,
                     "Check that the returned court matches the created court");
    }

    @Test
    @DisplayName("Tests that a list of courts with hearings "
        + "for each court is correct passed through from the rules service and from the given input search")
    protected void testSuccessfulCourtListWithInputJurisdictionGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setJurisdiction("anytext");
        court.setCourtId(1);

        courts.add(court);

        when(inMemoryRepository.getListCourts()).thenReturn(courts);
        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.of(court));

        List<Court> returnedCourt = rulesService.getCourtList("anytext");

        assertEquals(courts, returnedCourt,
                     "Check that the returned court matches the created court");
    }

    @Test
    @DisplayName("Tests that a list of courts with hearings "
        + "for each court is correct passed through from the rules service and from the given input search")
    protected void testSuccessfulCourtListWithInputLocationGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setLocation("anytext");
        court.setCourtId(1);

        courts.add(court);

        when(inMemoryRepository.getListCourts()).thenReturn(courts);
        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.of(court));

        List<Court> returnedCourt = rulesService.getCourtList("anytext");

        assertEquals(courts, returnedCourt,
                     "Check that the returned court matches the created court");
    }

    @Test
    @DisplayName("Tests that when a court is not found via the rules service, then an exception is thrown")
    protected void testUnsuccessfulCourtListGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("anyothertext");
        court.setCourtId(2);
        courts.add(court);

        when(inMemoryRepository.getListCourts()).thenReturn(courts);

        assertThrows(CourtNotFoundException.class, () -> rulesService.getCourtList("anytext"),
                    "Check that an exception is thrown if the court isn't found");
    }

    @Test
    @DisplayName("Tests that a list of courts with hearings "
        + "for each court is correct passed through from the rules service and from the given input search empty")
    protected void testSuccessfulCourtListAllWithInputEmptyGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("anytext");
        court.setCourtId(1);

        courts.add(court);

        when(inMemoryRepository.getListCourts()).thenReturn(courts);
        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.of(court));

        List<Court> returnedCourt = rulesService.getCourtList("");


        assertEquals(courts, returnedCourt,
                     "Check that the returned courts matches the created courts");
    }

    @Test
    @DisplayName("Tests that a court with hearings "
        + " is correct passed through from the rules service and from the passed court id")
    protected void testSuccessfulCourtListWithHearingsGet() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setLocation("anytext");
        court.setCourtId(1);

        Hearing hearing = new Hearing();
        hearing.setHearingId(1);
        hearing.setCaseName("some case name");
        hearing.setJudge("some judge");
        hearing.setCaseNumber("123-456");
        hearing.setDate(new Date());

        List<Hearing> hearings = new ArrayList<>();
        hearings.add(hearing);
        court.setHearingList(hearings);
        courts.add(court);

        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.of(court));

        Court returnedCourt = rulesService.getHearings(1);

        assertEquals(court, returnedCourt,
                     "Check that the returned court matches the created court");
    }

    @Test
    @DisplayName("Tests that when a court is not found via the rules service, then an exception is thrown")
    protected void testUnsuccessfulGetHearings() {

        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("anyothertext");
        court.setCourtId(2);
        courts.add(court);

        when(inMemoryRepository.getCourtHearings(1)).thenReturn(Optional.empty());

        assertThrows(CourtNotFoundException.class, () -> rulesService.getHearings(1),
                     "Check that an exception is thrown if the court isn't found");
    }
}
