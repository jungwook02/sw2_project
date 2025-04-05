package com.swProject.sw2_project.Controller;

import com.swProject.sw2_project.DTO.CmmnJoinDTO;
import com.swProject.sw2_project.Service.JoinService;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class JoinController {
    @Autowired
    JoinService joinService;


    // 회원가입 처리
    @PostMapping("/register")
    public String userRegister(@RequestBody CmmnJoinDTO cmmnJoinDTO) {
        String rtn = joinService.registerUserLogin(cmmnJoinDTO).toString();
        if(rtn.equals("success")){
            return "회원가입 성공 ";
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




}
