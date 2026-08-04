package com.fashionstore.fashionstore.service;

import com.fashionstore.fashionstore.dto.PageResponse;
import com.fashionstore.fashionstore.dto.ProductRequest;
import com.fashionstore.fashionstore.dto.ProductResponse;
import com.fashionstore.fashionstore.dto.UpdateProductRequest;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    String addProduct(ProductRequest request);

    List<ProductResponse> getAllProducts();

    PageResponse<ProductResponse> getProducts(
            String query,
            String category,
            String brand,
            String size,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Boolean inStock,
            Boolean featured,
            Boolean newArrival,
            int page,
            int sizeParam,
            String sortBy,
            String sortDir
    );

    ProductResponse getProductById(Long id);

    String updateProduct(Long id, UpdateProductRequest request);

    String deleteProduct(Long id);

    String updateStock(Long id, Integer quantity);
}