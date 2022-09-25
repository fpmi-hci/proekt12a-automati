package com.readme.api.rest.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BookRequestDto {
    private long id;

    private String title;

    private String description;

    private BigDecimal cost;

    private String bookFile;

    private List<Long> authors;

    private List<Long> genres;
}
