package com.readme.api.service;

import com.readme.api.db.entity.Book;
import com.readme.api.db.entity.User;
import com.readme.api.db.entity.UserOrder;
import com.readme.api.db.repository.OrderRepository;
import com.readme.api.mapper.UserOrderMapper;
import com.readme.api.rest.dto.UserOrderRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private UserOrderMapper orderMapper;

    private UserService userService;

    @Transactional
    public UserOrder addOrder(UserOrderRequestDto request) {
        UserOrder order = orderMapper.requestToEntity(request);
        Optional<UserOrder> optionalOrder = orderRepository.findById(request.getUserId());
        if(optionalOrder.isPresent()){
            UserOrder updatedOrder = optionalOrder.get();
            List<Book> newBooks = order.getBooks();
            List<Book> oldBooks = updatedOrder.getBooks();
            oldBooks.addAll(newBooks);
            return orderRepository.save(updatedOrder);
        }
        return orderRepository.save(order);
    }

    @Transactional
    public UserOrder updateOrder(long id, UserOrderRequestDto request){
        UserOrder order = orderMapper.requestToEntity(request);
        order.setId(id);
        return orderRepository.save(order);
    }

    public List<Book> findPurchasedBooksForCurrentUser(String currentUserToken) {
        User user = userService.findUserByToken(currentUserToken);
        return findUserBooks(user);
    }

    public List<Book> findUserBooks(User user) {
        return orderRepository.findByUserId(user.getId()).getBooks();
    }
}
