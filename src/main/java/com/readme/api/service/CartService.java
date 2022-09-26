package com.readme.api.service;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.Cart;
import com.readme.api.db.entity.User;
import com.readme.api.db.repository.CartRepository;
import com.readme.api.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    private final BookService bookService;
    private final CartRepository cartRepository;

    @Transactional
    public Cart getCartForCurrentUser(String currentUserToken) {
        String login = jwtTokenProvider.getLogin(currentUserToken);
        User currentUser = userService.findByName(login);
        Optional<Cart> cartByUser = cartRepository.findByUserId(currentUser.getId());
        if (cartByUser.isEmpty()) {
            Cart cart = new Cart();
            cart.setUser(currentUser);
            return cartRepository.save(cart);
        }
        return cartByUser.get();
    }

    @Transactional
    public Cart removeFromCart(long bookId, String currentUserToken) {
        Cart cartForCurrentUser = getCartForCurrentUser(currentUserToken);
        List<Book> books = cartForCurrentUser.getBooks();
        List<Book> filteredBooks = books.stream()
                .filter(book -> book.getId() != bookId)
                .collect(Collectors.toList());
        cartForCurrentUser.setBooks(filteredBooks);
        cartRepository.save(cartForCurrentUser);
        return cartForCurrentUser;
    }

    @Transactional
    public Cart removeFromCartBatch(List<Long> bookIds, String currentUserToken) {
        Cart cartForCurrentUser = getCartForCurrentUser(currentUserToken);
        List<Book> books = cartForCurrentUser.getBooks();
        List<Book> filteredBooks = books.stream()
                .filter(book -> !bookIds.contains(book.getId()))
                .collect(Collectors.toList());
        cartForCurrentUser.setBooks(filteredBooks);
        cartRepository.save(cartForCurrentUser);
        return cartForCurrentUser;
    }

    @Transactional
    public Cart addToCart(long bookId, String currentUserToken) {
        Cart cartForCurrentUser = getCartForCurrentUser(currentUserToken);
        List<Book> books = cartForCurrentUser.getBooks();
        Book addedBook = bookService.findById(bookId);
        books.add(addedBook);
        cartRepository.save(cartForCurrentUser);
        return cartForCurrentUser;
    }

    @Transactional
    public Cart addToCartBatch(List<Long> bookIds, String currentUserToken) {
        Cart cartForCurrentUser = getCartForCurrentUser(currentUserToken);
        List<Book> books = cartForCurrentUser.getBooks();
        List<Book> addedBooks = bookService.findByIdList(bookIds);
        books.addAll(addedBooks);
        cartRepository.save(cartForCurrentUser);
        return cartForCurrentUser;
    }
}
