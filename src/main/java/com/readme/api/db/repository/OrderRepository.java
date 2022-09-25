package com.readme.api.db.repository;

import com.readme.api.db.entity.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<UserOrder, Long> {
}
