package com.api.swagger3.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.swagger3.config.JwtProvider;
import com.api.swagger3.model.response.TokenReIssue;
import com.api.swagger3.service.CustomUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * - 내가 적용한 기본 access-token 재발급 로직
 * 1. Filter에서 접근하는 모든 access token을 확인한다.
 * 2. 그 중 ExpiredJwtException이 발생하면 바로 에러코드와 함께 리턴을 한다.
 * 3. Frontend에서는 해당 에러가 발생하면 Backend로 access token 재발급 요청한다.
 * 4. 재발급한 token은 쿠키에 저장한다.
 */
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";

    private final JwtProvider jwtProvider;
    private final CustomUserDetailsService customUserDetailsService;

    // 실제 필터링 로직
    // 토큰의 인증정보를 SecurityContext에 저장하는 역할 수행
    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, java.io.IOException {
        //헤더에 있는 토큰 불러오기
        String accessToken = resolveToken(request);

        //JWT 유효성 검증
        if (StringUtils.hasText(accessToken)) {
            try {
                //accessToken으로 검증
                jwtProvider.verifyAccessToken(accessToken);
            
                Long memberKey = jwtProvider.getMemberKey(accessToken);
                //유저와 토큰 일치 시 userDetails 생성
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberKey.toString());
        
                if(userDetails != null){
                    //UserDetails, Passwordm Role 접근권한 인증 토큰 생성
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    
                    //현재 Request의 Security Context에 접근권한 설정
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            } catch (ExpiredJwtException e) {
                
            }
            
        }

        filterChain.doFilter(request, response);
    }

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    private String resolveToken(HttpServletRequest request) {
        //accessToken
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}