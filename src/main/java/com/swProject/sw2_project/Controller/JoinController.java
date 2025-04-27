package com.swProject.sw2_project.Controller;

import com.swProject.sw2_project.DTO.CmmnJoinDTO;
import com.swProject.sw2_project.Service.EmailAuthService;
import com.swProject.sw2_project.Service.JoinService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/join")
@Slf4j
public class JoinController {
    @Autowired
    JoinService joinService;

    @Autowired
    EmailAuthService emailAuthService;

    Map<String, Object> rtnMap = new HashMap<>();
    // 회원가입 처리
    @PostMapping("/register")
    public String userRegister(@RequestBody CmmnJoinDTO cmmnJoinDTO,@RequestParam int authCode) {
        String chkEmailAuth = emailAuthService.validateAuthCode(cmmnJoinDTO.getUserEmail(), authCode);
        if (chkEmailAuth.equals("Y")) {
            rtnMap = joinService.registerUserLogin(cmmnJoinDTO);
            String status = (String) rtnMap.get("status");
            if (status.equals("success")) {
                return "회원가입 성공 ";
            }
        }
        return "회원가입 실패";
    }
    @PostMapping("/chkUserId")
    public String chkUserId(@RequestParam String userId) {
        String chkUserId = joinService.chkUserId(userId);
        if ("success".equals(chkUserId)) {
            return "사용 가능한 아이디입니다.";
        }
        return "사용 불가능한 아이디입니다.";

    }

    @PostMapping("/sendAuthEmail")
    public String sendAuthEmail(@RequestParam String userEmail){
        try {
            emailAuthService.sendEmail(userEmail);
            return "이메일을 성공적으로 보냈습니다!";
        } catch (Exception e) {
            e.printStackTrace();
            return "이메일 전송에 실패했습니다.";
        }
    }

    @PostMapping("/authEmail")
    public String authEmail(@RequestParam String userEmail,@RequestParam int authCode){
        try {
            return emailAuthService.validateAuthCode(userEmail,authCode);
        } catch (Exception e) {
            e.printStackTrace();
            return "이메일 전송에 실패했습니다.";
        }
    }

}
