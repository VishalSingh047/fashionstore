package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.ProductRequest;
import com.fashionstore.fashionstore.dto.ProductResponse;
import com.fashionstore.fashionstore.dto.UpdateProductRequest;

import java.util.List;

public interface ProductService {

    String addProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    String updateProduct(Long id, UpdateProductRequest request);

    String deleteProduct(Long id);
}