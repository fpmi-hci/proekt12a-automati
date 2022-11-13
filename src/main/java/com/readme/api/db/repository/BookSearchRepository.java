package com.readme.api.db.repository;

import com.readme.api.db.entity.Book;
import com.readme.api.rest.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BookSearchRepository {
    @Autowired
    private BookDynamicQueryBuilder bookDynamicQueryBuilder;

    @PersistenceContext
    private EntityManager entityManager;

    public List<Book> search(SearchParams searchParams) {
        String sql = bookDynamicQueryBuilder.buildSqlSearch(searchParams);
        return (List<Book>) entityManager.createNativeQuery(sql, Book.class)
                .getResultList()
                .stream()
                .distinct()
                .collect(Collectors.toList());
    }

}
