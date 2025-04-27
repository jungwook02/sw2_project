package com.swProject.sw2_project.Util.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private static final String SECRET_KEY = "your_secret_key";  // 실제 애플리케이션에서는 안전한 키 관리 필요

    // Access Token 생성
    public static String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15분
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Refresh Token 생성
    public static String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7일
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // JWT에서 Claims 추출
    public static Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 유효성 검사
    public static boolean validateToken(String token, String userId) {
        String extractedUserId = extractUserId(token);
        return extractedUserId.equals(userId) && !isTokenExpired(token);
    }

    // 사용자 ID 추출
    public static String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }

    // 만료 여부 확인
    private static boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Access Token 및 Refresh Token 생성
    public String generateToken(String userId) {
        return generateAccessToken(userId);  // 예시로 Access Token만 반환
    }
}


