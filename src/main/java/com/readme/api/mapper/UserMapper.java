package com.readme.api.mapper;

import com.readme.api.db.entity.User;
import com.readme.api.rest.dto.UserDto;
import com.readme.api.security.SecurityUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mappings({
            @Mapping(target = "login", source = "name"),
            @Mapping(target = "passwordHash", source = "password"),
            @Mapping(target = "isBlocked", source = "blocked"),
            @Mapping(target = "authorities", expression = "java(user.getRole().authorities())")
    })
    SecurityUser entityToSecurityUser(User user);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "role", source = "role"),
            @Mapping(target = "iconPath", source = "iconPath"),
            @Mapping(target = "name", source = "name")
    })
    UserDto entityToRequest(User user);

}
