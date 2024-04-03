package com.api.swagger3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurtiyConfig {
    private final JwtProvider jwtProvider;

    @SuppressWarnings("removal")
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf(AbstractHttpConfigurer::disable)// token을 사용하는 방식이기 때문에 csrf를 disable합니다.
        // .exceptionHandling((exceptionHandling) -> //컨트롤러의 예외처리를 담당하는 exception handler와는 다름.
        //                 exceptionHandling
        //                         .accessDeniedHandler(jwtAccessDeniedHandler)
        //                         .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        //         )
        .headers((headers)-> // enable h2-console
                headers.contentTypeOptions(contentTypeOptionsConfig ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)))
        .authorizeHttpRequests(
            (authorizeRequests) -> 
                authorizeRequests
                .requestMatchers("/api/v1/nonauth/**").permitAll()// ./api/v1/nonauth 포함한 end point 보안 적용 X
                .anyRequest().authenticated() // 그 외 인증 없이 접근X
        )
        .apply(new JwtSecurityConfig(jwtProvider)); // JwtFilter를 addFilterBefore로 등록했던 JwtSecurityConfig class 적용
        return http.build();
    }
}
