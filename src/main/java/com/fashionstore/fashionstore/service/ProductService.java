package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.ProductRequest;
import com.fashionstore.fashionstore.entity.Product;

import java.util.List;

public interface ProductService {
    String addProduct(ProductRequest request);
    List<Product> getAllProducts();
}
