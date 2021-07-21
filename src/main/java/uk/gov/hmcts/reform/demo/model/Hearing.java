package uk.gov.hmcts.reform.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Model which represents a hearing for a court.
 */
@Data
public class Hearing {

    /**
     * The ID for the hearing.
     */
    private Integer hearingId;

    /**
     * The ID for the court the hearing is for.
     */
    private Integer courtId;

    /**
     * The date of the hearing
     */
    @JsonFormat(pattern = "MM/dd/YYYY")
    private LocalDate date;

    /**
     * The time of the hearing.
     */
    @JsonFormat(pattern = "HH:mm a")
    private LocalTime time;

    /**
     * The judge for the hearing.
     */
    private String judge;

    /**
     * The platform for the hearing (e.g Skype, Teams etc)
     */
    private String platform;

    /**
     * The case number for the hearing.
     */
    private String caseNumber;

    /**
     * The name of the case.
     */
    private String caseName;
}