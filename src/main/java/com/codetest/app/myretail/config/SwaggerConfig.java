package com.codetest.app.myretail.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author nrajuri
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    /**
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.codetest.app.myretail.controller"))
                .paths(PathSelectors.any()).build().apiInfo(swaggerStaticApiInfo());
    }

    private ApiInfo swaggerStaticApiInfo() {
        return new ApiInfoBuilder().description("The MyRetail platform provides an interface for consumers to access" +
                " product details including price information of all the latest titles").title("MyRetail")
                .version("1.0.0-SNAPSHOT").build();
    }
}