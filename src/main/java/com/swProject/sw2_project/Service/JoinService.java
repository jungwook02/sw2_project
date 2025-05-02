package com.swProject.sw2_project.Service;

import com.swProject.sw2_project.DTO.CmmnJoinDTO;
import com.swProject.sw2_project.Entity.CmmnUser;
import com.swProject.sw2_project.Entity.CmmnUserLogin;
import com.swProject.sw2_project.Repository.CmmnUserLoginRepository;
import com.swProject.sw2_project.Repository.CmmnUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Service
public class JoinService {

    @Autowired
    private CmmnUserLoginRepository cmmnUserLoginRepository;

    @Autowired
    private CmmnUserRepository cmmnUserRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Map<String, Object> registerUserLogin(CmmnJoinDTO dto) {
        Map<String, Object> rtnMap = new HashMap<>();

        try {
            CmmnUser cmmnUser = new CmmnUser();
            cmmnUser.setUserId(dto.getUserId());
            cmmnUser.setUserNm(dto.getUserNm());
            cmmnUser.setUserAge(String.valueOf(dto.getUserAge()));
            cmmnUser.setUserEmail(dto.getUserEmail());
            cmmnUser.setTelNo(dto.getTelNo());
            cmmnUser.setRegDt(String.valueOf(dto.getRegDt()));
            cmmnUser.setChgDt(String.valueOf(dto.getChgDt()));

            // 2. CmmnUserLogin 엔티티 생성
            CmmnUserLogin cmmnUserLogin = new CmmnUserLogin();
            cmmnUserLogin.setUserId(dto.getUserId());
            cmmnUserLogin.setUserPassword(passwordEncoder.encode(dto.getUserPassword()));
            if (dto.getBeforeUserPassword() != null) {
                cmmnUserLogin.setBeforeUserPassword(passwordEncoder.encode(dto.getBeforeUserPassword()));
            }
            cmmnUserLogin.setPasswordExpDt(dto.getPasswordExpDt());
            cmmnUserLogin.setFirPasswordYn(dto.getFirPasswordYn());
            cmmnUserLogin.setLoginType(dto.getLoginType());
            cmmnUserLogin.setUseYn(dto.getUseYn());
            cmmnUserLogin.setDelYn(dto.getDelYn());


            // CmmnUserLogin의 userId를 CmmnUser에 설정
            cmmnUser.setCmmnUserLogin(cmmnUserLogin);

            // 3. CmmnUser 엔티티 저장
            cmmnUserRepository.save(cmmnUser);

            rtnMap.put("status", "success");
        } catch (Exception e) {
            rtnMap.put("status", "fail");
            rtnMap.put("error", e.getMessage());
            throw e;  // 예외가 발생하면 롤백 처리
        }

        return rtnMap;
    }
    public String chkUserId(String userId) {
        try {
            String chkUserId = String.valueOf(cmmnUserLoginRepository.findByChkUserId(userId));
            if ("null".equals(chkUserId)) {
                return "success";
            }
        } catch (Exception e) {
            throw e;
        }
            return "fail";
    }



}
