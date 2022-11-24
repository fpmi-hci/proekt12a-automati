package com.readme.api.service;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.User;
import com.readme.api.db.repository.BookRepository;
import com.readme.api.db.repository.BookSearchRepository;
import com.readme.api.db.repository.OrderRepository;
import com.readme.api.mapper.BookMapper;
import com.readme.api.rest.SearchParams;
import com.readme.api.rest.dto.BookContent;
import com.readme.api.rest.dto.BookRequestDto;
import com.readme.api.rest.dto.BookResponseDto;
import com.readme.api.s3.AmazonS3Client;
import com.readme.api.s3.S3Info;
import com.readme.api.service.exception.BookContentAccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookService {
    @Value("${readme.s3.bucket.name}")
    private String bucketName;

    private final BookRepository bookRepository;
    private final BookSearchRepository bookSearchRepository;
    private final BookMapper bookMapper;
    private final AmazonS3Client amazonS3Client;
    private final UserService userService;
    private final OrderRepository orderRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional
    public Book addBook(BookRequestDto requestBook, MultipartFile document) {
        Book book = bookMapper.requestToEntity(requestBook);
        String documentType = getDocumentType(document.getContentType());
        String pseudoUniqueDocumentName = "book" + UUID.randomUUID() + "." + documentType;
        try {
            S3Info s3Info = new S3Info(bucketName, pseudoUniqueDocumentName);
            amazonS3Client.persistContent(document.getInputStream(), s3Info);
        } catch (Exception e) {
            throw new RuntimeException();
        }
        book.setBookFile(pseudoUniqueDocumentName);
        return bookRepository.save(book);
    }

    @Transactional
    public Book deleteBook(long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
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
        Book bookToUpdate = findById(id);
        BigDecimal cost = requestBook.getCost();
        String title = requestBook.getTitle();
        String description = requestBook.getDescription();
        String imageUrl = requestBook.getImageUrl();
        if (imageUrl != null) {
            bookToUpdate.setImageUrl(imageUrl);
        }
        if (cost != null) {
            bookToUpdate.setCost(cost);
        }
        if (title != null) {
            bookToUpdate.setTitle(title);
        }
        if (description != null) {
            bookToUpdate.setDescription(description);
        }
        return bookRepository.save(bookToUpdate);
    }

    @Transactional
    public BookContent getBookContent(long bookId, String currentUserToken) throws IOException {
        Book book = findById(bookId);
        User currentUser = userService.findUserByToken(currentUserToken);
        if (!bookPurchasedByUser(book, currentUser)) {
            throw new BookContentAccessDeniedException(currentUser, book);
        }
        String bookAddress = book.getBookFile();
        S3Info s3Info = new S3Info(bucketName, bookAddress);
        S3Object fileFromS3 = amazonS3Client.getFileFromS3(s3Info);
        byte[] fileBytes = IOUtils.toByteArray(fileFromS3.getObjectContent());
        return new BookContent(fileFromS3.getKey(), fileBytes);
    }

    @Transactional
    public Set<Book> findByIdList(List<Long> ids) {
        if (ids == null) {
            return Collections.emptySet();
        }
        return new HashSet<>(bookRepository.findAllById(ids));
    }

    @Transactional
    public Book findById(long id) {
        return bookRepository.getById(id);
    }

    @Transactional
    public BookResponseDto findByIdFull(long id, String currentUserToken) {
        Book book = bookRepository.getById(id);
        User userByToken = userService.findUserByToken(currentUserToken);
        BookResponseDto bookResponseDto = bookMapper.entityToResponse(book);
        bookResponseDto.setPurchasedByUser(bookPurchasedByUser(book, userByToken));
        return bookResponseDto;
    }

    @Transactional
    public List<Book> findBooks(SearchParams searchParams) {
        return bookSearchRepository.search(searchParams);
    }

    private String getDocumentType(String contentType) {
        int contentStartIdx = contentType.lastIndexOf("/");
        return contentType.substring(contentStartIdx + 1);
    }

    private boolean bookPurchasedByUser(Book book, User currentUser) {
        Set<Book> userBooks = orderRepository.findByUserId(currentUser.getId()).get().getBooks();
        return userBooks.contains(book);
    }

}
