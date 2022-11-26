package com.readme.api.db.repository;

import com.readme.api.rest.SearchParams;
import com.readme.api.rest.SearchType;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

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

    private static final String SORT_PART = " ORDER BY title %s";

    private static final String PAGING_PART = " LIMIT %d OFFSET %d";

    private static final String SEARCH_PART = " WHERE %s ILIKE '%%%s%%'";

    private static final String APPEND_DISJUNCTION = " OR %s ILIKE '%%%s%%'";

    private static final String SEARCH_BY_GENRE_APPEND = " AND g.id in (%s)";

    private static final String SEARCH_BY_GENRE_ONLY = " WHERE g.id in (%s)";

    private static final String AUTHOR_SEARCH = "a.full_name";

    private static final String BOOK_SEARCH = "b.title";


    public String buildSqlSearch(SearchParams searchParams) {
        String searchString = searchParams.getSearchString();
        StringBuilder sql = new StringBuilder(SELECT_SQL);
        SearchType searchType = searchParams.getSearchType();
        if (searchType == SearchType.AUTHOR) {
            sql.append(String.format(SEARCH_PART, AUTHOR_SEARCH, searchString));
        } else if (searchType == SearchType.TITLE) {
            sql.append(String.format(SEARCH_PART, BOOK_SEARCH, searchString));
        } else if(searchType == SearchType.TITLE_OR_AUTHOR){
            sql.append(String.format(SEARCH_PART, BOOK_SEARCH, searchString));
            sql.append(String.format(APPEND_DISJUNCTION, AUTHOR_SEARCH, searchString));
        }
        if(searchParams.getGenres() != null){
            String genresSearch = searchType == null ? SEARCH_BY_GENRE_ONLY : SEARCH_BY_GENRE_APPEND;
            String genres = searchParams.getGenres()
                    .stream()
                    .map(id -> Long.toString(id))
                    .collect(Collectors.joining(","));
            sql.append(String.format(genresSearch, genres));
        }

        String sortDirection = searchParams.getSortDirection();
        sql.append(String.format(SORT_PART, sortDirection));
        int pageNumber = searchParams.getPageNumber();
        int pageSize = searchParams.getPageSize();
        sql.append(String.format(PAGING_PART, pageSize, pageNumber * pageSize));
        return sql.toString();
    }
}
