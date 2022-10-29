package com.readme.api.rest;

import com.readme.api.db.entity.Genre;
import com.readme.api.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Genre>> getAll() {
        List<Genre> genres = genreService.findAll();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Genre>> searchGenres(String name) {
        List<Genre> genres = genreService.findByName(name);
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Genre> addGenre(@RequestBody @Valid Genre genre) {
        Genre newGenre = genreService.saveOrUpdateGenre(genre);
        return new ResponseEntity<>(newGenre, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Genre> updateGenre(@PathVariable("id") long id, @Valid  Genre genre) {
        Genre updatedGenre = genreService.updateGenre(id, genre);
        return new ResponseEntity<>(updatedGenre, HttpStatus.ACCEPTED);
    }
}
