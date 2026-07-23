package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.ProductRequest;
import com.fashionstore.fashionstore.dto.ProductResponse;
import com.fashionstore.fashionstore.dto.UpdateProductRequest;
import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.fashionstore.repository.ProductRepository;
import com.fashionstore.fashionstore.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public String addProduct(ProductRequest request) {

        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());

        // Pricing
        product.setPrice(request.getPrice());
        product.setOriginalPrice(request.getOriginalPrice());
        product.setDiscount(request.getDiscount());

        // Thrift Inventory Logic
        // Every product is unique, so default stock = 1
        product.setStock(1);
        product.setSoldOut(false);

        // Size
        product.setSize(request.getSize());

        // Images
        product.setImgUrl(request.getImgUrl());
        product.setImageGallery(request.getImageGallery());

        // Homepage Features
        product.setFeatured(
                request.getFeatured() != null && request.getFeatured()
        );

        product.setNewArrival(
                request.getNewArrival() != null && request.getNewArrival()
        );


        productRepository.save(product);

        return "Product Added Successfully";
    }



    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }



    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found")
                );

        return mapToResponse(product);
    }


    @Override
    public String updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        if (request.getProductName() != null)
            product.setProductName(request.getProductName());

        if (request.getDescription() != null)
            product.setDescription(request.getDescription());

        if (request.getCategory() != null)
            product.setCategory(request.getCategory());

        if (request.getBrand() != null)
            product.setBrand(request.getBrand());

        if (request.getPrice() != null)
            product.setPrice(request.getPrice());

        if (request.getOriginalPrice() != null)
            product.setOriginalPrice(request.getOriginalPrice());

        if (request.getDiscount() != null)
            product.setDiscount(request.getDiscount());

        if (request.getStock() != null)
            product.setStock(request.getStock());

        if (request.getSize() != null)
            product.setSize(request.getSize());

        if (request.getImgUrl() != null)
            product.setImgUrl(request.getImgUrl());

        if (request.getImageGallery() != null)
            product.setImageGallery(request.getImageGallery());

        if (request.getFeatured() != null)
            product.setFeatured(request.getFeatured());

        if (request.getNewArrival() != null)
            product.setNewArrival(request.getNewArrival());

        productRepository.save(product);

        return "Product updated successfully";
    }


    @Override
    public String deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found")
                );


        productRepository.delete(product);

        return "Product deleted successfully";
    }




    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getCategory(),
                product.getBrand(),
                product.getPrice(),
                product.getOriginalPrice(),
                product.getDiscount(),
                product.getStock(),
                product.getSoldOut(),
                product.getSize(),
                product.getImgUrl(),
                product.getImageGallery(),
                product.getRating(),
                product.getReviewCount(),
                product.getFeatured(),
                product.getNewArrival(),
                product.getCreatedAt()
        );
    }
}