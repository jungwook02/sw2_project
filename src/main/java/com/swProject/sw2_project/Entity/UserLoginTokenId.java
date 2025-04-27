package com.swProject.sw2_project.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserLoginTokenId implements Serializable {

    // 사용자 아이디
    @Column(name = "user_id", length = 20)
    private String userId;

    // 토큰 아이디
    @Column(name = "token_id", length = 20)
    private String tokenId;

    // 기본 생성자
    public UserLoginTokenId() {}

    // 생성자
    public UserLoginTokenId(String userId, String tokenId) {
        this.userId = userId;
        this.tokenId = tokenId;
    }

    // equals(), hashCode() 메서드 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLoginTokenId that = (UserLoginTokenId) o;
        return userId.equals(that.userId) && tokenId.equals(that.tokenId);
    }

    @Override
    public int hashCode() {
        return 31 * userId.hashCode() + tokenId.hashCode();
    }

}
