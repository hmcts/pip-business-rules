package uk.gov.hmcts.reform.pip.rules.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.Comparator;
import java.util.Date;

/**
 * Model which represents a hearing for a court.
 */
@Data
public class Hearing {

    /**
     * The ID for the hearing.
     */
    @JsonProperty
    private Integer hearingId;

    /**
     * The ID for the court the hearing is for.
     */
    @JsonProperty
    private Integer courtId;

    /**
     * The number for the court
     */
    @JsonProperty
    private Integer courtNumber;

    /**
     * The date of the hearing.
     */
    @JsonProperty
    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss")
    private Date date;


    /**
     * The judge for the hearing.
     */
    @JsonProperty
    private String judge;

    /**
     * The platform for the hearing (e.g Skype, Teams etc)
     */
    @JsonProperty
    private String platform;

    /**
     * The case number for the hearing.
     */
    @JsonProperty
    private String caseNumber;

    /**
     * The name of the case.
     */
    @JsonProperty
    private String caseName;
}
