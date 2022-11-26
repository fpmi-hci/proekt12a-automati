package com.readme.api.service;

import com.readme.api.db.entity.Author;
import com.readme.api.db.entity.Book;
import com.readme.api.db.repository.AuthorRepository;
import com.readme.api.db.repository.BookRepository;
import com.readme.api.service.exception.AuthorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public List<Author> findByName(String name) {
        return authorRepository.findByFullName(name);
    }

    public List<Author> findByIdList(List<Long> ids) {
        if (ids == null) {
            return Collections.emptyList();
        }
        return authorRepository.findAllById(ids);
    }

    @Transactional
    public Author saveOrUpdateAuthor(Author author) {
        return authorRepository.save(author);
    }

    @Transactional
    public Author updateAuthor(long id, Author author) {
        author.setId(id);
        return authorRepository.save(author);
    }

    @Transactional
    public Author deleteAuthor(long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);
        optionalAuthor.orElseThrow(() -> new AuthorNotFoundException(id));
        Author authorToDelete = optionalAuthor.get();
        for (Book authorBooks : authorToDelete.getBooks()) {
            bookRepository.deleteAuthorBooks(authorBooks.getId());
        }
        authorRepository.delete(authorToDelete);
        return authorToDelete;
    }
}
