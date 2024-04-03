package com.api.swagger3.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.api.swagger3.model.dto.LoginDTO;
import com.api.swagger3.model.response.TokenReIssue;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwe;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
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
    private final static Long ACCESSTOKEN_VALID_MILLSECCOND = 1000 * 60L * 10; // 유효시간 임시적으로 10분
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
    public String createAccessToken(Long memberKey) throws Exception {
        try{

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
        }catch(Exception e){
            log.error("createAccessToken err", e);
            throw new Exception();
        }
        
    }

    /*
     * Refresh Token 발급
     */
    public String createRefreshToken(Long memberKey) throws Exception {
        try{

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
        }catch(Exception e){
            log.error("createRefreshToken err", e);
            throw new Exception();
        }
    }

    /*
     * AccessToken으로 검증하기
     */
    public TokenReIssue verifyToken(String accessToken, String refreshToken) throws Exception, JwtException, IllegalArgumentException{
        TokenReIssue reissueToken = new TokenReIssue();

        Long claim_memberKey = null;
        String claim_accessToken = null;

        try{
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(accessToken);

            Claims payload = claimsJws.getPayload();
            claim_memberKey = Long.parseLong(payload.get("memberKey").toString());

            reissueToken.setMessage("PASS");
            return reissueToken;
        }
        catch (MissingClaimException e) {
            e.printStackTrace();
            throw new Exception("Access Token Claims 오류!");
        }
        catch (MalformedJwtException e) {
            e.printStackTrace();
            throw new Exception("Access Token 유효검사 :: 토큰의 유형이 잘못되었습니다.");
        }
        catch (ExpiredJwtException e) {
            log.info("Access Token 유효검사 :: 만료된 토큰입니다. Refresh Token 유효성 검사를 실시합니다.");
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Access Token 유효검사 :: 기타오류 발생");
        }

        // AccessToken 유효기간이 지났을 경우 RefreshToken 유효성 검사 실시
        try{
            Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(refreshToken);

            /*
             * RefreshToken이 유효할 경우 AccessToken과 RefreshToken을 재발급해준다.
             */
            
            reissueToken.setAccessToken(createAccessToken(claim_memberKey));
            reissueToken.setRefreshToken(createRefreshToken(claim_memberKey));
            reissueToken.setMessage("PASS");
            return reissueToken;

        }
        catch (MissingClaimException e) {
            e.printStackTrace();
            throw new Exception("Refresh Token Claims 오류!");
        }
        catch (ExpiredJwtException e) {
            throw new Exception("Refresh Token 유효검사 :: 만료된 토큰입니다. 토큰 재발급이 필요합니다.");
        } 
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Refresh Token 유효검사 :: 기타오류 발생");
        }

    }

    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(token);
        Claims claims = claimsJws.getPayload();
        log.info("authorites = {}", claims.get("memberKey").toString().split(","));
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList(); //authorities를 빈 리스트로 설정했으므로, 그대로 대입.

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 토큰의 유효성 검증을 수행
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

}
