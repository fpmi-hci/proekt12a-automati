package com.readme.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookContent {
    private String fileName;
    private final byte[] content;

}
