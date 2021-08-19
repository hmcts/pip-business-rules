package uk.gov.hmcts.reform.pip.rules.rules.authorization;

import uk.gov.hmcts.reform.pip.rules.model.api.Publication;
import uk.gov.hmcts.reform.pip.rules.model.rules.RedactionRule;
import uk.gov.hmcts.reform.pip.rules.model.rules.Rule;

import java.util.Optional;

@RedactionRule
public class CourtNameRule implements Rule {

    @Override
    public Optional<Publication> process(Publication publication) {
        if (publication.getPublicationId() == 1) {
            return Optional.empty();
        }

        return Optional.of(publication);
    }
}
