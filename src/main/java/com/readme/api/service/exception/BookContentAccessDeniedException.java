package com.readme.api.service.exception;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.User;

public class BookContentAccessDeniedException extends AccessDeniedException {
    private static final String BOOK_RESOURCE = "Book, title = %s, fileName = %s";

    public BookContentAccessDeniedException(User user, Book book) {
        super(user, String.format(book.getTitle(), book.getBookFile()));
    }
}
