package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(Order order);

}