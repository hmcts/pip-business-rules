package uk.gov.hmcts.reform.pip.rules.rules.redaction;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:rule-configurations/redaction.rule.properties")
@Getter
@Setter
public class NameRedactorConfiguration {

    @Value("${redactionValue}")
    private String redactionValue;

    @Value("${courtName}")
    private String courtName;



}
