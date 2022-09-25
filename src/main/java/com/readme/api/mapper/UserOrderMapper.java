package com.readme.api.mapper;

import com.readme.api.db.entity.Cart;
import com.readme.api.db.entity.UserOrder;
import com.readme.api.rest.dto.CartRequestDto;
import com.readme.api.rest.dto.UserOrderRequestDto;
import com.readme.api.service.BookService;
import com.readme.api.service.UserService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Setter
public abstract class UserOrderMapper {

    @Autowired
    protected BookService bookService;

    @Autowired
    protected UserService userService;



    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "user", expression = "java(userService.findById(order.getUserId()))"),
            @Mapping(target = "books", expression = "java(bookService.findByIdList(order.getBooks()))")
    })
    public abstract UserOrder requestToEntity(UserOrderRequestDto order);
}
