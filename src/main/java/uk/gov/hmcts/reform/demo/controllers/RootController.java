package uk.gov.hmcts.reform.demo.controllers;

import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.demo.publications.RulesService;
import uk.gov.hmcts.reform.demo.model.Publication;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Default endpoints per application.
 */
@RestController
public class RootController {

    @Autowired
    RulesService rulesService;


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
    @ApiResponses(value = {
        @ApiResponse( code = 200, response = String.class, message = "Publication has been found" ),
        @ApiResponse( code = 404, response = String.class, message = "Publication has not been found")
    })
    public ResponseEntity<Publication> getPublication(@ApiParam(value = "The publication ID to retrieve", required = true)
                                                 @PathVariable Integer id) {

        return ResponseEntity.status(HttpStatus.OK)
            .body(rulesService.getPublication(id));
    }
}
