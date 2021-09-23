package uk.gov.hmcts.reform.pip.rules.rules;

import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.CourtNotFoundException;
import uk.gov.hmcts.reform.pip.rules.model.Court;
import uk.gov.hmcts.reform.pip.rules.model.Hearing;
import uk.gov.hmcts.reform.pip.rules.repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * A service to handle the business logic to validate whether a user has access to a publication,
 * and then retrieve it from the publication service.
 */
@Service
public class RulesService {

    @Autowired
    private InMemoryRepository inMemoryRepository;

    /**
     * Method to get a court.
     * @param courtId The ID of the publication to get.
     * @return The court itself, if it passes the business rules and is found.
     */
    public Court getCourt(Integer courtId) {
        Optional<Court> courtWithHearings = inMemoryRepository.getCourtHearings(courtId);
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
    public List<Court> getCourtList(String input) {
        List<Court> courts = inMemoryRepository.getListCourts();
        List<Court> courtsWithHearings = new ArrayList<>();
        if (!input.isEmpty()) {
            String finalInput = input.toLowerCase();
            courts = courts.stream()
                .filter(c -> !Strings.isNullOrEmpty(c.getName()) && c.getName().toLowerCase().contains(finalInput)
                || !Strings.isNullOrEmpty(c.getJurisdiction()) && c.getJurisdiction().toLowerCase().contains(finalInput)
                || !Strings.isNullOrEmpty(c.getLocation()) && c.getLocation().toLowerCase().contains(finalInput))
                .collect(Collectors.toList());
        }

        for (Court court: courts) {
            Optional<Court> courtWithHearings = inMemoryRepository.getCourtHearings(court.getCourtId());
            if (courtWithHearings.isPresent()) {
                courtsWithHearings.add(courtWithHearings.get());
            }
        }

        if (!courtsWithHearings.isEmpty()) {
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
    public Court getHearings(Integer courtId) {
        Optional<Court> courtOptional = inMemoryRepository.getCourtHearings(courtId);
        if (courtOptional.isPresent()) {
            Court court = courtOptional.get();

            List<Hearing> unsortedList = court.getHearingList();

            // filter hearingList for today date
            //TODO: The following code is commented out for testing purpose. It will be used at later stage
            //unsortedList = unsortedList.stream().filter(h -> h.getDate()
            //    .after(startDate) && h.getDate().before(endDate))
            //    .collect(Collectors.toList());

            // order by CourtNumber the hearings
            unsortedList.sort(Comparator.comparing(Hearing::getCourtNumber));
            court.setHearingList(unsortedList);

            return court;
        } else {
            throw new CourtNotFoundException(String.format("Court with ID %s not found", courtId));
        }
    }
}
