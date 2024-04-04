package com.api.swagger3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.api.swagger3.filter.JwtFilter;
import com.api.swagger3.handler.CustomAccessDeniedHandler;
import com.api.swagger3.handler.CustomAuthenticationEntryPoint;
import com.api.swagger3.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurtiyConfig {
    private final JwtProvider jwtProvider;

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDenidHandler;

    private static final String[] AUTH_WHITElIST = {
        "/swagger-ui/**", "/api-docs", "/swagger-ui.html", "/api-docs/**", 
        "/api/v1/nonauth/**" , "/"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        try{
            http
            .csrf((csrf) -> csrf.disable())// token을 사용하는 방식이기 때문에 csrf를 disable합니다.
            .cors(Customizer.withDefaults())// 일반적으로 모든 도메인에서의 요청이 허용됩니다.

            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//세션 관리 상태 없음으로 구성, Spring Security가 세션 생성 or 사용 X
  
            .formLogin((from) -> from.disable()) //FormLogin 비활성화
            .httpBasic((basic) -> basic.disable()) // httpBasic 비활성화
            .addFilterBefore(new JwtFilter(jwtProvider, customUserDetailsService), UsernamePasswordAuthenticationFilter.class)
	        .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(authenticationEntryPoint).accessDeniedHandler(accessDenidHandler) )
            .authorizeHttpRequests(//권한규칙 생성
                (authorizeRequests) -> 
                    authorizeRequests
                    .requestMatchers(AUTH_WHITElIST).permitAll()// ./api/v1/nonauth 포함한 end point 보안 적용 X
                    .anyRequest().authenticated() // 그 외 인증 없이 접근X
        );
            return http.build();
        }catch(Exception e){
            log.error("An error occurred while configuring Spring Security", e);
            throw e; // 예외를 다시 던져서 Spring Boot가 적절히 처리하도록 합니다.
        }
        
    }
}
