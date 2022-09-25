package com.readme.api.service;

import com.readme.api.db.entity.Book;
import com.readme.api.db.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
       return bookRepository.findAll();
    }
}
