package com.swProject.sw2_project.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CmmnJoinDTO {

    private String userId;
    private String userNm;
    private String userAge;
    private String userEmail;
    private String telNo;
    private String regDt;
    private String chgDt;

    private String userPassword;
    private String beforeUserPassword;
    private LocalDate passwordExpDt;
    private String firPasswordYn;
    private String loginType;
    private String useYn;
    private String delYn;

    // Getter와 Setter
}

