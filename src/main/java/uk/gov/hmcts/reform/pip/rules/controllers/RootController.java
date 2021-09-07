package uk.gov.hmcts.reform.pip.rules.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.hmcts.reform.pip.rules.model.Court;
import uk.gov.hmcts.reform.pip.rules.model.Publication;
import uk.gov.hmcts.reform.pip.rules.rules.RulesService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Default endpoints per application.
 */
@RestController
@Api(tags = "Business Rules root API")
public class RootController {

    private static final String DATE_FORMAT = "dd/MM/yyyy";

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
     * <p>It will apply the business rules against the publication and requesting user,
     * to understand whether they should see the requested publication.
     *
     * <p>If the user does not have permission to see the resource, or the resource is not found,
     * then a 404 will be returned instead.
     *
     * @param id The ID of the publication to search for.
     * @return The publication, if found.
     */
    @GetMapping("/publication/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, response = String.class, message = "Publication has been found"),
        @ApiResponse(code = 404, response = String.class, message = "Publication has not been found")
    })
    public ResponseEntity<Publication> getPublication(
        @ApiParam(value = "The publication ID to retrieve", required = true) @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(rulesService.getPublication(id));
    }

    /**
     * This endpoint returns a court, based on it's ID.
     *
     * <p>It will apply the business rules against the court and requesting user,
     * to understand whether they should see the requested court.
     *
     * <p>If the user does not have permission to see the resource, or the resource is not found,
     * then a 404 will be returned instead.
     *
     * @param id The ID of the court to search for.
     * @return The court, if found.
     */
    @GetMapping("/court/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, response = String.class, message = "Court has been found"),
        @ApiResponse(code = 404, response = String.class, message = "Court has not been found")
    })
    public ResponseEntity<Court> getCourt(
        @ApiParam(value = "The court ID to retrieve", required = true) @PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(rulesService.getCourt(id));
    }

    /**
     * This endpoint returns a court list, based on input search.
     *
     * <p>It will apply the business rules against the court and requesting user,
     * to understand whether they should see the requested court.
     *
     * <p>If the user does not have permission to see the resource, or the resource is not found,
     * then a 404 will be returned instead.
     *
     * @param input The ID of the court to search for.
     * @return The court, if found.
     */
    @GetMapping("/courtlist/{input}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, response = String.class, message = "Court has been found"),
        @ApiResponse(code = 404, response = String.class, message = "Court has not been found")
    })
    public ResponseEntity<List<Court>> getCourtList(
        @ApiParam(value = "The input search to retrieve", required = true) @PathVariable String input) {
        return ResponseEntity.status(HttpStatus.OK)
            .body(rulesService.getCourtList(input));
    }

    /**
     * This endpoint returns a court list.
     *
     * <p>It will apply the business rules against the court and requesting user,
     * to understand whether they should see the requested court.
     *
     * <p>If the user does not have permission to see the resource, or the resource is not found,
     * then a 404 will be returned instead.
     *
     * @return The list of all courts, if found.
     */
    @GetMapping("/courtlistall")
    @ApiResponses(value = {
        @ApiResponse(code = 200, response = String.class, message = "Court has been found"),
        @ApiResponse(code = 404, response = String.class, message = "Court has not been found")
    })
    public ResponseEntity<List<Court>> getCourtList() {
        return ResponseEntity.status(HttpStatus.OK)
            .body(rulesService.getCourtList(""));
    }

    /**
     * This endpoint returns a hearings list, based on court's ID.
     *
     * <p>It will apply the business rules against the court and requesting user,
     * to understand whether they should see the requested court.
     *
     * <p>If the user does not have permission to see the resource, or the resource is not found,
     * then a 404 will be returned instead.
     *
     * @param id The ID of the court to search for.
     * @return The hearings list, if found.
     */
    @GetMapping("/hearings/{id}")
    @ApiResponses(value = {
        @ApiResponse(code = 200, response = String.class, message = "hearings have been found"),
        @ApiResponse(code = 404, response = String.class, message = "hearings have not been found")
    })
    public ResponseEntity<Court> getHearings(
        @ApiParam(value = "The court ID to retrieve", required = true) @PathVariable Integer id) throws ParseException {

            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            String d1String = sdf.format(new Date());
            Date d1 = sdf.parse(d1String);
            LocalDateTime localDateTime = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

            localDateTime = localDateTime.plusDays(1);
            Date d2 = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

            return ResponseEntity.status(HttpStatus.OK)
                .body(rulesService.getHearings(id, d1, d2));



    }
}
