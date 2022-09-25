package com.readme.api.rest;

import lombok.Data;

@Data
public class SearchEntry {
    private String fieldName;

    private String search;

    private boolean useDisjunction;

}
