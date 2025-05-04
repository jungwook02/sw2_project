package com.swProject.sw2_project.Entity;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
public class CmmnUser {
    // 사용자_아이디 (Primary Key로 지정)
    @Id
    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    // 사용자_이름
    private String userNm;

    // 사용자_나이
    private String userAge;

    @Version  // 낙관적 락을 위한 버전 관리 필드 추가
    private Long version;

    // 사용자_이메일
    @Column(nullable = false)
    private String userEmail;

    // 사용자_번호
    private String telNo;

    // 등록_날짜
    private String regDt;

    // 변경_날짜
    private String chgDt;

    @OneToOne
    @MapsId  // userId를 공유
    @JoinColumn(name = "userId")
    private CmmnUserLogin cmmnUserLogin;
}
