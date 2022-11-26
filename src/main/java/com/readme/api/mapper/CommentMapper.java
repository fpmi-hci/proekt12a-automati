package com.readme.api.mapper;

import com.readme.api.db.entity.Cart;
import com.readme.api.db.entity.Comment;
import com.readme.api.rest.dto.CartRequestDto;
import com.readme.api.rest.dto.CommentRequestDto;
import com.readme.api.service.BookService;
import com.readme.api.service.UserService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
@Setter
public abstract class CommentMapper {
    @Autowired
    protected BookService bookService;

    @Autowired
    protected UserService userService;


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "rate", source = "rate"),
            @Mapping(target = "book", expression = "java(bookService.findById(comment.getBookId()))"),
    })
    public abstract Comment requestToEntity(CommentRequestDto comment);
}
