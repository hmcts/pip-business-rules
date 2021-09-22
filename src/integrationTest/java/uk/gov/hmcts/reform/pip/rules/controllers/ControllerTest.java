package uk.gov.hmcts.reform.pip.rules.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.pip.rules.model.Court;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("Should welcome upon root request with 200 response code")
    @Test
    public void welcomeRootEndpoint() throws Exception {
        MvcResult response = mockMvc.perform(get("/")).andExpect(status().isOk()).andReturn();

        assertThat(response.getResponse().getContentAsString()).startsWith("Welcome");
    }


    @DisplayName("Should return a 404, with no court")
    @Test
    public void courtDoesNotExistTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/court/1232")).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus(), "Check that the status is a 404");
    }


    @DisplayName("Should return a 200, and a court list containing a list of hearings for each court")
    @Test
    public void courtListThatExistsHearingsTest() throws Exception {
        String courtname = "Abergavenny Magistrates' Court";
        MvcResult response = mockMvc.perform(get("/api/courtlist/" + courtname)).andReturn();

        assertEquals(200, response.getResponse().getStatus(),
                     "Check that a 200 status is returned is returned");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Court[] courts = objectMapper.readValue(response.getResponse().getContentAsByteArray(), Court[].class);

        final int courtID = 1;
        assertEquals(courtID, courts[0].getCourtId(), "Court ID is " + courtID);
        assertEquals(13, courts[0].getHearingList().size(), "The array contains a list of hearings");
    }

    @DisplayName("Should return a 404, with no courts")
    @Test
    public void courtListDoesNotExistTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/courtlist/foobar")).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus(), "Check that the status is a 404");
    }

    @DisplayName("Should return a 200, and all court list ")
    @Test
    public void courtListAllTest() throws Exception {
        MvcResult response = mockMvc.perform(get("/api/courtlistall/")).andReturn();

        assertEquals(200, response.getResponse().getStatus(),
                     "Check that a 200 status is returned is returned");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Court[] courts = objectMapper.readValue(response.getResponse().getContentAsByteArray(), Court[].class);

        final int courtID = 1;
        assertEquals(courtID, courts[0].getCourtId(), "Court ID is " + courtID);
        assertEquals(583, courts.length, "The array contains a list of courts");
    }

    @DisplayName("Should return a 200, and all hearing list for the court id passed and the today date")
    @Test
    public void hearingListForTodayDateTest() throws Exception {
        MvcResult response = mockMvc.perform(get("/api/hearings/1")).andReturn();

        assertEquals(200, response.getResponse().getStatus(),
                     "Check that a 200 status is returned is returned");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Court court = objectMapper.readValue(response.getResponse().getContentAsByteArray(), Court.class);

        final int courtID = 1;
        assertEquals(courtID, court.getCourtId(), "Court ID is " + courtID);
    }

}
