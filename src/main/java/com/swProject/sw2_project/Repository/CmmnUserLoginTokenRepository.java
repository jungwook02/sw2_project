package com.swProject.sw2_project.Repository;

import com.swProject.sw2_project.Entity.CmmnUserLoginToken;
import com.swProject.sw2_project.Entity.UserLoginTokenId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmmnUserLoginTokenRepository extends JpaRepository<CmmnUserLoginToken, UserLoginTokenId> {
}
