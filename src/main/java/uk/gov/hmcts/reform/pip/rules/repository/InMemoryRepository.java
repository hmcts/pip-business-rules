package uk.gov.hmcts.reform.pip.rules.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.pip.rules.model.Court;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A dummy repository, that returns courts and hearings randomly generated.
 */
@Component
public class InMemoryRepository {

    private List<Court> listCourts;

    private List<Hearing>  listHearings;

    private static final String mockPath = "src/main/java/uk/gov/hmcts/reform/pip/rules/repository/mocks/";

    public InMemoryRepository() throws IOException {

        ObjectMapper om = new ObjectMapper().setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));

        File fileHearings = new File(mockPath + "hearingsList.json");
        Hearing[] list = om.readValue(fileHearings, Hearing[].class);
        this.listHearings = Arrays.asList(list);

        File fileCourts = new File(mockPath + "courtsAndHearingsCount.json");
        Court[] courts = om.readValue(fileCourts, Court[].class);

        this.listCourts = Arrays.asList(courts);

    }


    /**
     * Method to retrieve a court hearings using court ID.
     * @param courtId The ID of the court.
     * @return The court hearings to return, if found.
     */
    public Optional<Court> getCourtHearings(Integer courtId) {
        Court court = this.listCourts.stream()
            .filter(c -> courtId.equals(c.getCourtId()))
            .findAny()
            .orElse(null);

        if (court != null) {
            List<Hearing> hearings = this.getHearings(courtId);
            court.setHearingList(hearings);
            return Optional.of(court);
        } else {
            return Optional.empty();
        }
    }

    private List<Hearing> getHearings(Integer courtId) {
        return this.listHearings.stream()
            .filter(h -> h.getCourtId().equals(courtId))
            .collect(Collectors.toList());
    }

    public List<Court> getListCourts() {
        return this.listCourts;
    }

}
