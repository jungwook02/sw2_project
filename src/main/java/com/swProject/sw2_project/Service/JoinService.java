package com.swProject.sw2_project.Service;

import com.swProject.sw2_project.Entity.CmmnUser;
import com.swProject.sw2_project.Entity.CmmnUserLogin;
import com.swProject.sw2_project.Repository.CmmnUserLoginRepository;
import com.swProject.sw2_project.Repository.CmmnUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class JoinService {
    @Autowired
    private CmmnUserLoginRepository cmmnUserLoginRepository;

    @Autowired
    private CmmnUserRepository cmmnUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Map<String, Object> registerUserLogin(Map<String, Object> paramMap) {
        Map<String, Object> rtnMap = new HashMap<>();

        try {
            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode((String) paramMap.get("userPassword"));
            String userId = (String) paramMap.get("userId");

            // 1. CmmnUserLogin 객체 생성
            CmmnUserLogin cmmnUserLogin = new CmmnUserLogin();
            cmmnUserLogin.setUserId(userId);
            cmmnUserLogin.setUserPassword(encodedPassword);
            cmmnUserLogin.setBeforeUserPassword("");
            cmmnUserLogin.setPasswordExpDt(LocalDate.now().plusMonths(3));
            cmmnUserLogin.setLoginType("standard");
            cmmnUserLogin.setUseYn("Y");
            cmmnUserLogin.setDelYn("N");
            cmmnUserLogin.setRegDt(LocalDate.now());
            cmmnUserLogin.setChgDt(LocalDate.now());

            // 2. CmmnUserLogin 저장
            cmmnUserLoginRepository.save(cmmnUserLogin);


            rtnMap.put("status", "success");
        } catch (Exception e) {
            rtnMap.put("status", "fail");
            rtnMap.put("error", e.getMessage());
            throw e;  // 예외를 던져서 롤백 처리
        }

        return rtnMap;
    }
    public Map<String, Object> registerUser(Map<String, Object> paramMap) {
        Map<String, Object> rtnMap = new HashMap<>();

        try {
            // 비밀번호 암호화
            String userId = (String) paramMap.get("userId");

            // 3. CmmnUser 객체 생성
            CmmnUser cmmnUser = new CmmnUser();
            cmmnUser.setUserId(userId);  // CmmnUser와 CmmnUserLogin이 동일한 userId를 공유
            cmmnUser.setUserNm((String) paramMap.get("userNm"));
            cmmnUser.setUserAge((String) paramMap.get("userAge"));
            cmmnUser.setUserEmail((String) paramMap.get("userEmail"));
            cmmnUser.setTelNo((String) paramMap.get("telNo"));
            cmmnUser.setRegDt(String.valueOf(LocalDate.now()));
            cmmnUser.setChgDt(String.valueOf(LocalDate.now()));

            // 4. CmmnUserLogin을 CmmnUser에 설정
            //cmmnUser.setCmmnUserLogin(cmmnUserLogin);

            // 5. CmmnUser 저장
            cmmnUserRepository.save(cmmnUser);

            rtnMap.put("status", "success");
        } catch (Exception e) {
            rtnMap.put("status", "fail");
            rtnMap.put("error", e.getMessage());
            throw e;  // 예외를 던져서 롤백 처리
        }

        return rtnMap;
    }
}


