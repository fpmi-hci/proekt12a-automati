package com.readme.api.db.repository;

import com.readme.api.db.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
    Optional<UserOrder> findByUserId(long userId);
}
