package uk.gov.hmcts.reform.pip.rules.rules.authorization;

import uk.gov.hmcts.reform.pip.rules.model.api.Publication;
import uk.gov.hmcts.reform.pip.rules.model.rules.AuthorizationRule;
import uk.gov.hmcts.reform.pip.rules.model.rules.Rule;

import java.util.Optional;

@AuthorizationRule
public class CourtIdRule implements Rule {

    @Override
    public Optional<Publication> process(Publication publication) {
        if (publication.getPublicationId() == 2) {
            return Optional.empty();
        }

        return Optional.of(publication);
    }
}
