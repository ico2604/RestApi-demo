package com.api.swagger3.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;


// SwaggerConfig.java
@OpenAPIDefinition(
        info = @Info(title = "Swagger Test",
                description = "설명란",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
 
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/v1/**"};
 
        return GroupedOpenApi.builder()
                .group("API-v1")
                .pathsToMatch(paths)
                .build();
    }
}
