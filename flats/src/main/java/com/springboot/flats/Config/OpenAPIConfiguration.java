package com.springboot.flats.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("RESTFul-service")
                        .description("API Documentation")
                        .version("v1"));
    }
}
