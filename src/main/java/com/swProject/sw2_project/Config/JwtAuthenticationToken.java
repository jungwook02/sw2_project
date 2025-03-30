package com.swProject.sw2_project.Config;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String token;

    public JwtAuthenticationToken(String token) {
        super(null); // 권한이 없다고 가정하고 null 전달
        this.token = token;
        setAuthenticated(true); // 토큰이 유효하다고 가정하고 인증을 설정
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return null; // 필요한 경우, JWT에서 사용자 정보를 추출하여 반환할 수 있음
    }
}
