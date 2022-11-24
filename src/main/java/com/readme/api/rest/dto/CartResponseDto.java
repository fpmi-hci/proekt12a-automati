package com.readme.api.rest.dto;

import com.readme.api.db.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class CartResponseDto {
    private long id;

    private List<Book> books;
}
