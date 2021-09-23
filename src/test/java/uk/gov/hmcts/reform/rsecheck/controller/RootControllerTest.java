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
import uk.gov.hmcts.reform.pip.rules.model.Court;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
import uk.gov.hmcts.reform.pip.rules.rules.RulesService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    @DisplayName("Tests that a 200 code is returned, with a court list is returned for search input")
    public void testGetCourtList() {
        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("sometext");
        court.setCourtId(1);

        courts.add(court);

        when(rulesService.getCourtList("sometext")).thenReturn(courts);

        ResponseEntity<List<Court>> response = rootController.getCourtList("sometext");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "An OK response code is returned");
        assertEquals(courts, response.getBody(),
                     "The correct response body is returned");
    }

    @Test
    @DisplayName("Tests that a 200 code is returned, with a court list "
        + "is returned for an empty search input")
    public void testGetCourtListAll() {
        List<Court> courts = new ArrayList<>();
        Court court = new Court();
        court.setName("sometext");
        court.setCourtId(1);

        courts.add(court);

        when(rulesService.getCourtList("")).thenReturn(courts);

        ResponseEntity<List<Court>> response = rootController.getCourtList("");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "An OK response code is returned");
        assertEquals(courts, response.getBody(),
                     "The correct response body is returned");
    }


    @Test
    @DisplayName("Tests that a 200 code is returned, with the correct response message "
        + "and a court is returned and the hearings list is returned")
    public void testGetHearings() {
        Court court = new Court();
        court.setName("sometext");
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
        List<Court> courts = new ArrayList<>();
        courts.add(court);

        when(rulesService.getHearings(1)).thenReturn(court);

        ResponseEntity<Court> response = rootController.getHearings(1);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "An OK response code is returned");
        assertEquals(court, response.getBody(),
                     "The correct response body is returned");
        assertEquals(court.getHearingList().size(), 1,
                     "The correct response body is returned");
    }

}
