package com.swProject.sw2_project.Controller;

import com.swProject.sw2_project.Service.JoinService;
import com.swProject.sw2_project.Service.LoginService;
import com.swProject.sw2_project.Util.Jwt.JwtUtil;
import org.hibernate.mapping.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    LoginService loginService;

    @Autowired
    JoinService joinService;

    // 회원가입 처리
    @PostMapping("/login/register")
    public String userRegister(@RequestParam Map<String, Object> paramMap) {


        return joinService.registerUserLogin(paramMap).toString();


    }

    @PostMapping("/login/register/user")
    public String usercmmnRegister(@RequestParam Map<String, Object> paramMap) {


        return joinService.registerUser(paramMap).toString();


    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam Map<String, Object> paramMap) {
        String userId = (String) paramMap.get("userId");
        String password = (String) paramMap.get("password");

        boolean isAuthenticated = loginService.authenticateUser(userId, password);
        if (isAuthenticated) {
//            String token = JwtUtil.generateToken(userId);
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }
}
