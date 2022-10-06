package com.readme.api.service;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.Cart;
import com.readme.api.db.entity.User;
import com.readme.api.db.repository.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    public static final int USER_ID = 424242;
    public static final String USER_TOKEN = "token";
    @InjectMocks
    private CartService cartService;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @Mock
    private CartRepository cartRepository;

    @Test
    public void shouldGetCarForCurrentUserIfAlreadyExists() {
        User testUser = new User();
        testUser.setId(USER_ID);
        when(userService.findUserByToken(anyString())).thenReturn(testUser);
        Cart testCart = new Cart();
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(testCart));

        Cart cartForCurrentUser = cartService.getCartForCurrentUser(USER_TOKEN);

        assertEquals(cartForCurrentUser, testCart);

        verify(userService).findUserByToken(USER_TOKEN);
        verify(cartRepository).findByUserId(USER_ID);
    }

    @Test
    public void shouldCreateNewCartForCurrentUserNotExists() {
        User testUser = new User();
        testUser.setId(USER_ID);
        when(userService.findUserByToken(anyString())).thenReturn(testUser);
        Cart testCart = new Cart();
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        when(cartRepository.save(any())).thenReturn(testCart);

        String userToken = "";
        Cart cartForCurrentUser = cartService.getCartForCurrentUser(userToken);
        Cart expectedCart = new Cart();
        expectedCart.setUser(testUser);

        assertEquals(cartForCurrentUser, testCart);

        verify(cartRepository).save(expectedCart);
        verify(userService).findUserByToken(userToken);
        verify(cartRepository).findByUserId(USER_ID);
    }


    @Test
    public void shouldAddToCart() {
        long bookId = 42L;
        User testUser = new User();
        testUser.setId(USER_ID);
        when(userService.findUserByToken(anyString())).thenReturn(testUser);
        Cart testCart = new Cart();
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(testCart));
        Book testBook = new Book();
        testBook.setId(bookId);
        when(bookService.findById(anyLong())).thenReturn(testBook);

        Cart cart = cartService.addToCart(bookId, USER_TOKEN);
        assertEquals(cart.getBooks(), Collections.singletonList(testBook));

        verify(userService).findUserByToken(USER_TOKEN);
        verify(cartRepository).findByUserId(USER_ID);
        verify(bookService).findById(bookId);
    }

    @Test
    public void shouldRemoveFromCart() {
        long bookId = 42L;
        User testUser = new User();
        testUser.setId(USER_ID);
        when(userService.findUserByToken(anyString())).thenReturn(testUser);
        Book testBook = new Book();
        testBook.setId(bookId);
        Cart testCart = new Cart();
        testCart.setBooks(Collections.singletonList(testBook));
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(testCart));

        Cart cart = cartService.removeFromCart(bookId, USER_TOKEN);
        assertEquals(cart.getBooks(), Collections.emptyList());

        verify(userService).findUserByToken(USER_TOKEN);
        verify(cartRepository).findByUserId(USER_ID);
    }
}