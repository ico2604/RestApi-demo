package com.api.swagger3.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.api.swagger3.model.response.TokenReIssue;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Collections;
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

    //private final static Long ACCESSTOKEN_VALID_MILLSECCOND = 1000 * 60L * 60 * 1; // 유효시간 1시간
    //
    public final static Long ACCESSTOKEN_VALID_MILLSECCOND = 1000 * 60L * 1; // 유효시간 임시적으로 1분
    public final static Long REFRESHTOKEN_VALID_MILLSECCOND = 1000 * 60L * 60L * 24L * 7 ; // 유효시간 1주일

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
    public String createAccessToken(Long memberKey) {
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("memberKey", memberKey);

        String token = Jwts.builder()
            .claims(payloads)
            .issuer("myCompany") //발급자
            .issuedAt(new Date()) //발행시간
            .expiration(new Date(System.currentTimeMillis() + ACCESSTOKEN_VALID_MILLSECCOND))//만료시간
            .signWith(getSecretKey(), SIG.HS256)//시크릿키
            .compact();
            
        return token;
        
    }

    /*
     * Refresh Token 발급
     */
    public String createRefreshToken(Long memberKey) {
        Map<String, Object> payloads = new HashMap<>();
        payloads.put("memberKey", memberKey);

        String token = Jwts.builder()
            .claims(payloads)
            .issuer("myCompany")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + REFRESHTOKEN_VALID_MILLSECCOND))
            .signWith(getSecretKey(), SIG.HS256)
            .compact();
            
        return token;
    }

    /*
     * AccessToken으로 검증하기
     */
    public TokenReIssue verifyAccessToken(String accessToken) throws ExpiredJwtException {
        TokenReIssue reissueToken = new TokenReIssue();

        Long claim_memberKey = null;

        try{
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(accessToken);

            Claims payload = claimsJws.getPayload();
            claim_memberKey = Long.parseLong(payload.get("memberKey").toString());
            reissueToken.setClaimMemberKey(claim_memberKey);
            return reissueToken;
        }
        catch (ExpiredJwtException e) {
            log.info("Access Token 유효검사 :: 만료된 토큰입니다. Refresh Token 유효성 검사를 실시합니다.");
        } 
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("Access Token 유효검사 :: 잘못된 JWT 서명입니다.");
        }
        catch (UnsupportedJwtException e) {
            log.error("Access Token 유효검사 :: 지원되지 않는 JWT 토큰입니다.");
        }
        catch (IllegalArgumentException e) {
            log.error("Access Token 유효검사 :: JWT 토큰이 잘못되었습니다.");
        }
        return reissueToken;
    }

    /*
     * Refresh Token 검증
     */
    public TokenReIssue verifyRefreshToken(String refreshToken, Long claim_memberKey) {
        TokenReIssue reissueToken = new TokenReIssue();
        // RefreshToken 유효성 검사 실시
        try{
            Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(refreshToken);
            return reissueToken;

        }
        catch (ExpiredJwtException e2) {
            log.error("Refresh Token 유효검사 :: 만료된 토큰입니다. Refresh Token 유효성 검사를 실시합니다.");
        } 
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e2) {
            log.error("Refresh Token 유효검사 :: 잘못된 JWT 서명입니다.");
        }
        catch (UnsupportedJwtException e2) {
            log.error("Refresh Token 유효검사 :: 지원되지 않는 JWT 토큰입니다.");
        }
        catch (IllegalArgumentException e2) {
            log.error("Refresh Token 유효검사 :: JWT 토큰이 잘못되었습니다.");
        }
        return reissueToken;
    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList(); //authorities를 빈 리스트로 설정했으므로, 그대로 대입.
        log.info(claims.toString());
        User principal = new User(claims.get("memberKey").toString(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    //토큰으로 회원 키 가져오기
    public Long getMemberKey(String token){
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();
        return Long.parseLong(claims.get("memberKey").toString());
    }

    /*
     * 토큰 검증
     */
    public Jws<Claims> validateToken(String token) {
        return Jwts.parser().verifyWith(getSecretKey()).build().parseSignedClaims(token);
    }
}
