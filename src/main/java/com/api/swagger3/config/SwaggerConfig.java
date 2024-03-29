package com.api.swagger3.config;

import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.*;

import io.jsonwebtoken.lang.Arrays;
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
     * SecurityGroupOpenApi - 로그인 후 사용 가능한 API
     * NonSecurityGroupOpenApi - 로그인 없이 사용 가능한 API
     */
    @Bean
    public GroupedOpenApi SecurityGroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("Security Open Api")
                .pathsToMatch("/v1/auth/**")
                .pathsToExclude("/v1/nonauth/**")
                .addOpenApiCustomizer(buildSecurityOpenApi())
                .build();
    }

    @Bean
    public GroupedOpenApi NonSecurityGroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("Non Security Open Api")
                .pathsToMatch("/v1/nonauth/**")
                .pathsToExclude("/v1/auth/**")
                .build();
    }

    // //스웨거에서 사용할 API 인증 방식 목록
    // private List<SecurityScheme> defaultAuth() {
    //     AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    //     AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    //     authorizationScopes[0] = authorizationScope;
    //     List<SecurityScheme> list = Arrays.asList(new SecurityScheme("Access", authorizationScopes));
    //     return Arrays.asList(
    //             new SecurityScheme("access", authorizationScopes),
    //             new SecurityScheme("refresh", authorizationScopes));
    // }

    // // API 작업에 사용할 기본 인증 방식 목록
    // private SecurityContext securityContext() {
    //     return SecurityContext.builder()
    //             .securityReferences(defaultAuth())
    //             .build();
    // }

    // private ApiKey apiKey() {
    //     return new ApiKey("Access", "Access", "Header");
    // }

    // private ApiKey apiRefreshKey() {
    //     return new ApiKey("Refresh", "Refresh", "Header");
    // }

    
    public OpenApiCustomizer buildSecurityOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer");
        
        return OpenApi -> OpenApi
                .addSecurityItem(new SecurityRequirement().addList("ACCESS TOKEN"))
                .getComponents().addSecuritySchemes("ACCESS TOKEN", securityScheme);
    }
}
