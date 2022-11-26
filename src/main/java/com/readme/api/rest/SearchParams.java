package com.readme.api.rest;


import lombok.Data;

import java.util.List;

@Data
public class SearchParams {

    private int pageNumber = 0;

    private int pageSize = 1000;

    private List<Long> genres;

    private String searchString;

    private SearchType searchType;

    private String sortDirection = "asc";
}
