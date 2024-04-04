package com.api.swagger3.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.swagger3.config.JwtProvider;
import com.api.swagger3.service.CustomUserDetailsService;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //헤더에 있는 토큰 불러오기
        String accesToken = resolveToken(request, "Access");
        String refreshToken = resolveToken(request, "Refresh");

        //JWT 유효성 검증
        if (StringUtils.hasText(accesToken) && jwtProvider.validateToken(accesToken)) {
            Long memberKey = jwtProvider.getMemberKey(accesToken);

            //유저와 토큰 일치 시 userDetails 생성
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(memberKey.toString());

            if(userDetails != null){
                //UserDetails, Passwordm Role 접근권한 인증 토큰 생성
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //현재 Request의 Security Context에 접근권한 설정
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }

        try {
            filterChain.doFilter(request, response);
        } catch (java.io.IOException e) {
            // TODO Auto-generated catch block
            log.error("doFilter ioException");
            e.printStackTrace();
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            log.error("doFilter ServletException");
            e.printStackTrace();
        }
    }

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    private String resolveToken(HttpServletRequest request, String tokenType) {
        //accessToken
        if(tokenType.equals("Access")){
            String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }
        }
        //refreshToken
        else {
            return request.getHeader(REFRESH_TOKEN_HEADER);
        }
        return null;
    }
}