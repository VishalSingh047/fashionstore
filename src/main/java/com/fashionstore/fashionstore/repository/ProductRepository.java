package com.fashionstore.fashionstore.repository;

import com.fashionstore.fashionstore.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
