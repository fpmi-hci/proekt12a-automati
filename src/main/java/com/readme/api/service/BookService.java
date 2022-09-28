package com.readme.api.service;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.User;
import com.readme.api.db.repository.BookRepository;
import com.readme.api.db.repository.BookSearchRepository;
import com.readme.api.mapper.BookMapper;
import com.readme.api.rest.SearchParams;
import com.readme.api.rest.dto.BookRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private BookSearchRepository bookSearchRepository;

    private BookMapper bookMapper;

    private UserService userService;
    private OrderService orderService;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book addBook(BookRequestDto requestBook) {
        Book book = bookMapper.requestToEntity(requestBook);
        return bookRepository.save(book);
    }

    @Transactional
    public Book deleteBook(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isEmpty()) {
            return new Book();
        }
        Book bookToDelete = optionalBook.get();
        bookRepository.delete(bookToDelete);
        return bookToDelete;
    }

    @Transactional
    public Book updateBook(long id, BookRequestDto requestBook) {
        Book book = bookMapper.requestToEntity(requestBook);
        book.setId(id);
        return bookRepository.save(book);
    }

    public List<Book> findByIdList(List<Long> ids) {
        if(ids == null){
            return Collections.emptyList();
        }
        return bookRepository.findAllById(ids);
    }

    public Book findById(long id) {
        return bookRepository.getById(id);
    }

    public List<Book> findBooks(SearchParams searchParams) {
        return bookSearchRepository.search(searchParams);
    }

    public List<Book> findPurchasedBooksForCurrentUser(String currentUserToken) {
        User user = userService.findUserByToken(currentUserToken);
        return orderService.findUserBooks(user);
    }
}
