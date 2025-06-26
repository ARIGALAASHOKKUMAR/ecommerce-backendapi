package com.demo.test.Entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String user_name;
    private String password;
    @Transient
    private String captcha;
    @Transient
    private String captchaId;

}

