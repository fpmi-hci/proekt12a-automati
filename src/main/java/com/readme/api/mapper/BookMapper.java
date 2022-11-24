package com.readme.api.mapper;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.Comment;
import com.readme.api.rest.dto.BookRequestDto;
import com.readme.api.rest.dto.BookResponseDto;
import com.readme.api.service.AuthorService;
import com.readme.api.service.GenreService;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
@Setter
public abstract class BookMapper {

    @Autowired
    protected AuthorService authorService;

    @Autowired
    protected GenreService genreService;


    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "cost", source = "cost"),
            @Mapping(target = "imageUrl", source = "imageUrl"),
            @Mapping(target = "authors",
                    expression = "java(authorService.findByIdList(book.getAuthors()))"),
            @Mapping(target = "genres",
                    expression = "java(genreService.findByIdList(book.getGenres()))"),
            @Mapping(target = "comments", expression = "java(generateEmptyList())"),

    })
    public abstract Book requestToEntity(BookRequestDto book);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "cost", source = "cost"),
            @Mapping(target = "imageUrl", source = "imageUrl"),
            @Mapping(target = "authors", source = "authors"),
            @Mapping(target = "genres", source = "authors"),
            @Mapping(target = "comments", source = "authors"),

    })
    public abstract BookResponseDto entityToResponse(Book book);

    public List<Comment> generateEmptyList() {
        return new ArrayList<>();
    }
}
