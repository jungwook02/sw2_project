package com.swProject.sw2_project.Service;


import com.swProject.sw2_project.Entity.CmmnUser;
import com.swProject.sw2_project.Entity.CmmnUserLogin;
import com.swProject.sw2_project.Repository.CmmnUserLoginRepository;
import com.swProject.sw2_project.Repository.CmmnUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService{
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

            // 사용자 로그인 정보 생성
            CmmnUserLogin cmmnUserLogin = new CmmnUserLogin();
            cmmnUserLogin.setUserId((String) paramMap.get("userId"));
            cmmnUserLogin.setUserPassword(encodedPassword);
            cmmnUserLogin.setBeforeUserPassword("");
            cmmnUserLogin.setPasswordExpDt(LocalDate.now().plusMonths(3));
            cmmnUserLogin.setLoginType("standard");
            cmmnUserLogin.setFirPasswordYn("Y");
            cmmnUserLogin.setUseYn("Y");
            cmmnUserLogin.setDelYn(null);
            cmmnUserLogin.setRegDt(LocalDate.now());
            cmmnUserLogin.setChgDt(LocalDate.now());


            // 사용자 로그인 정보 저장
            cmmnUserLoginRepository.save(cmmnUserLogin);


            CmmnUser cmmnUser = new CmmnUser();
            cmmnUser.setUserId((String) paramMap.get("userId"));
            cmmnUser.setUserNm((String) paramMap.get("userNm"));
            cmmnUser.setUserAge((String) paramMap.get("userAge"));
            cmmnUser.setUserEmail((String) paramMap.get("userEmail"));
            cmmnUser.setTelNo((String) paramMap.get("telNo"));
            cmmnUser.setRegDt(LocalDate.now());
            cmmnUser.setChgDt(LocalDate.now());
            cmmnUser.setCmmnUserLogin(cmmnUserLogin);


            cmmnUserRepository.save(cmmnUser);

            rtnMap.put("status", "success");
        } catch (Exception e) {
            rtnMap.put("status", "fail");
            rtnMap.put("error", e.getMessage());
        }

        return rtnMap;
    }


    public boolean authenticateUser(String userId, String password) {
        CmmnUserLogin cmmnUserLogin = cmmnUserLoginRepository.findByUserId(userId);
        if (cmmnUserLogin != null && passwordEncoder.matches(password, cmmnUserLogin.getUserPassword())) {
            return true;  // 로그인 성공
        }
        return false;  // 로그인 실패
    }




}

