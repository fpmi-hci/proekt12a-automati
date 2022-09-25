package com.readme.api.service;

import com.readme.api.db.entity.Genre;
import com.readme.api.db.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    public List<Genre> findByName(String name) {
        return genreRepository.findByName(name);
    }

    public List<Genre> findByIdList(List<Long>ids){
        if(ids == null){
            return Collections.emptyList();
        }
        return genreRepository.findAllById(ids);
    }

    @Transactional
    public Genre saveOrUpdateGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    @Transactional
    public Genre updateGenre(long id, Genre genre) {
        genre.setId(id);
        return genreRepository.save(genre);
    }
}
