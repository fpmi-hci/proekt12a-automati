package com.readme.api.db.repository;

import com.readme.api.rest.SearchEntry;
import com.readme.api.rest.SearchParams;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookDynamicQueryBuilder {

    private static final String SELECT_SQL = "SELECT * FROM PUBLIC.BOOK B\n" +
            "LEFT JOIN BOOK_AUTHORS BA\n" +
            "ON B.ID = BA.BOOK_ID\n" +
            "LEFT JOIN AUTHOR A\n" +
            "ON BA.AUTHOR_ID = A.ID\n" +
            "LEFT JOIN BOOK_GENRES BG\n" +
            "ON B.ID = BG.BOOK_ID\n" +
            "LEFT JOIN GENRE G\n" +
            "ON BG.GENRE_ID = G.ID";

    private static final String SORT_PART = " ORDER BY %s %s";

    private static final String PAGING_PART = " LIMIT %d OFFSET %d";

    private static final String SEARCH_PART = " WHERE %s LIKE '%%%s%%'";

    private static final String APPEND_CONJUNCTION = " AND %s LIKE '%%%s%%'";

    private static final String APPEND_DISJUNCTION = " OR %s LIKE '%%%s%%'";
    public static final String BOOK_PREFIX = "b.";
    public static final String AUTHOR_PREFIX = "a.";
    public static final String GENRE_PREFIX = "g.";

    public String buildSqlSearch(SearchParams searchParams) {
        List<SearchEntry> search = mergeSearchParams(
                searchParams.getBookSearchParams() == null ? new ArrayList<>() : searchParams.getBookSearchParams(),
                searchParams.getAuthorSearchParams() == null ? new ArrayList<>() : searchParams.getAuthorSearchParams(),
                searchParams.getGenreSearchParams() == null ? new ArrayList<>() : searchParams.getGenreSearchParams());
        StringBuilder sql = new StringBuilder(SELECT_SQL);

        if (!search.isEmpty()) {
            SearchEntry firstEntry = search.get(0);
            sql.append(String.format(SEARCH_PART, firstEntry.getFieldName(), firstEntry.getSearch()));
            for (int i = 1; i < search.size(); i++) {
                SearchEntry searchEntry = search.get(i);
                if (searchEntry.isUseDisjunction()) {
                    sql.append(String.format(APPEND_DISJUNCTION, searchEntry.getFieldName(), searchEntry.getSearch()));
                } else {
                    sql.append(String.format(APPEND_CONJUNCTION, searchEntry.getFieldName(), searchEntry.getSearch()));
                }
            }
        }

        String tablePrefix = searchParams.getSortTable().charAt(0) + ".";
        String sortField = tablePrefix + searchParams.getSortField();
        String sortDirection = searchParams.getSortDirection();
        sql.append(String.format(SORT_PART, sortField, sortDirection));
        int pageNumber = searchParams.getPageNumber();
        int pageSize = searchParams.getPageSize();
        sql.append(String.format(PAGING_PART, pageSize, pageNumber * pageSize));
        return sql.toString();
    }

    private List<SearchEntry> mergeSearchParams(List<SearchEntry> bookSearchParams, List<SearchEntry> authorSearchParams,
                                                List<SearchEntry> genreSearchParams) {
        bookSearchParams.forEach(param -> param.setFieldName(BOOK_PREFIX + param.getFieldName()));
        authorSearchParams.forEach(param -> param.setFieldName(AUTHOR_PREFIX + param.getFieldName()));
        genreSearchParams.forEach(param -> param.setFieldName(GENRE_PREFIX + param.getFieldName()));
        bookSearchParams.addAll(authorSearchParams);
        bookSearchParams.addAll(genreSearchParams);
        return bookSearchParams;
    }
}
