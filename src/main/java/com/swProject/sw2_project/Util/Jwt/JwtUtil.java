package com.swProject.sw2_project.Util.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    // SecretKey를 저장할 String 필드
    @Value("${jwt.secret}")
    private String secretKeyString;

    // SecretKey를 반환하는 메소드
    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKeyString);  // application.properties의 base64 문자열
        return Keys.hmacShaKeyFor(keyBytes);  // HS256에 맞게 키 생성
    }


    // Access Token 생성
    public String generateAccessToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15분
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // SecretKey 사용
                .compact();
    }

    // Refresh Token 생성
    public String generateRefreshToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7일
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // SecretKey 사용
                .compact();
    }

    // JWT에서 Claims 추출
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())  // SecretKey 사용
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // JWT 유효성 검사
    public boolean validateToken(String token, String userId) {
        String extractedUserId = extractUserId(token);
        return extractedUserId.equals(userId) && !isTokenExpired(token);
    }

    // 사용자 ID 추출
    public String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }

    // 만료 여부 확인
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    // Access Token 및 Refresh Token 생성
    public String generateToken(String userId) {
        return generateAccessToken(userId);  // 예시로 Access Token만 반환
    }
}
