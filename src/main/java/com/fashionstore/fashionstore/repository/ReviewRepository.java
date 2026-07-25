package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.entity.Review;
import com.fashionstore.fashionstore.entity.UserAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    Page<Review> findByProductOrderByCreatedAtDesc(Product product, Pageable pageable);

    List<Review> findByUserOrderByCreatedAtDesc(UserAccount user);

    boolean existsByUserAndProduct(UserAccount user, Product product);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product = :product")
    Double calculateAverageRating(@Param("product") Product product);

    long countByProduct(Product product);
}
