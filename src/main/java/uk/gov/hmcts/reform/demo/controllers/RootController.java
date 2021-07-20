package uk.gov.hmcts.reform.demo.controllers;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Default endpoints per application.
 */
@RestController
public class RootController {

    /**
     * Root GET endpoint.
     *
     * <p>Azure application service has a hidden feature of making requests to root endpoint when
     * "Always On" is turned on.
     * This is the endpoint to deal with that and therefore silence the unnecessary 404s as a response code.
     *
     * @return Welcome message from the service.
     */
    @GetMapping("/")
    public ResponseEntity<String> welcome() {
        return ok("Welcome to pip-business-rules");
    }

    /**
     * This endpoint returns a publication, based on it's ID.
     *
     * It will apply the business rules against the publication and requesting user,
     * to understand whether they should see the requested publication.
     *
     * If the user does not have permission to see the resource, or the resource is not found,
     * then a 404 will be returned instead.
     *
     * @param id The ID of the publication to search for.
     * @return The publication, if found.
     */
    @GetMapping("/publication/{id}")
    public ResponseEntity<String> getPublication(Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body("Hello World");
    }
}
