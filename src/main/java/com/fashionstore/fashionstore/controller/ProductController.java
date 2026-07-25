package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.dto.PageResponse;
import com.fashionstore.fashionstore.dto.ProductRequest;
import com.fashionstore.fashionstore.dto.ProductResponse;
import com.fashionstore.fashionstore.dto.UpdateProductRequest;
import com.fashionstore.fashionstore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> addProduct(
            @Valid @RequestBody ProductRequest request
    ){
        return ResponseEntity.ok(ApiResponse.success(productService.addProduct(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> getProducts(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStock,
            @RequestParam(required = false) Boolean featured,
            @RequestParam(required = false) Boolean newArrival,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int sizeParam,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir
    ){
        return ResponseEntity.ok(ApiResponse.success(
                "Products fetched successfully",
                productService.getProducts(
                        query, category, brand, size, minPrice, maxPrice, inStock, featured, newArrival, page, sizeParam, sortBy, sortDir
                )
        ));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(){
        return ResponseEntity.ok(ApiResponse.success("All products fetched", productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(ApiResponse.success("Product details fetched", productService.getProductById(id)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request
    ){
        return ResponseEntity.ok(ApiResponse.success(productService.updateProduct(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteProduct(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(ApiResponse.success(productService.deleteProduct(id)));
    }
}