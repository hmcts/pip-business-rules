package uk.gov.hmcts.reform.finrem.caseorchestration.smoketests;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("uk.gov.hmcts.reform.pip.rules.smoketests")
@PropertySource("application.properties")
public class SmokeTestConfiguration {

    @Test
    @DisplayName("Smoke Test")
    public void test() {
        assertEquals(1, 1,
                     "Tested");
    }
}