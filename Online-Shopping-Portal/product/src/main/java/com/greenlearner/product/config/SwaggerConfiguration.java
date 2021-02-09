package com.greenlearner.product.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

/**
 * @author - GreenLearner(https://www.youtube.com/c/greenlearner)
 */

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.greenlearner.product.controller"))
                .build()
                .apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Product Microservice",
                "Product Microservice for Online shopping portal provided by Green Learner",
                "1.0",
                "https://github.com/greenlearner01/microservices-projects-store",
                new Contact("Arvind Kumar", "https://youtube.com/greenlearner", "whytechnology01@gmail.com"),
                "Terms of Use Licence",
                "https://github.com/greenlearner01/microservices-projects-store/blob/master/LICENSE",
                Collections.emptyList()
        );
    }
}
