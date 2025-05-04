package com.swProject.sw2_project.Repository;

import com.swProject.sw2_project.Entity.CmmnUserLoginToken;
import com.swProject.sw2_project.Entity.UserLoginTokenId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CmmnUserLoginTokenRepository extends JpaRepository<CmmnUserLoginToken, UserLoginTokenId> {
    @Query("SELECT c FROM CmmnUserLoginToken c WHERE c.id.userId = :userId")
    Optional<CmmnUserLoginToken> findByChkUserId(@Param("userId") String userId);

}
