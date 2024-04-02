package com.api.swagger3.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
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

    //private final static Long ACCESSTOKEN_VALID_MILLSECCOND = 1000 * 60L * 60 * 1; // 유효시간 1시간
    //
    private final static Long ACCESSTOKEN_VALID_MILLSECCOND = 1000 * 60L; // 유효시간 임시적으로 1분
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
    public String createRefreshToken(Long memberKey, String accessToken) throws Exception {
        try{

            Map<String, Object> payloads = new HashMap<>();
            payloads.put("memberKey", memberKey);
            payloads.put("accessToken", accessToken);

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

            claim_memberKey = (Long) payload.get("memberKey");
            log.info("claim_memberKey : "+claim_memberKey);

            reissueToken.setMessage("PASS");
            return reissueToken;
        }
        catch (MissingClaimException e) {
            throw new Exception("Access Token Claims 오류!");
        }
        catch (MalformedJwtException e) {
            throw new Exception("Access Token 유효검사 :: 토큰의 유형이 잘못되었습니다.");
        }
        catch (ExpiredJwtException e) {
            log.info("Access Token 유효검사 :: 만료된 토큰입니다. Refresh Token 유효성 검사를 실시합니다.");
        } 
        catch (Exception e) {
            throw new Exception("Access Token 유효검사 :: 기타오류 발생");
        }

        // AccessToken 유효기간이 지났을 경우 RefreshToken 유효성 검사 실시
        try{
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(getSecretKey()).build().parseSignedClaims(refreshToken);

            Claims payload = claimsJws.getPayload();

            claim_accessToken = (String) payload.get("accessToken");
            log.info("claim_accessToken : "+claim_accessToken);

            /*
             * RefreshToken이 유효할 경우 AccessToken과 RefreshToken을 재발급해준다.
             */
            
            reissueToken.setAccessToken(createAccessToken(claim_memberKey));
            reissueToken.setRefreshToken(createRefreshToken(claim_memberKey, reissueToken.getAccessToken()));
            reissueToken.setMessage("PASS");
            return reissueToken;

        }
        catch (MissingClaimException e) {
            throw new Exception("Refresh Token Claims 오류!");
        }
        catch (ExpiredJwtException e) {
            throw new Exception("Refresh Token 유효검사 :: 만료된 토큰입니다. 토큰 재발급이 필요합니다.");
        } 
        catch (Exception e) {
            throw new Exception("Refresh Token 유효검사 :: 기타오류 발생");
        }

    }

}
