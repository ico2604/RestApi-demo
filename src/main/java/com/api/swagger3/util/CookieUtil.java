package com.api.swagger3.util;

import org.springframework.stereotype.Component;

import com.api.swagger3.config.JwtProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class CookieUtil {
    private final JwtProvider jwtProvider;

    //엑세스 토큰 쿠기 저장
    public void setAuthCookie(HttpServletResponse res, String value){
        Cookie cookie = new Cookie("auth", value); // 쿠키 이름 지정하여 생성( key, value 개념)
        cookie.setMaxAge(LongToInt(jwtProvider.ACCESSTOKEN_VALID_MILLSECCOND)); //쿠키 유효 기간: 하루로 설정(60초 * 60분 * 24시간)
        cookie.setPath("/"); //모든 경로에서 접근 가능하도록 설정
        res.addCookie(cookie); //response에 Cookie 추가
    }

    //특정 쿠키 삭제
    public void deleteCookie(HttpServletResponse res, String name){
        Cookie cookie = new Cookie(name, null); // 삭제할 쿠키에 대한 값을 null로 지정
        cookie.setMaxAge(0); // 유효시간을 0으로 설정해서 바로 만료시킨다.
        res.addCookie(cookie); // 응답에 추가해서 없어지도록 함
    }

    //모든 쿠키 삭제
    public void deleteAllCookies(HttpServletRequest req,HttpServletResponse res) {
        Cookie[] cookies = req.getCookies(); // 모든 쿠키의 정보를 cookies에 저장
        if (cookies != null) { // 쿠키가 한개라도 있으면 실행
            for (int i = 0; i < cookies.length; i++) {
                cookies[i].setMaxAge(0); // 유효시간을 0으로 설정
                res.addCookie(cookies[i]); // 응답에 추가하여 만료시키기.
            }
        }
    }
    private int LongToInt(Long num){
        return Integer.parseInt(num.toString());
    }
}
