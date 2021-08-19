package uk.gov.hmcts.reform.pip.rules.rules.processing;

import uk.gov.hmcts.reform.pip.rules.model.api.Publication;
import uk.gov.hmcts.reform.pip.rules.model.rules.ProcessRule;
import uk.gov.hmcts.reform.pip.rules.model.rules.Rule;
import uk.gov.hmcts.reform.pip.rules.model.rules.AuthorizationRule;

import java.util.Optional;

@ProcessRule
public class IdPrinting implements Rule {

    @Override
    public Optional<Publication> process(Publication publication) {

        System.out.println(publication.getPublicationId());

        return Optional.of(publication);
    }
}
