package com.readme.api.rest.dto;

import com.readme.api.db.entity.Author;
import com.readme.api.db.entity.Comment;
import com.readme.api.db.entity.Genre;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class BookResponseDto {

    private long id;

    private String title;

    private String description;

    private BigDecimal cost;

    private String bookFile;

    private String imageUrl;

    private List<Author> authors;

    private List<Genre> genres;

    private List<Comment> comments;

    private boolean isPurchasedByUser;
}
