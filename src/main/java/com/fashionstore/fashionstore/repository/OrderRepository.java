package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
