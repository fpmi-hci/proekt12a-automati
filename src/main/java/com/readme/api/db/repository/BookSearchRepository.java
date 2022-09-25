package com.readme.api.db.repository;

import com.readme.api.db.entity.Book;
import com.readme.api.rest.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BookSearchRepository extends AbstractSearchRepository<Book> {
    private static final String TABLE_NAME = "book";


    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
