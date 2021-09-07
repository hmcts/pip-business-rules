package uk.gov.hmcts.reform.pip.rules.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Model which represents the list of hearings for a court.
 */
@Data
public class Court {

    /**
     * The ID of the court.
     */
    private Integer courtId;

    /**
     * The name of the court.
     */
    private String name;

    /**
     * The name of the jurisdiction
     */
    private String jurisdiction;

    /**
     * The name of the location
     */
    private String location;

    /**
     * Adding hearing to the hearing list
     * @param hearing
     */
    public void addHearing(Hearing hearing) {
        this.hearingList.add(hearing);
        this.hearings = this.hearingList.size();
    }

    /**
     * The list of hearings in the court.
     */
    private List<Hearing> hearingList = new ArrayList<>();


    public void setHearingList(List<Hearing> hearingList) {
        this.hearingList = hearingList;
        this.hearings = this.hearingList.size();
    }

    /**
     * Count of the hearings
     */
    private int hearings;
}

