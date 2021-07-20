package uk.gov.hmcts.reform.demo.hearings;

import org.springframework.stereotype.Service;
import uk.gov.hmcts.reform.demo.model.CourtHearings;
import uk.gov.hmcts.reform.demo.model.Publication;

import java.util.List;

@Service
public class HearingsService {

    public Publication getHearings(Integer publicationId) {
        return new Publication();

    }
}
