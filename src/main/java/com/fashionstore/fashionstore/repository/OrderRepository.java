package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Customer's own orders
    List<Order> findByUserOrderByOrderedAtDesc(UserAccount user);

}