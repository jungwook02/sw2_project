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
            String encodedPassword = passwordEncoder.encode((String) paramMap.get("password"));
            String userId = (String) paramMap.get("userId");
            // 사용자 로그인 정보 생성
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

            // 사용자 로그인 정보 저장
            cmmnUserLoginRepository.save(cmmnUserLogin);


            // 사용자 정보 저장
            CmmnUser cmmnUser = new CmmnUser();
            cmmnUser.setCmmnUserLogin(cmmnUserLogin);

            cmmnUser.setUserId(cmmnUserLogin.getUserId());
            cmmnUser.setUserNm((String) paramMap.get("userNm"));
            cmmnUser.setUserAge((String) paramMap.get("userAge"));
            cmmnUser.setUserEmail((String) paramMap.get("userEmail"));
            cmmnUser.setTelNo((String) paramMap.get("telNo"));
            cmmnUser.setRegDt(String.valueOf(LocalDate.now()));
            cmmnUser.setChgDt(String.valueOf(LocalDate.now()));

            System.out.println("+++++++++++++++++++++++++++++++++++++++++");
            System.out.println(cmmnUserLogin);

            // CmmnUserLogin 연결


            // 사용자 정보 저장
            cmmnUserRepository.save(cmmnUser);

            rtnMap.put("status", "success");
        } catch (Exception e) {
            rtnMap.put("status", "fail");
            rtnMap.put("error", e.getMessage());
            throw e;  // 예외를 던져서 롤
        }

        return rtnMap;
    }
}
