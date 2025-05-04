package com.swProject.sw2_project.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class CmmnUserLoginTokenDTO {

    private String userId;
    private String refreshToken;
    private Date tokenExpiration;

    // 생성자 수정
    public CmmnUserLoginTokenDTO(String userId, String refreshToken, Date tokenExpiration) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.tokenExpiration = tokenExpiration;
    }
}