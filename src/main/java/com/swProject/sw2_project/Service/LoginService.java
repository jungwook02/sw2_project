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

import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    @Autowired
    private CmmnUserLoginRepository cmmnUserLoginRepository;

    @Autowired
    private CmmnUserRepository cmmnUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean authenticateUser(String userId, String password) {
        CmmnUserLogin cmmnUserLogin = cmmnUserLoginRepository.findByUserId(userId);
//        if (cmmnUserLogin != null && passwordEncoder.matches(password, cmmnUserLogin.getUserPassword())) {
//            return true;  // 로그인 성공
//        }
        return false;  // 로그인 실패
    }




}

