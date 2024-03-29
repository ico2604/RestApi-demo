package com.api.swagger3.config;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;

@Component
public class JwtProvider {
    // 시크릿 키를 담는 변수
    private SecretKey cachedSecretKey;

    // plain 시크릿 키를 담는 변수
    @Value("${custom.jwt.secretKey}")
    public String secretKeyPlain;

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
}
