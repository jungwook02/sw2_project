package com.swProject.sw2_project.Repository;

import  com.swProject.sw2_project.Entity.CmmnUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmmnUserLoginRepository extends JpaRepository<CmmnUserLogin, String> {
    CmmnUserLogin findByUserId(String userId);
}
