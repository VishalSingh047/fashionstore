package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Cart;
import com.fashionstore.fashionstore.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(UserAccount user);
}
