package com.readme.api.db.repository;

import com.readme.api.db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Long, Book> {
}
