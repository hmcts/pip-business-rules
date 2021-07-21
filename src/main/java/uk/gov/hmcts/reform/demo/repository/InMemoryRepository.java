package uk.gov.hmcts.reform.demo.repository;

import org.springframework.stereotype.Component;
import uk.gov.hmcts.reform.demo.model.CourtHearings;
import uk.gov.hmcts.reform.demo.model.Hearing;
import uk.gov.hmcts.reform.demo.model.Publication;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A dummy repository, that returns a dummy publication if the ID is 1, else
 * it will return an empty optional.
 */
@Component
public class InMemoryRepository {

    private static final Integer AVAILABLE_PUBLICATION = 1;

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
            hearing.setDate(LocalDate.now());
            hearing.setTime(LocalTime.now());
            hearing.setJudge("This is a judge");
            hearing.setPlatform("This is a platform");
            hearing.setCaseNumber("This is a case number");
            hearing.setCaseName("This is a case name");

            Hearing otherHearing = new Hearing();
            otherHearing.setHearingId(2);
            otherHearing.setCourtId(1);
            otherHearing.setDate(LocalDate.now());
            otherHearing.setTime(LocalTime.now());
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



}
