package com.carpem.parser.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class SwaggerUIConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Parser API")
                        .version("1.0.0")
                        .description("This API handles parsing operations for the Carpem project.")
                        .contact(new Contact()
                                .name("Carpem Dev Team")
                                .email("iheb.bentemessek@esprit.tn")
                        )
                );
    }

}
