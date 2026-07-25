package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.entity.UserAccount;
import com.fashionstore.fashionstore.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {

    List<WishlistItem> findByUserOrderByCreatedAtDesc(UserAccount user);

    Optional<WishlistItem> findByUserAndProduct(UserAccount user, Product product);

    boolean existsByUserAndProduct(UserAccount user, Product product);

    void deleteByUserAndProduct(UserAccount user, Product product);

    void deleteByUser(UserAccount user);
}
