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

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam Map<String, Object> paramMap,
                                        HttpServletResponse response) {
        String userId = (String) paramMap.get("userId");
        String password = (String) paramMap.get("userPassword");

        boolean isAuthenticated = Boolean.parseBoolean(loginService.authenticateUser(userId, password));

        if (isAuthenticated) {
            String accessToken = jwtUtil.generateAccessToken(userId);
            String refreshToken = jwtUtil.generateRefreshToken(userId);

            loginService.saveRefreshToken(userId, refreshToken);

            addTokenToCookie(response, "accessToken", accessToken, 15 * 60);
            addTokenToCookie(response, "refreshToken", refreshToken, 7 * 24 * 60 * 60);

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
