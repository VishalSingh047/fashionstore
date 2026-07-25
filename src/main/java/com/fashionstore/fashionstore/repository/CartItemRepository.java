package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Cart;
import com.fashionstore.fashionstore.entity.CartItem;
import com.fashionstore.fashionstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    @Modifying
    @Transactional
    void deleteByCart(Cart cart);
}

