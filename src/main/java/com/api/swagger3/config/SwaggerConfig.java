package com.api.swagger3.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.customizers.OpenApiCustomizer;
// SwaggerConfig.java
@OpenAPIDefinition(
        info = @Info(title = "Swagger Demo",
                description = "나의 공부용 API문서",
                version = "0.0.1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {
    /**
     * Sagger Group 분리
     */
    @Bean
    public GroupedOpenApi v1GroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("v1")
                .pathsToMatch("/api/v1/**")
                //.addOpenApiCustomizer(buildSecurityOpenApi()) 특정 그룹만 세큐리티를 지정하려 했지만 DTO를 못찾는 오류로 인해 사용 못함
                .build();
    }

    @Bean
    public GroupedOpenApi v2GroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("v2")
                .pathsToMatch("/api/v2/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI(){
        String key = "Access Token (Bearer)";
        String refreshKey = "Refresh Token";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(key)
                .addList(refreshKey);

        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");

        SecurityScheme refreshTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("RefeshToken");

        Components components = new Components()
                .addSecuritySchemes(key, accessTokenSecurityScheme)
                .addSecuritySchemes(refreshKey, refreshTokenSecurityScheme);
        
        return new OpenAPI()
                .components(components)
                .addSecurityItem(securityRequirement);
    }
    
    private OpenApiCustomizer buildSecurityOpenApi() {
        String key = "Access Token (Bearer)";
        String refreshKey = "Refresh Token";

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(key)
                .addList(refreshKey);

        SecurityScheme accessTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .scheme("bearer")
                .bearerFormat("JWT")
                .name("Authorization");

        SecurityScheme refreshTokenSecurityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .name("RefeshToken");

        Components components = new Components()
                .addSecuritySchemes(key, accessTokenSecurityScheme)
                .addSecuritySchemes(refreshKey, refreshTokenSecurityScheme);
        
        return OpenApi -> OpenApi
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
