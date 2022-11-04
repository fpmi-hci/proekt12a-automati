package com.readme.api.rest;


import com.readme.api.db.entity.Book;
import com.readme.api.rest.dto.BookContent;
import com.readme.api.rest.dto.BookRequestDto;
import com.readme.api.service.BookService;
import com.readme.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final OrderService orderService;


    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> books = bookService.findAll();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getById(@PathVariable("id") long id){
        Book book = bookService.findById(id);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestBody SearchParams searchParams) {
        List<Book> books = bookService.findBooks(searchParams);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @RequestMapping(path = "", method = POST, consumes =
            {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> addBook(@RequestPart BookRequestDto book,
                                        @RequestPart MultipartFile document) {
        Book newBook = bookService.addBook(book, document);
        return new ResponseEntity<>(newBook, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {
        Book deletedBook = bookService.deleteBook(id);
        return new ResponseEntity<>(deletedBook, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody BookRequestDto book) {
        Book updatedBook = bookService.updateBook(id, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }

    @GetMapping("/purchased")
    public ResponseEntity<List<Book>> getPurchasedBooks(@RequestHeader("Authorization") String currentUserToken) {
        List<Book> books = orderService.findPurchasedBooksForCurrentUser(currentUserToken);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/{id}/content")
    public ResponseEntity<?> getBookContent(@PathVariable("id") long id, @RequestHeader("Authorization") String currentUserToken) throws Exception {
        BookContent bookContent = bookService.getBookContent(id, currentUserToken);
        return new ResponseEntity<>(bookContent, HttpStatus.OK);
    }
}
