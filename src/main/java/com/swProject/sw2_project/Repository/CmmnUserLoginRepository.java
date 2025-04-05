package com.swProject.sw2_project.Repository;

import  com.swProject.sw2_project.Entity.CmmnUserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CmmnUserLoginRepository extends JpaRepository<CmmnUserLogin, String> {
    CmmnUserLogin findByUserId(String userId);

    @Query("SELECT u.userId FROM CmmnUserLogin u WHERE u.userId = :userId")
    String findByChkUserId(@Param("userId") String userId);



}
