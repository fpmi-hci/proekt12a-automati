package com.readme.api.db.repository;

import com.readme.api.rest.SearchEntry;
import com.readme.api.rest.SearchParams;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DynamicQueryBuilder {

    private static final String SELECT_SQL = "SELECT * FROM %s";

    private static final String SORT_PART = " ORDER BY %s %s";

    private static final String PAGING_PART = " LIMIT %d OFFSET %d";

    private static final String SEARCH_PART = " WHERE %s LIKE '%%%s%%'";

    private static final String APPEND_CONJUNCTION = " AND %s LIKE '%%%s%%'";

    private static final String APPEND_DISJUNCTION = " OR %s LIKE '%%%s%%'";

    public String buildSqlSearch(String tableName, SearchParams searchParams) {
        List<SearchEntry> search = searchParams.getParams();
        StringBuilder sql = new StringBuilder(String.format(SELECT_SQL, tableName));
        if (search != null && !search.isEmpty()) {
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

        String sortField = searchParams.getSortField();
        String sortDirection = searchParams.getSortDirection();
        if (sortField != null) {
            sql.append(String.format(SORT_PART, sortField, sortDirection));
        }
        int pageNumber = searchParams.getPageNumber();
        int pageSize = searchParams.getPageSize();
        sql.append(String.format(PAGING_PART, pageSize, pageNumber * pageSize));
        return sql.toString();
    }
}
