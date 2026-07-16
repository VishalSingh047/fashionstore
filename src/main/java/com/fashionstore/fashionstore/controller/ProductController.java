package com.fashionstore.fashionstore.controller;

import com.fashionstore.fashionstore.dto.ProductRequest;
import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public String addProduct(@Valid @RequestBody ProductRequest request){
        return productService.addProduct(request);
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}
