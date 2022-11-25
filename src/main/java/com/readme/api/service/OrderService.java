package com.readme.api.service;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.Cart;
import com.readme.api.db.entity.User;
import com.readme.api.db.entity.UserOrder;
import com.readme.api.db.repository.CartRepository;
import com.readme.api.db.repository.OrderRepository;
import com.readme.api.mapper.UserOrderMapper;
import com.readme.api.rest.dto.UserOrderRequestDto;
import com.readme.api.service.exception.OrderAccessDeniedException;
import com.readme.api.service.exception.OrderNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private UserOrderMapper orderMapper;

    private CartRepository cartRepository;
    private UserService userService;

    @Transactional
    public UserOrder addOrder(UserOrderRequestDto request, String currentUserToken) {
        UserOrder order = orderMapper.requestToEntity(request);
        User userByToken = userService.findUserByToken(currentUserToken);
        Optional<UserOrder> optionalOrder = orderRepository.findByUserId(userByToken.getId());
        removeOrderBooksFromCart(order.getBooks(), userByToken.getId());
        if (optionalOrder.isPresent()) {
            UserOrder updatedOrder = optionalOrder.get();
            Set<Book> newBooks = order.getBooks();
            Set<Book> oldBooks = updatedOrder.getBooks();
            oldBooks.addAll(newBooks);
            return updatedOrder;
        }
        order.setUser(userByToken);
        return orderRepository.save(order);
    }

    @Transactional
    public UserOrder updateOrder(long id, UserOrderRequestDto request, String currentUserToken) {
        User userByToken = userService.findUserByToken(currentUserToken);
        Optional<UserOrder> optOrder = orderRepository.findById(id);
        if (!optOrder.isPresent()) {
            throw new OrderNotFoundException(id);
        }
        UserOrder orderToUpdate = optOrder.get();
        if (!orderToUpdate.getUser().equals(userByToken)) {
            throw new OrderAccessDeniedException(userByToken, orderToUpdate);
        }
        UserOrder order = orderMapper.requestToEntity(request);
        removeOrderBooksFromCart(order.getBooks(), userByToken.getId());
        orderToUpdate.setBooks(order.getBooks());
        return orderToUpdate;
    }

    public Set<Book> findPurchasedBooksForCurrentUser(String currentUserToken) {
        User user = userService.findUserByToken(currentUserToken);
        return findUserBooks(user);
    }

    public Set<Book> findUserBooks(User user) {
        Optional<UserOrder> order = orderRepository.findByUserId(user.getId());
        if (order.isPresent()) {
            return order.get().getBooks();
        }
        return new HashSet<>();
    }

    private void removeOrderBooksFromCart(Set<Book> books, long userId) {
        Optional<Cart> optionalCart = cartRepository.findByUserId(userId);
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            cart.getBooks().removeAll(books);
        }
    }
}
