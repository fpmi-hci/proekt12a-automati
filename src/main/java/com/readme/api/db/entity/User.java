package com.readme.api.db.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "application_user")
public class User {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String encodedPassword;

    @Column(name = "status")
    private UserStatus status;

    @Column(name = "role")
    private String role;

    @Column(name = "iconPath")
    private String iconPath;

    @Column(name = "name")
    private String name;
}
