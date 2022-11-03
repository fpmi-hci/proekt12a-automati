package com.readme.api.mapper;

import com.readme.api.db.entity.Cart;
import com.readme.api.rest.dto.CartRequestDto;
import com.readme.api.rest.dto.CartResponseDto;
import com.readme.api.service.BookService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Setter
public abstract class CartMapper {
    @Autowired
    protected BookService bookService;

    @Autowired
    protected UserMapper userMapper;



    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "books", expression = "java(bookService.findByIdList(cart.getBooks()))")
    })
    public abstract Cart requestToEntity(CartRequestDto cart);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "books", source = "books"),
            @Mapping(target = "user", expression = "java(userMapper.entityToRequest(cart.getUser()))")
    })
    public abstract CartResponseDto entityToResponse(Cart cart);

}
