package uk.gov.hmcts.reform.pip.rules.rules.redaction;

import org.springframework.beans.factory.annotation.Autowired;
import uk.gov.hmcts.reform.pip.rules.model.api.Publication;
import uk.gov.hmcts.reform.pip.rules.model.rules.RedactionRule;
import uk.gov.hmcts.reform.pip.rules.model.rules.Rule;

import java.util.Optional;

@RedactionRule
public class NameRedactor implements Rule {

    @Autowired
    private NameRedactorConfiguration nameRedactorConfiguration;

    @Override
    public Optional<Publication> process(Publication publication) {

        publication.getCourtHearingsList().forEach(courtHearings -> {
          if (nameRedactorConfiguration.getCourtName().equals(courtHearings.getName())) {
            courtHearings.setName(nameRedactorConfiguration.getRedactionValue());
          }
        });

        return Optional.of(publication);
    }
}
