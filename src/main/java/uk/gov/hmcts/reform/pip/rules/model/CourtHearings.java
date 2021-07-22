package uk.gov.hmcts.reform.pip.rules.model;

import lombok.Data;

import java.util.List;

/**
 * Model which represents the list of hearings for a court.
 */
@Data
public class CourtHearings {

    /**
     * The ID of the court.
     */
    private Integer courtId;

    /**
     * The name of the court.
     */
    private String name;

    /**
     * The list of hearings in the court.
     */
    private List<Hearing> hearingList;

}
