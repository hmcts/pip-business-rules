package uk.gov.hmcts.reform.pip.rules.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.pip.rules.model.Court;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
//
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A dummy repository, that returns courts and hearings randomly generated
 */
@Component
public class InMemoryRepository {

    private List<Court> listCourts;

    private List<Hearing>  listHearings;

    public InMemoryRepository() throws IOException {

        ObjectMapper om = new ObjectMapper();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        om.setDateFormat(df);
        File fileHearings = new File("src/main/java/uk/gov/hmcts/reform/pip/rules/repository/mocks/hearingsList.json");
        Hearing[] list = om.readValue(fileHearings, Hearing[].class);
        this.listHearings = Arrays.asList(list);

        File fileCourts = new File("src/main/java/uk/gov/hmcts/reform/pip/rules/repository/mocks/courtsAndHearingsCount.json");
        Court[] courts = om.readValue(fileCourts, Court[].class);

        this.listCourts = Arrays.asList(courts);

    }


    /**
     * Method to retrieve a court hearings using court ID.
     * @param courtId The ID of the court.
     * @return The court hearings to return, if found.
     */
    public Optional<Court> getCourtHearings(Integer courtId)
    {
        Court court = this.listCourts.stream()
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

    public List<Court> getListCourts() {
        return this.listCourts;
    }

}
