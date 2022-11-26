package com.readme.api.db.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "application_user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email was entered")
    @Column(name = "email", unique = true)
    private String email;


    @Size(min = 6, message = "Password should contain than 5 symbols")
    @Column(name = "password")
    private String password;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "iconPath")
    private String iconPath;

    @NotBlank(message = "Name must be provided for user")
    @Column(name = "name")
    private String name;
}
