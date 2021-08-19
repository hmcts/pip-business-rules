package uk.gov.hmcts.reform.pip.rules.model.rules;

import uk.gov.hmcts.reform.pip.rules.model.api.Publication;

import java.util.Optional;

/**
 * This class captures the construct for a rule
 *
 * It contains two key functions, config, and processing
 *
 */
public interface Rule {

    /**
     * This method deals with the processing of the publication
     * @param publication
     */
    public Optional<Publication> process(Publication publication);


}
