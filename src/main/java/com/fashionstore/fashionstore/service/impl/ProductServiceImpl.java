package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.common.MessageConstants;
import com.fashionstore.fashionstore.dto.PageResponse;
import com.fashionstore.fashionstore.dto.ProductRequest;
import com.fashionstore.fashionstore.dto.ProductResponse;
import com.fashionstore.fashionstore.dto.UpdateProductRequest;
import com.fashionstore.fashionstore.entity.Product;
import com.fashionstore.fashionstore.exception.ResourceNotFoundException;
import com.fashionstore.fashionstore.repository.ProductRepository;
import com.fashionstore.fashionstore.service.ProductService;
import com.fashionstore.fashionstore.specification.ProductSpecification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public String addProduct(ProductRequest request) {

        Product product = new Product();

        product.setSku(request.getSku());   // <-- ADD THIS

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setBrand(request.getBrand());

        product.setActive(true);
        product.setSoldOut(false);

        // Pricing
        product.setPrice(request.getPrice());
        product.setOriginalPrice(request.getOriginalPrice());
        product.setDiscount(request.getDiscount());

        // Inventory Logic
        product.setStock(request.getStock() != null ? request.getStock() : 1);
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

        return MessageConstants.PRODUCT_CREATED;
    }


    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public PageResponse<ProductResponse> getProducts(
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
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, sizeParam, sort);

        Specification<Product> spec = ProductSpecification.filterProducts(
                query, category, brand, size, minPrice, maxPrice, inStock, featured, newArrival
        );

        Page<Product> productPage = productRepository.findAll(spec, pageable);

        List<ProductResponse> content = productPage.getContent().stream()
                .map(this::mapToResponse)
                .toList();

        return new PageResponse<>(
                content,
                productPage.getNumber(),
                productPage.getSize(),
                productPage.getTotalElements(),
                productPage.getTotalPages(),
                productPage.isLast()
        );
    }


    @Override
    public ProductResponse getProductById(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND)
                );

        return mapToResponse(product);
    }


    @Override
    public String updateProduct(Long id, UpdateProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND));

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

        return MessageConstants.PRODUCT_UPDATED;
    }


    @Override
    public String deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(MessageConstants.PRODUCT_NOT_FOUND)
                );


        productRepository.delete(product);

        return MessageConstants.PRODUCT_DELETED;
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

    @Override
    public String updateStock(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                MessageConstants.PRODUCT_NOT_FOUND
                        )
                );
        int updatedStock = product.getStock() + quantity;

        if(updatedStock < 0){
            updatedStock = 0;
        }

        product.setStock(updatedStock);
        product.setSoldOut(updatedStock == 0);

        productRepository.save(product);
        return "Stock updated successfully";
    }
}