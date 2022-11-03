package com.readme.api.rest.dto;

import com.readme.api.db.entity.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private long id;

    private String email;

    private UserRole role;

    private String iconPath;

    private String name;
}
