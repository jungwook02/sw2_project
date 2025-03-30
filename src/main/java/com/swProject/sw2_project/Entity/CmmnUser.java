package com.swProject.sw2_project.Entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Data
@Entity
public class CmmnUser {

    @Id  // 이 필드가 기본 키임을 명시합니다.
    private String userId;

    private String userNm;
    private String userAge;
    private String userEmail;
    private String telNo;
    private LocalDate regDt;
    private LocalDate chgDt;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", insertable = false, updatable = false)
    private CmmnUserLogin cmmnUserLogin;

}
