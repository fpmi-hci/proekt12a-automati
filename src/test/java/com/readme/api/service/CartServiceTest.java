package com.readme.api.service;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.Cart;
import com.readme.api.db.entity.User;
import com.readme.api.db.repository.CartRepository;
import com.readme.api.mapper.CartMapper;
import com.readme.api.rest.dto.CartResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
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

    @Mock
    private CartMapper cartMapper;
    @Mock
    private OrderService orderService;

    @Test
    public void shouldGetCarForCurrentUserIfAlreadyExists() {
        User testUser = new User();
        testUser.setId(USER_ID);
        when(userService.findUserByToken(anyString())).thenReturn(testUser);
        Cart testCart = new Cart();
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(testCart));
        when(cartMapper.entityToResponse(any())).thenReturn(new CartResponseDto());

        CartResponseDto cartForCurrentUser = cartService.getCartForCurrentUser(USER_TOKEN);

        verify(cartMapper).entityToResponse(testCart);
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
        when(cartMapper.entityToResponse(any())).thenReturn(new CartResponseDto());

        String userToken = "";
        CartResponseDto cartForCurrentUser = cartService.getCartForCurrentUser(userToken);
        Cart expectedCart = new Cart();
        expectedCart.setUser(testUser);


        verify(cartMapper).entityToResponse(testCart);
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
        CartResponseDto resp = new CartResponseDto();
        resp.setBooks(Collections.singletonList(testBook));
        when(cartMapper.entityToResponse(any())).thenReturn(resp);
        when(orderService.findPurchasedBooksForCurrentUser(anyString())).thenReturn(new HashSet<>());

        CartResponseDto cart = cartService.addToCart(bookId, USER_TOKEN);
        assertEquals(cart.getBooks(), Collections.singletonList(testBook));

        verify(cartMapper).entityToResponse(any());
        verify(userService).findUserByToken(USER_TOKEN);
        verify(cartRepository).findByUserId(USER_ID);
        verify(bookService).findById(bookId);
        verify(orderService).findPurchasedBooksForCurrentUser(USER_TOKEN);
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
        testCart.setBooks(Collections.singleton(testBook));
        when(cartRepository.findByUserId(anyLong())).thenReturn(Optional.of(testCart));
        when(cartMapper.entityToResponse(any())).thenReturn(new CartResponseDto());

        CartResponseDto cart = cartService.removeFromCart(bookId, USER_TOKEN);
        assertNull(cart.getBooks());

        verify(cartMapper).entityToResponse(any());
        verify(userService).findUserByToken(USER_TOKEN);
        verify(cartRepository).findByUserId(USER_ID);
    }
}