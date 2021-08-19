package uk.gov.hmcts.reform.pip.rules.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.PublicationNotFoundException;
import uk.gov.hmcts.reform.pip.rules.model.api.Publication;
import uk.gov.hmcts.reform.pip.rules.model.rules.ProcessRule;
import uk.gov.hmcts.reform.pip.rules.model.rules.RedactionRule;
import uk.gov.hmcts.reform.pip.rules.model.rules.Rule;
import uk.gov.hmcts.reform.pip.rules.model.rules.AuthorizationRule;
import uk.gov.hmcts.reform.pip.rules.repository.InMemoryRepository;

import java.util.*;

/**
 * A service to handle the business logic to validate whether a user has access to a publication,
 * and then retrieve it from the publication service.
 */
@Service
public class RulesService {

    @Autowired
    private InMemoryRepository inMemoryRepository;

    List<Rule> authorizationRules = new ArrayList<>();

    List<Rule> redactionRules = new ArrayList<>();

    List<Rule> processingRules = new ArrayList<>();


    public RulesService(ApplicationContext applicationContext) {
        applicationContext.getBeansWithAnnotation(AuthorizationRule.class).values().forEach(object -> {
            authorizationRules.add((Rule) object);
        });

        applicationContext.getBeansWithAnnotation(RedactionRule.class).values().forEach(object -> {
            redactionRules.add((Rule) object);
        });

        applicationContext.getBeansWithAnnotation(ProcessRule.class).values().forEach(object -> {
            processingRules.add((Rule) object);
        });
    }
    /**
     * Method to get a publication.
     * @param publicationId The ID of the publication to get.
     * @return The publication itself, if it passes the business rules and is found.
     */
    public Publication getPublication(Integer publicationId) {

        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(publicationId);

        if (publicationOptional.isPresent()) {
            Optional<Publication> processedPublication = processPublication(publicationOptional.get());
            if (processedPublication.isPresent()) {
                return processedPublication.get();
            } else {
                throw new PublicationNotFoundException(String.format(
                    "Publication with ID %s not found",
                    publicationId
                ));
            }
        } else {
            throw new PublicationNotFoundException(String.format("Publication with ID %s not found", publicationId));

        }
    }

    /**
     * Process the publication, by running it through each business rule
     *
     * If any of the rules filter out the business rule, then an Empty optional
     * will be returned
     *
     * Otherwise, the process publication will be returned
     * @param publication The publication to be processed
     * @return The returned publication, or an empty optional if it has been filtered out
     */
    private Optional<Publication> processPublication(Publication publication) {

        //Processing Rules
        for (Rule rule : processingRules) {
            rule.process(publication);
        }

        //Authorization rules
        for (Rule rule : authorizationRules) {
            if (rule.process(publication).isEmpty()) {
                return Optional.empty();
            }
        }

        //Redaction rules
        for (Rule rule : redactionRules) {
            rule.process(publication);
        }

        return Optional.of(publication);
    }
}
