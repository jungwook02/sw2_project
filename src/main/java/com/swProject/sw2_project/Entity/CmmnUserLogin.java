package com.swProject.sw2_project.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data
public class CmmnUserLogin {
        @Id
        @Column(name = "userId", nullable = false)
        private String userId;

        @Column(name = "userPassword")
        private String userPassword;

        @Column(name = "beforeUserPassword")
        private String beforeUserPassword;

        @Column(name = "passwordExpDt")
        private LocalDate passwordExpDt;

        @Column(name = "firPasswordYn")
        private String firPasswordYn;

        @Column(name = "loginType")
        private String loginType;

        @Column(name = "userYn")
        private String useYn;

        @Column(name = "delYn")
        private String delYn;

        @Column(name = "regDt")
        private LocalDate regDt;

        @Column(name = "chgDt")
        private LocalDate chgDt;

        @OneToOne(mappedBy = "cmmnUserLogin")
        @PrimaryKeyJoinColumn
        private CmmnUser cmmnUser;
}

