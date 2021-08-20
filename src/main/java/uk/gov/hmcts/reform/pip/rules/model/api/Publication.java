package uk.gov.hmcts.reform.pip.rules.model.api;

import lombok.Data;

import java.util.List;

/**
 * Model which represents a publication.
 */
@Data
public class Publication {

    /**
     * The publication ID, that represents this piece of data.
     */
    private Integer publicationId;

    /**
     * The list of Court Hearings.
     */
    private List<CourtHearings> courtHearingsList;

}