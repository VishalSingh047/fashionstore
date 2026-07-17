package com.fashionstore.fashionstore.service.impl;

import com.fashionstore.fashionstore.dto.ProductRequest;
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
    public String addProduct(ProductRequest request){
        Product product = new Product();

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setImgUrl(request.getImageUrl());
        product.setStock(request.getStock());

        productRepository.save(product);
        return "Product Added Successfully";
    }

    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public String updateProduct(Long id, ProductRequest request){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setCategory(request.getCategory());
        product.setImgUrl(request.getImageUrl());
        product.setStock(request.getStock());

        productRepository.save(product);

        return "Product updated successfully";
    }

    @Override
    public String deleteProduct(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);

        return "Product deleted successfully";
    }
}
