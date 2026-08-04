package com.fashionstore.fashionstore.controller;


import com.fashionstore.fashionstore.common.ApiResponse;
import com.fashionstore.fashionstore.dto.ProductRequest;
import com.fashionstore.fashionstore.dto.ProductResponse;
import com.fashionstore.fashionstore.dto.UpdateProductRequest;
import com.fashionstore.fashionstore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final ProductService productService;

    public AdminProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts(){

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Admin products fetched",
                        productService.getAllProducts()
                )
        );
    }


    @PostMapping
    public ResponseEntity<ApiResponse<String>> addProduct(
            @Valid @RequestBody ProductRequest request
    ){

        return ResponseEntity.ok(
                ApiResponse.success(
                        productService.addProduct(request)
                )
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProductRequest request
    ){

        return ResponseEntity.ok(
                ApiResponse.success(
                        productService.updateProduct(id,request)
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(
            @PathVariable Long id
    ){

        return ResponseEntity.ok(
                ApiResponse.success(
                        productService.deleteProduct(id)
                )
        );
    }
}