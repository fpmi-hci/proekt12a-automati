package com.readme.api.db.entity;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "application_user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email was entered")
    @Column(name = "email", unique = true)
    private String email;


    @Size(min = 6, message = "Password should contain than 5 symbols")
    @Column(name = "password")
    private String encodedPassword;

    @Column(name = "status")
    private UserStatus status;

    @Column(name = "role")
    private String role;

    @Column(name = "iconPath")
    private String iconPath;

    @NotBlank(message = "Name must be provided for user")
    @Column(name = "name")
    private String name;
}
