package com.swProject.sw2_project.Entity;

import jakarta.persistence.*;
import lombok.Data;



@Entity
@Data
public class CmmnUser {
    // 사용자_아이디 (Primary Key로 지정)
    @Id
    @Column(name = "USER_ID", length = 20, nullable = false)
    private String userId;

    // 사용자_이름
    private String userNm;

    // 사용자_나이
    private String userAge;

    // 사용자_이메일
    @Column(nullable = false)
    private String userEmail;

    // 사용자_번호
    private String telNo;

    // 등록_날짜
    private String regDt;

    // 변경_날짜
    private String chgDt;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId //@MapsId 는 @id로 지정한 컬럼에 @OneToOne 이나 @ManyToOne 관계를 매핑시키는 역할
    @JoinColumn(name = "user_id")
    private CmmnUserLogin cmmnUserLogin;
}
