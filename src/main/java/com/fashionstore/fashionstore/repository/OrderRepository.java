package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Order;
import com.fashionstore.fashionstore.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Customer's own orders
    List<Order> findByUserOrderByOrderedAtDesc(UserAccount user);
    Page<Order> findAll(Pageable pageable);
}