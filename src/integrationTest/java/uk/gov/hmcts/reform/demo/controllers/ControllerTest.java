package uk.gov.hmcts.reform.demo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import uk.gov.hmcts.reform.demo.model.CourtHearings;
import uk.gov.hmcts.reform.demo.model.Hearing;
import uk.gov.hmcts.reform.demo.model.Publication;

import java.util.List;

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

    @DisplayName("Should welcome upon root request with 200 response code")
    @Test
    public void publicationThatExistsTest() throws Exception {
        MvcResult response = mockMvc.perform(get("/publication/1")).andExpect(status().isOk()).andReturn();

        assertEquals(200, response.getResponse().getStatus(),
                     "Check that a 200 status is returned is returned");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Publication publication = objectMapper.readValue(
            response.getResponse().getContentAsByteArray(), Publication.class);

        assertEquals(1, publication.getPublicationId(), "Publication ID is 0");
        assertEquals(1, publication.getCourtHearingsList().size(), "The array contains a single publication");

        List<CourtHearings> courtHearingsList = publication.getCourtHearingsList();
        assertThat(courtHearingsList.get(0).getCourtId()).as("Get first court id").isEqualTo(1);

        List<Hearing> courtHearings = courtHearingsList.get(0).getHearingList();
        assertThat(courtHearings.get(0).getCourtId()).as("Get first court id").isEqualTo(1);
        assertThat(courtHearings.get(1).getCourtId()).as("Get second court id").isEqualTo(2);
    }


    @DisplayName("Should welcome upon root request with 200 response code")
    @Test
    public void publicationDoesNotExistTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/publication/2")).andExpect(status().isNotFound()).andReturn();
        assertEquals(404, mvcResult.getResponse().getStatus(), "Check that the status is a 404");
    }

}
