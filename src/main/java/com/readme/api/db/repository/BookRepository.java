package com.readme.api.db.repository;

import com.readme.api.db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Modifying
    @Query(value = "delete from book_authors ba where ba.book_id=:bookId ;\n" +
            "delete from book b where id=:bookId\n", nativeQuery = true)
    void deleteAuthorBooks(@Param("bookId") Long bookId);

}
