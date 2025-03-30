package com.swProject.sw2_project.Entity;

import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class CmmnUserLogin {
        @Id
        private String userId;

        private String userPassword;
        private String beforeUserPassword;
        private LocalDate passwordExpDt;
        private String loginType;
        private String firPasswordYn;
        private String useYn;
        private LocalDate delYn;
        private LocalDate regDt;
        private LocalDate chgDt;

        @OneToMany(mappedBy = "cmmnUserLogin")
        private List<CmmnUser> cmmnUserList = new ArrayList<>();
}



