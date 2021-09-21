package uk.gov.hmcts.reform.finrem.caseorchestration.smoketests;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan("uk.gov.hmcts.reform.pip.rules.smoketests")
@PropertySource("application.properties")
public class SmokeTestConfiguration {

     @Value("${test.instance.uri}")
    private String url;

    private RestAssuredConfig config;

    @Before
    public void setUp() {
        RestAssured.useRelaxedHTTPSValidation();
        config = RestAssured.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 60000)
                        .setParam("http.socket.timeout", 60000)
                        .setParam("http.connection-manager.timeout", 60000));
    }

    @Test
    public void shouldGetOkStatusFromHealthEndpoint() {
        given().config(config)
                .when()
                .get(url + "/health")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}