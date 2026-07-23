package com.fashionstore.fashionstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Basic Information

    @NotBlank(message = "Product name is required")
    private String productName;

    @Column(length = 2000)
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    private String brand;


    // Pricing

    @NotNull(message = "Price is required")
    private BigDecimal price;

    private BigDecimal originalPrice;

    private Integer discount;


    // Single Piece Inventory

    @NotNull(message = "Stock is required")
    private Integer stock;

    private Boolean soldOut = false;


    // Size Information

    private String size;


    // Images

    @NotBlank(message = "Image URL is required")
    private String imgUrl;

    @Column(length = 3000)
    private String imageGallery;


    // Customer Trust

    private Double rating = 0.0;

    private Integer reviewCount = 0;


    // Homepage Controls

    private Boolean featured = false;

    private Boolean newArrival = true;


    // Product Visibility

    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;
}