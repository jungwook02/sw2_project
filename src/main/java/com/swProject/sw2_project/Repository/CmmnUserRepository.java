package com.swProject.sw2_project.Repository;


import com.swProject.sw2_project.Entity.CmmnUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CmmnUserRepository extends JpaRepository<CmmnUser, Long> {
    CmmnUser findByUserId(String userId);
}
