package com.swProject.sw2_project.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Data
@Getter
@Setter
public class CmmnUserLoginToken {

    // 복합키
    @EmbeddedId
    private UserLoginTokenId id;

    // 토큰 만료일자
    @Column(name = "token_exp_dt")
    private Date tokenExpDt;

    // CMMN_USER_LOGIN과 연관관계 설정 (N:1)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // 복합키의 userId를 외래 키로 맵핑
    @JoinColumn(name = "user_id", referencedColumnName = "userId", insertable = false, updatable = false)
    private CmmnUserLogin cmmnUserLogin;

    public CmmnUserLoginToken(UserLoginTokenId tokenId, Date tokenExpiration) {
        this.id = tokenId;
        this.tokenExpDt = tokenExpiration;
    }

    public CmmnUserLoginToken() {
    }
}
