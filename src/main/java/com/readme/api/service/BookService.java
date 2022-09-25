package com.readme.api.service;

import com.readme.api.db.entity.Author;
import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.Genre;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {
    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final int DEFAULT_PAGE = 0;

    private BookRepository bookRepository;
    private BookSearchRepository bookSearchRepository;

    private BookMapper bookMapper;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book addBook(BookRequestDto requestBook) {
        Book book = bookMapper.requestToEntity(requestBook);
//        List<Author> authors = book.getAuthors();
//        List<Author> updatedAuthors = authors.stream()
//                .map(author -> authorService.findById(author))
//                .collect(Collectors.toList());
//        List<Genre> genres = book.getGenres();
//        List<Genre> updatedGenres = genres.stream()
//                .map(genre -> genreService.saveOrUpdateGenre(genre))
//                .collect(Collectors.toList());

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
        return bookRepository.getReferenceById(id);
    }

    public List<Book> findBooks(SearchParams searchParams) {
        return bookSearchRepository.search(searchParams);
    }
}
