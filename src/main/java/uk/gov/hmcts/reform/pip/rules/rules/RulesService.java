package uk.gov.hmcts.reform.pip.rules.rules;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.pip.rules.errorhandling.exceptions.PublicationNotFoundException;
import uk.gov.hmcts.reform.pip.rules.model.Publication;
import uk.gov.hmcts.reform.pip.rules.repository.InMemoryRepository;

import java.util.Optional;

/**
 * A service to handle the business logic to validate whether a user has access to a publication,
 * and then retrieve it from the publication service.
 */
@Service
public class RulesService {

    @Autowired
    private InMemoryRepository inMemoryRepository;

    /**
     * Method to get a publication.
     * @param publicationId The ID of the publication to get.
     * @return The publication itself, if it passes the business rules and is found.
     */
    public Publication getPublication(Integer publicationId) {

        Optional<Publication> publicationOptional = inMemoryRepository.getPublication(publicationId);

        if (publicationOptional.isPresent()) {
            return publicationOptional.get();
        } else {
            throw new PublicationNotFoundException(String.format("Publication with ID %s not found", publicationId));
        }
    }
}
