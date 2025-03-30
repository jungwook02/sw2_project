package com.swProject.sw2_project.Util.Jwt;

import com.swProject.sw2_project.Config.JwtAuthenticationToken;
import com.swProject.sw2_project.Util.Jwt.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.authentication.AuthenticationManager;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JWTFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public JWTFilter(JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String header = httpRequest.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // "Bearer " 뒤의 토큰을 추출
            if (jwtUtil.validateToken(token, jwtUtil.extractUserId(token))) {
                JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(token);

                try {
                    Authentication authentication = authenticationManager.authenticate(authenticationToken); // authenticationManager 사용
                    SecurityContextHolder.getContext().setAuthentication(authentication); // 인증 결과를 SecurityContext에 설정
                } catch (AuthenticationException e) {
                    // 인증 실패 시 처리 (로그 출력 등)
                    e.printStackTrace();
                }
            }
        }

        chain.doFilter(request, response); // 필터 체인 계속 진행
    }


}
