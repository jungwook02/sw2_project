package com.swProject.sw2_project.Service;

import com.swProject.sw2_project.DTO.CmmnUserLoginTokenDTO;
import com.swProject.sw2_project.Entity.CmmnUserLogin;
import com.swProject.sw2_project.Entity.CmmnUserLoginToken;
import com.swProject.sw2_project.Entity.UserLoginTokenId;
import com.swProject.sw2_project.Repository.CmmnUserLoginRepository;
import com.swProject.sw2_project.Repository.CmmnUserLoginTokenRepository;
import com.swProject.sw2_project.Util.Jwt.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
@Slf4j
@Service
public class LoginService {

    @Autowired
    private CmmnUserLoginRepository cmmnUserLoginRepository;

    @Autowired
    private CmmnUserLoginTokenRepository cmmnUserLoginTokenRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 로그인 인증 및 JWT 토큰 발급
    public String authenticateUser(String userId, String password) {
        // 사용자 로그인 정보 조회
        CmmnUserLogin userLogin = cmmnUserLoginRepository.findByUserId(userId);

        if (userLogin != null && passwordEncoder.matches(password, userLogin.getUserPassword())) {
            // 비밀번호 일치시 JWT 토큰 생성
            String accessToken = jwtUtil.generateAccessToken(userId);
            String refreshToken = jwtUtil.generateRefreshToken(userId);

            saveRefreshToken(userId, refreshToken);
            Date tokenExpiration = jwtUtil.extractClaims(accessToken).getExpiration();
            CmmnUserLoginTokenDTO loginTokenDTO = new CmmnUserLoginTokenDTO(userId, refreshToken, tokenExpiration.toString());

            // CmmnUserLoginToken 엔티티 생성 후 저장
            saveUserLoginToken(loginTokenDTO);

            // Access Token 반환
            return accessToken;  // 생성된 JWT 토큰 반환
        }

        // 인증 실패 시
        return "Invalid credentials!";
    }


    // Refresh Token 저장
    public void saveRefreshToken(String userId, String refreshToken) {
        // DTO 객체 생성
        CmmnUserLoginTokenDTO loginTokenDTO = new CmmnUserLoginTokenDTO(userId, refreshToken, "7d");
        saveUserLoginToken(loginTokenDTO);
    }

    // UserLoginTokenDTO를 받아서 DB에 저장하는 메서드
    private void saveUserLoginToken(CmmnUserLoginTokenDTO loginTokenDTO) {
        // DTO -> Entity 변환
        UserLoginTokenId tokenId = new UserLoginTokenId(loginTokenDTO.getUserId(), loginTokenDTO.getRefreshToken());
        CmmnUserLoginToken userLoginToken = new CmmnUserLoginToken(tokenId, loginTokenDTO.getTokenExpiration());

        // DB에 저장
        cmmnUserLoginTokenRepository.save(userLoginToken);
    }
}


