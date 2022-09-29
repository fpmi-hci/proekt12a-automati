package com.readme.api.rest;


import com.readme.api.db.entity.Book;
import com.readme.api.rest.dto.BookRequestDto;
import com.readme.api.service.BookService;
import com.readme.api.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestBody SearchParams searchParams) {
        List<Book> books = bookService.findBooks(searchParams);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> addBook(@RequestBody BookRequestDto book) {
        Book newBook = bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> deleteBook(@PathParam("id") long id) {
        Book deletedBook = bookService.deleteBook(id);
        return new ResponseEntity<>(deletedBook, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> updateBook(@PathParam("id") long id, BookRequestDto book) {
        Book updatedBook = bookService.updateBook(id, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @GetMapping("/purchased")
    public ResponseEntity<List<Book>> getPurchasedBooks(@RequestHeader("Authorization") String currentUserToken){
        List<Book>books  = orderService.findPurchasedBooksForCurrentUser(currentUserToken);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
