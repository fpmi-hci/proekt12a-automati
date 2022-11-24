package com.readme.api.service;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.Cart;
import com.readme.api.db.entity.User;
import com.readme.api.db.repository.CartRepository;
import com.readme.api.mapper.CartMapper;
import com.readme.api.rest.dto.CartResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartService {
    private final UserService userService;
    private final BookService bookService;
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;

    private final OrderService orderService;

    @Transactional
    public CartResponseDto getCartForCurrentUser(String currentUserToken) {
        return cartMapper.entityToResponse(getCart(currentUserToken));
    }

    @Transactional
    public CartResponseDto removeFromCart(long bookId, String currentUserToken) {
        Cart cartForCurrentUser = getCart(currentUserToken);
        Set<Book> books = cartForCurrentUser.getBooks();
        Set<Book> filteredBooks = books.stream()
                .filter(book -> book.getId() != bookId)
                .collect(Collectors.toSet());
        cartForCurrentUser.setBooks(filteredBooks);
        cartRepository.save(cartForCurrentUser);
        return cartMapper.entityToResponse(cartForCurrentUser);
    }

    @Transactional
    public CartResponseDto removeFromCartBatch(List<Long> bookIds, String currentUserToken) {
        Cart cartForCurrentUser = getCart(currentUserToken);
        Set<Book> books = cartForCurrentUser.getBooks();
        Set<Book> filteredBooks = books.stream()
                .filter(book -> !bookIds.contains(book.getId()))
                .collect(Collectors.toSet());
        cartForCurrentUser.setBooks(filteredBooks);
        cartRepository.save(cartForCurrentUser);
        return cartMapper.entityToResponse(cartForCurrentUser);
    }

    @Transactional
    public CartResponseDto addToCart(long bookId, String currentUserToken) {
        Cart cartForCurrentUser = getCart(currentUserToken);
        Set<Book> books = cartForCurrentUser.getBooks();
        Book addedBook = bookService.findById(bookId);
        if(notPurchasedByUser(addedBook, currentUserToken)) {
            books.add(addedBook);
        }
        cartRepository.save(cartForCurrentUser);
        return cartMapper.entityToResponse(cartForCurrentUser);
    }

    @Transactional
    public CartResponseDto addToCartBatch(List<Long> bookIds, String currentUserToken) {
        Cart cartForCurrentUser = getCart(currentUserToken);
        Set<Book> books = cartForCurrentUser.getBooks();
        Set<Book> addedBooks = bookService.findByIdList(bookIds);
        List<Book> newBooks = addedBooks.stream()
                .filter(book -> notPurchasedByUser(book, currentUserToken))
                .collect(Collectors.toList());
        books.addAll(newBooks);
        cartRepository.save(cartForCurrentUser);
        return cartMapper.entityToResponse(cartForCurrentUser);
    }

    private boolean notPurchasedByUser(Book book, String currentUserToken) {
        return !orderService.findPurchasedBooksForCurrentUser(currentUserToken).contains(book);
    }

    private Cart getCart(String currentUserToken) {
        User currentUser = userService.findUserByToken(currentUserToken);
        Optional<Cart> cartByUser = cartRepository.findByUserId(currentUser.getId());

        if (!cartByUser.isPresent()) {
            Cart cart = new Cart();
            cart.setUser(currentUser);
            return cartRepository.save(cart);
        }
        return cartByUser.get();
    }
}
