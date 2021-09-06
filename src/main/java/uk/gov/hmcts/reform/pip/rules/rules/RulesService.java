package uk.gov.hmcts.reform.pip.rules.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.CourtNotFoundException;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.PublicationNotFoundException;
import uk.gov.hmcts.reform.pip.rules.model.CourtHearings;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
import uk.gov.hmcts.reform.pip.rules.model.Publication;
import uk.gov.hmcts.reform.pip.rules.repository.InMemoryRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A service to handle the business logic to validate whether a user has access to a publication,
 * and then retrieve it from the publication service.
 */
@Service
public class RulesService {

    @Autowired
    private InMemoryRepository inMemoryRepository;

    /**
     * Method to get a publication.
     * @param publicationId The ID of the publication to get.
     * @return The publication itself, if it passes the business rules and is found.
     */
    public Publication getPublication(Integer publicationId) {

        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(publicationId);

        if (publicationOptional.isPresent()) {
            return publicationOptional.get();
        } else {
            throw new PublicationNotFoundException(String.format("Publication with ID %s not found", publicationId));
        }
    }

    /**
     * Method to get a court.
     * @param courtId The ID of the publication to get.
     * @return The court itself, if it passes the business rules and is found.
     */
    public CourtHearings getCourt(Integer courtId) {

        Optional<CourtHearings> courtWithHearings = inMemoryRepository.getCourtHearings(courtId);
        if (courtWithHearings.isPresent()) {
            return courtWithHearings.get();
        } else {
            throw new CourtNotFoundException(String.format("Court with ID %s not found", courtId));
        }
    }

    /**
     * Method to get a list of courts.
     * @param input The input search to get.
     * @return The list of court itself, if it passes the business rules and is found.
     */
    public List<CourtHearings> getCourtList(String input) {

        List<CourtHearings> courts = inMemoryRepository.getCourtHearings();
        List<CourtHearings> courtsWithHearings = new ArrayList<>();
        if(!input.isEmpty())
        {
            String finalInput = input.toLowerCase();
            Stream<CourtHearings> streamCourt = courts.stream().filter(c -> c.getName().toLowerCase().contains(finalInput)
                || c.getJurisdiction().contains(finalInput)
                || c.getLocation().contains(finalInput));
            if (streamCourt != null) {
                courts = streamCourt
                    .collect(Collectors.toList());


            }
        }
        for (CourtHearings court : courts ) {
            Optional<CourtHearings> courtWithHearings = inMemoryRepository.getCourtHearings(court.getCourtId());
            if (courtWithHearings.isPresent())
                courtsWithHearings.add(courtWithHearings.get());
        }


        if (courtsWithHearings.size() > 0) {
            return courtsWithHearings;
        } else {
            throw new CourtNotFoundException(String.format("Courts with input search %s not found", input));
        }
    }


    /**
     * Method to get a hearings list.
     * @param courtId The ID of the publication to get.
     * @return The court itself, if it passes the business rules and is found.
     */
    public CourtHearings getHearings(Integer courtId, Date startDate, Date endDate) throws ParseException {


        Optional<CourtHearings> courtOptional = inMemoryRepository.getCourtHearings(courtId);
        CourtHearings court = courtOptional.get();

        List<Hearing> unsortedList = court.getHearingList();

        // filter hearingList for today date
        //TODO: The following code is commented out for testing purpose. It will be used at later stage

//        unsortedList = unsortedList.stream().filter(h -> h.getDate()
//            .after(startDate) && h.getDate().before(endDate))
//            .collect(Collectors.toList());

        // order by CourtNumber the hearings
        unsortedList.sort(Comparator.comparing(Hearing::getCourtNumber));
        court.setHearingList(unsortedList);

        if (courtOptional.isPresent()) {
            return court;
        } else {
            throw new PublicationNotFoundException(String.format("Court with ID %s not found", courtId));
        }
    }
}
