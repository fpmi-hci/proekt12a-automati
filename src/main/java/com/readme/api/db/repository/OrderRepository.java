package com.readme.api.db.repository;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
    List<Book> findByUserId(long id);
}
