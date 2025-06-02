package org.koreait.member.services;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 미로그인 시 인가 실패 시 처리
 */
public class MemberAuthenticationExceptionHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String URL = request.getRequestURI();
        String qs = request.getQueryString();  // 쿼리스트링 ("주소?..."에서 ... 부분)
        URL = StringUtils.hasText(qs) ? URL += "?" + qs : URL;

        response.sendRedirect(String.format("%s/member/login?redirectUrl=%s", request.getContextPath(), URL));
    }
}
