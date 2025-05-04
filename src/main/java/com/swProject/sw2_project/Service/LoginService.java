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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

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
            CmmnUserLoginTokenDTO loginTokenDTO = new CmmnUserLoginTokenDTO(userId, refreshToken, tokenExpiration);

            saveUserLoginToken(loginTokenDTO);
            return accessToken;  // 생성된 JWT 토큰 반환
        }

        // 인증 실패 시
        return "Invalid credentials!";
    }


    // Refresh Token 저장
    public void saveRefreshToken(String userId, String refreshToken) {
        Date exp = jwtUtil.extractClaims(refreshToken).getExpiration();
        CmmnUserLoginTokenDTO loginTokenDTO = new CmmnUserLoginTokenDTO(userId, refreshToken, exp);
        saveUserLoginToken(loginTokenDTO);
    }

    @Transactional
    public void saveUserLoginToken(CmmnUserLoginTokenDTO dto) {
        // 사용자 정보 조회 (연관관계 설정용)
        CmmnUserLogin user = cmmnUserLoginRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        // 기존 토큰이 존재하는지 확인 (여기서는 복합 조건을 사용)
        Optional<CmmnUserLoginToken> existingToken = cmmnUserLoginTokenRepository
                .findByChkUserId(dto.getUserId());

        CmmnUserLoginToken entity;

        if (existingToken.isPresent()) {
            // 기존 토큰 존재 시 만료일자 갱신
            entity = existingToken.get();
            entity.setTokenExpDt(dto.getTokenExpiration());
        } else {
            // 새로운 토큰 생성
            UserLoginTokenId tokenId = new UserLoginTokenId(dto.getRefreshToken(), dto.getUserId());
            entity = new CmmnUserLoginToken(tokenId, dto.getTokenExpiration());
        }

        // 연관관계 설정
        entity.setCmmnUserLogin(user);  // 사용자 정보 연결
        cmmnUserLoginTokenRepository.save(entity);
    }
}

