package com.api.swagger3.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.api.swagger3.model.dto.LoginDTO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
/**
 * - AccessToken과 RefreshToken이 무엇이고 왜 필요할까?
 * 참고 : https://velog.io/@chuu1019/Access-Token%EA%B3%BC-Refresh-Token%EC%9D%B4%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B4%EA%B3%A0-%EC%99%9C-%ED%95%84%EC%9A%94%ED%95%A0%EA%B9%8C
 * 
 * - AccessToken, RefreshToken 문제점
 * 참고 : https://hudi.blog/refresh-token/
 * 
 * - Spring Boot와 Redis를 사용하여 RefreshToken 구현(위 문제점 해결방법 입니다.)
 * 참고 : https://hudi.blog/refresh-token-in-spring-boot-with-redis/
 */
@Slf4j
@Component
public class JwtProvider {
    // 시크릿 키를 담는 변수
    private SecretKey cachedSecretKey;

    // plain 시크릿 키를 담는 변수
    @Value("${custom.jwt.secretKey}")
    public String secretKeyPlain;

    private final static Long ACCESSTOKEN_VALID_MILLSECCOND = 1000 * 30L; // 유효시간 임시적으로 30 초 시간
    //private final static Long ACCESSTOKEN_VALID_MILLSECCOND = 1000 * 60L * 60 * 1; // 유효시간 1시간
    private final static Long REFRESHTOKEN_VALID_MILLSECCOND = 1000 * 60L * 60L * 24L * 7 ; // 유효시간 1주일

    // plain -> 시크릿 키 변환 method
    private SecretKey _getSecretKey() {
        String keyBase64Encoded = Base64.encodeBase64String(secretKeyPlain.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }

    // 시크릿 키를 반환하는 method
    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();
        return cachedSecretKey;
    }

    /*
     * Access Token 발급
     */
    @SuppressWarnings("deprecation")
    public String createAccessToken(LoginDTO loginDTO) throws Exception {
        try{
            Map<String, Object> headers = new HashMap<>();
            headers.put("typ", "JWT");
            headers.put("alg", "HS256");

            Map<String, Object> payloads = new HashMap<>();
            payloads.put("memberKey", loginDTO.getMemberKey());

            String token = Jwts.builder()
                .setSubject(loginDTO.getMemberId())  // 사용자 UUID
                .setIssuedAt(new Date())  // JWT 토큰 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + ACCESSTOKEN_VALID_MILLSECCOND))    // JWT 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, getSecretKey())  // JWT 토큰 서명
                .compact();
                
            return token;
        }catch(Exception e){
            log.error("createAccessToken err", e);
            throw new Exception();
        }
        
    }

    /*
     * Refresh Token 발급
     */
    @SuppressWarnings("deprecation")
    public String createRefreshToken(LoginDTO loginDTO) throws Exception {
        try{
            Map<String, Object> headers = new HashMap<>();
            headers.put("typ", "JWT");
            headers.put("alg", "HS256");

            Map<String, Object> payloads = new HashMap<>();
            payloads.put("memberKey", loginDTO.getMemberKey());

            String token = Jwts.builder()
                .setSubject(loginDTO.getMemberId())  // 사용자 UUID
                .setIssuedAt(new Date())  // JWT 토큰 발행 시간
                .setExpiration(new Date(System.currentTimeMillis() + REFRESHTOKEN_VALID_MILLSECCOND))    // JWT 토큰 만료 시간
                .signWith(SignatureAlgorithm.HS256, getSecretKey())  // JWT 토큰 서명
                .compact();
                
            return token;
        }catch(Exception e){
            log.error("createRefreshToken err", e);
            throw new Exception();
        }
    }
}
