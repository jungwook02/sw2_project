package com.swProject.sw2_project.Controller;


import com.swProject.sw2_project.Service.LoginService;
import com.swProject.sw2_project.Util.Jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam Map<String, Object> paramMap,
                                        HttpServletResponse response) {
        String userId = (String) paramMap.get("userId");
        String password = (String) paramMap.get("password");

        // 1. 사용자 인증
        boolean isAuthenticated = Boolean.parseBoolean(loginService.authenticateUser(userId, password));

        if (isAuthenticated) {
            // 2. Access Token & Refresh Token 생성
            String accessToken = JwtUtil.generateAccessToken(userId);
            String refreshToken = JwtUtil.generateRefreshToken(userId);

            // 3. Refresh Token을 DB에 저장 (서버에서만 관리)
            loginService.saveRefreshToken(userId, refreshToken);

            // 4. 쿠키에 토큰 저장 (Access Token: 클라이언트에서 사용, Refresh Token: 서버에서 관리)
            addTokenToCookie(response, "accessToken", accessToken, 15 * 60); // 15분
            addTokenToCookie(response, "refreshToken", refreshToken, 7 * 24 * 60 * 60); // 7일

            return ResponseEntity.ok("Login successful!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials!");
        }
    }

    private void addTokenToCookie(HttpServletResponse response, String name, String token, int maxAge) {
        Cookie cookie = new Cookie(name, token);
        cookie.setHttpOnly(true);  // JS에서 접근 불가
        cookie.setSecure(true);    // HTTPS에서만 전송 (개발 시에는 false로 임시 설정 가능)
        cookie.setPath("/");       // 전체 도메인에서 사용 가능
        cookie.setMaxAge(maxAge);  // 만료 시간 설정
        response.addCookie(cookie);
    }
}
