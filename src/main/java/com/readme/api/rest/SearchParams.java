package com.readme.api.rest;


import lombok.Data;

import java.util.List;

@Data
public class SearchParams {

    private int pageNumber = 0;

    private int pageSize = 100;

    private List<SearchEntry> params;

    private String sortDirection = "asc";

    private String sortField;


}
