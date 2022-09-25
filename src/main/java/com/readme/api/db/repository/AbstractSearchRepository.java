package com.readme.api.db.repository;

import com.readme.api.db.entity.Book;
import com.readme.api.rest.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public abstract class AbstractSearchRepository<T> {

    @Autowired
    private DynamicQueryBuilder dynamicQueryBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> search(SearchParams searchParams) {
        String sql = dynamicQueryBuilder.buildSqlSearch(getTableName(), searchParams);
        return entityManager.createNativeQuery(sql, Book.class).getResultList();
    }

    public abstract String getTableName();
}
