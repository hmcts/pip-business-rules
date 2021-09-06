package uk.gov.hmcts.reform.pip.rules.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.applicationinsights.core.dependencies.google.gson.JsonArray;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import uk.gov.hmcts.reform.pip.rules.model.CourtHearings;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
import uk.gov.hmcts.reform.pip.rules.model.Publication;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A dummy repository, that returns a dummy publication if the ID is 1, else
 * it will return an empty optional.
 */
@Component
public class InMemoryRepository {

    private static final Integer AVAILABLE_PUBLICATION = 1;



    public InMemoryRepository() throws IOException {

        ObjectMapper om = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        om.setDateFormat(df);
        File file = new File("src/main/java/uk/gov/hmcts/reform/pip/rules/repository/mocks/hearingsList.json");
        Hearing[] list = om.readValue(file, Hearing[].class);
        this.listHearings = Arrays.asList(list);

        file = new File("src/main/java/uk/gov/hmcts/reform/pip/rules/repository/mocks/courtsAndHearingsCount.json");
        CourtHearings[] courts = om.readValue(file, CourtHearings[].class);

        this.courtHearings = Arrays.asList(courts);

    }


    /**
     * Method to retrieve a publication using it's ID.
     * @param publicationId The ID of the publication.
     * @return The publication to return, if found.
     */
    public Optional<Publication> getPublication(Integer publicationId) {

        if (publicationId.equals(AVAILABLE_PUBLICATION)) {
            CourtHearings courtHearings = new CourtHearings();
            courtHearings.setCourtId(1);
            courtHearings.setName("This is a court");

            Hearing hearing = new Hearing();
            hearing.setHearingId(1);
            hearing.setCourtId(1);
//            hearing.setDate(LocalDate.now());
//            hearing.setTime(LocalTime.now());
            hearing.setJudge("This is a judge");
            hearing.setPlatform("This is a platform");
            hearing.setCaseNumber("This is a case number");
            hearing.setCaseName("This is a case name");

            Hearing otherHearing = new Hearing();
            otherHearing.setHearingId(2);
            otherHearing.setCourtId(1);
//            otherHearing.setDate(LocalDate.now());
//            otherHearing.setTime(LocalTime.now());
            otherHearing.setJudge("This is a judge");
            otherHearing.setPlatform("This is a platform");
            otherHearing.setCaseNumber("This is a case number");
            otherHearing.setCaseName("This is a case name");

            List<Hearing> hearings = new ArrayList<>();
            hearings.add(hearing);
            hearings.add(otherHearing);
            courtHearings.setHearingList(hearings);

            Publication publication = new Publication();
            publication.setPublicationId(1);

            List<CourtHearings> courtHearingsList = new ArrayList<>();
            courtHearingsList.add(courtHearings);

            publication.setCourtHearingsList(new ArrayList<>(courtHearingsList));
            return Optional.of(publication);
        }

        return Optional.empty();
    }


    /**
     * Method to retrieve a court hearings using court ID.
     * @param courtId The ID of the court.
     * @return The court hearings to return, if found.
     */
    public Optional<CourtHearings> getCourtHearings(Integer courtId)
    {
        CourtHearings court = this.courtHearings.stream()
            .filter(c -> courtId.equals(c.getCourtId()))
            .findAny()
            .orElse(null);

        List<Hearing> listHearings = this.listHearings.stream()
            .filter(h -> h.getCourtId() == courtId)
            .collect(Collectors.toList());

        if (court != null) {
            court.setHearingList(listHearings);
            return Optional.of(court);
        }
        else {
            return Optional.empty();
        }


    }

    public List<CourtHearings> getCourtHearings() {
        return this.courtHearings;
    }

    private List<CourtHearings> courtHearings;

    private List<Hearing>  listHearings;

}
