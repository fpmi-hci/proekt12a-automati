package com.readme.api.service;

import com.readme.api.db.entity.Author;
import com.readme.api.db.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    public List<Author> findByName(String name) {
        return authorRepository.findByFullName(name);
    }

    public List<Author> findByIdList(List<Long> ids){
        if(ids == null){
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
}
