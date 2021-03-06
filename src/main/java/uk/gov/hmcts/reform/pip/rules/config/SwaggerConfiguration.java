package uk.gov.hmcts.reform.pip.rules.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import uk.gov.hmcts.reform.pip.rules.Application;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .select()
            .apis(RequestHandlerSelectors.basePackage(Application.class.getPackage().getName() + ".controllers"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("P&I Business Rules Service")
            .description("Use this service to view rules applied to the withholding of information not yet ready for "
                             + "release. You can find out more about how the code works at"
                             + " https://github.com/hmcts/pip-business-rules")
            .version("1.0.0")
            .contact(new Contact("P&I Team",
                                 "https://tools.hmcts.net/confluence/display/PUBH/Publication+Hub",
                                 "publicationinformation@hmcts.net"))
            .build();
    }

}
