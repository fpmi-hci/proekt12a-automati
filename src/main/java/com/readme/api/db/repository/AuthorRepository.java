package com.readme.api.db.repository;

import com.readme.api.db.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByFullName(String name);
}
