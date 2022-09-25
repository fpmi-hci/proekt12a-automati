package com.readme.api.rest;

import com.readme.api.db.entity.Author;
import com.readme.api.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        List<Author> authors = authorService.findAll();
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Author>> searchAuthors(String name) {
        List<Author> authors = authorService.findByName(name);
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        Author newAuthor = authorService.saveOrUpdateAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.ACCEPTED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathParam("id") long id, Author author) {
        Author updatedAuthor = authorService.updateAuthor(id, author);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }
}
