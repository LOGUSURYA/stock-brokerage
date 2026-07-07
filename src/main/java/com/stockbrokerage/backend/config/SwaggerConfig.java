package com.stockbrokerage.backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI stockBrokerOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Stock Brokerage & Portfolio Management API")
                        .description("REST API Documentation for Stock Brokerage System")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Surya Prasath")
                                .email("logusurya8526@gmail.com"))
                        .license(new License()
                                .name("MIT License")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation"));
    }
}